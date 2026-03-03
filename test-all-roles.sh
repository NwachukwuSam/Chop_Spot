#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

BASE_URL="http://localhost:8080/api/auth"

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  Delichops Authentication Test Suite${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Function to print section headers
print_section() {
    echo -e "\n${YELLOW}>>> $1${NC}\n"
}

# Function to print success
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

# Function to print error
print_error() {
    echo -e "${RED}✗ $1${NC}"
}

# Check if services are running
print_section "Checking Services"

if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
    print_success "API Gateway is running"
else
    print_error "API Gateway is not running on port 8080"
    echo "Please start services first. See START_SERVICES.md"
    exit 1
fi

if curl -s http://localhost:9090 > /dev/null 2>&1; then
    print_success "Keycloak is running"
else
    print_error "Keycloak is not running on port 9090"
    echo "Please start docker-compose first"
    exit 1
fi

# Test 1: Register CUSTOMER
print_section "Test 1: Register CUSTOMER"
CUSTOMER_REG=$(curl -s -X POST $BASE_URL/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_customer_'$(date +%s)'",
    "email": "customer'$(date +%s)'@test.com",
    "password": "Test123!",
    "firstName": "Test",
    "lastName": "Customer",
    "userType": "CUSTOMER"
  }')

echo "$CUSTOMER_REG" | jq '.'
if echo "$CUSTOMER_REG" | grep -q "registered successfully"; then
    print_success "Customer registration successful"
    CUSTOMER_USERNAME=$(echo "$CUSTOMER_REG" | jq -r '.username')
else
    print_error "Customer registration failed"
fi

# Test 2: Login CUSTOMER
print_section "Test 2: Login CUSTOMER"
CUSTOMER_LOGIN=$(curl -s -X POST $BASE_URL/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "'$CUSTOMER_USERNAME'",
    "password": "Test123!"
  }')

echo "$CUSTOMER_LOGIN" | jq '.'
if echo "$CUSTOMER_LOGIN" | grep -q "accessToken"; then
    print_success "Customer login successful"
    CUSTOMER_TOKEN=$(echo "$CUSTOMER_LOGIN" | jq -r '.accessToken')
else
    print_error "Customer login failed"
fi

# Test 3: Access Customer Dashboard
print_section "Test 3: Access Customer Dashboard"
CUSTOMER_DASH=$(curl -s -X GET $BASE_URL/customer/dashboard \
  -H "Authorization: Bearer $CUSTOMER_TOKEN")

echo "$CUSTOMER_DASH" | jq '.'
if echo "$CUSTOMER_DASH" | grep -q "Customer Dashboard"; then
    print_success "Customer dashboard access successful"
else
    print_error "Customer dashboard access failed"
fi

# Test 4: Register VENDOR
print_section "Test 4: Register VENDOR"
VENDOR_REG=$(curl -s -X POST $BASE_URL/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_vendor_'$(date +%s)'",
    "email": "vendor'$(date +%s)'@test.com",
    "password": "Test123!",
    "firstName": "Test",
    "lastName": "Vendor",
    "userType": "VENDOR"
  }')

echo "$VENDOR_REG" | jq '.'
if echo "$VENDOR_REG" | grep -q "registered successfully"; then
    print_success "Vendor registration successful"
    VENDOR_USERNAME=$(echo "$VENDOR_REG" | jq -r '.username')
else
    print_error "Vendor registration failed"
fi

# Test 5: Login VENDOR
print_section "Test 5: Login VENDOR"
VENDOR_LOGIN=$(curl -s -X POST $BASE_URL/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "'$VENDOR_USERNAME'",
    "password": "Test123!"
  }')

echo "$VENDOR_LOGIN" | jq '.'
if echo "$VENDOR_LOGIN" | grep -q "accessToken"; then
    print_success "Vendor login successful"
    VENDOR_TOKEN=$(echo "$VENDOR_LOGIN" | jq -r '.accessToken')
else
    print_error "Vendor login failed"
fi

# Test 6: Access Vendor Dashboard
print_section "Test 6: Access Vendor Dashboard"
VENDOR_DASH=$(curl -s -X GET $BASE_URL/vendor/dashboard \
  -H "Authorization: Bearer $VENDOR_TOKEN")

echo "$VENDOR_DASH" | jq '.'
if echo "$VENDOR_DASH" | grep -q "Vendor Dashboard"; then
    print_success "Vendor dashboard access successful"
else
    print_error "Vendor dashboard access failed"
fi

# Test 7: Register RIDER
print_section "Test 7: Register RIDER"
RIDER_REG=$(curl -s -X POST $BASE_URL/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_rider_'$(date +%s)'",
    "email": "rider'$(date +%s)'@test.com",
    "password": "Test123!",
    "firstName": "Test",
    "lastName": "Rider",
    "userType": "RIDER"
  }')

echo "$RIDER_REG" | jq '.'
if echo "$RIDER_REG" | grep -q "registered successfully"; then
    print_success "Rider registration successful"
    RIDER_USERNAME=$(echo "$RIDER_REG" | jq -r '.username')
else
    print_error "Rider registration failed"
fi

# Test 8: Login RIDER
print_section "Test 8: Login RIDER"
RIDER_LOGIN=$(curl -s -X POST $BASE_URL/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "'$RIDER_USERNAME'",
    "password": "Test123!"
  }')

echo "$RIDER_LOGIN" | jq '.'
if echo "$RIDER_LOGIN" | grep -q "accessToken"; then
    print_success "Rider login successful"
    RIDER_TOKEN=$(echo "$RIDER_LOGIN" | jq -r '.accessToken')
else
    print_error "Rider login failed"
fi

# Test 9: Access Rider Dashboard
print_section "Test 9: Access Rider Dashboard"
RIDER_DASH=$(curl -s -X GET $BASE_URL/rider/dashboard \
  -H "Authorization: Bearer $RIDER_TOKEN")

echo "$RIDER_DASH" | jq '.'
if echo "$RIDER_DASH" | grep -q "Rider Dashboard"; then
    print_success "Rider dashboard access successful"
else
    print_error "Rider dashboard access failed"
fi

# Test 10: Login ADMIN (must be created manually in Keycloak)
print_section "Test 10: Login ADMIN (if exists)"
ADMIN_LOGIN=$(curl -s -X POST $BASE_URL/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin@delichops.com",
    "password": "Admin123!"
  }')

if echo "$ADMIN_LOGIN" | grep -q "accessToken"; then
    print_success "Admin login successful"
    ADMIN_TOKEN=$(echo "$ADMIN_LOGIN" | jq -r '.accessToken')
    echo "$ADMIN_LOGIN" | jq '.'
    
    # Test 11: Access Admin Dashboard
    print_section "Test 11: Access Admin Dashboard"
    ADMIN_DASH=$(curl -s -X GET $BASE_URL/admin/dashboard \
      -H "Authorization: Bearer $ADMIN_TOKEN")
    
    echo "$ADMIN_DASH" | jq '.'
    if echo "$ADMIN_DASH" | grep -q "Admin Dashboard"; then
        print_success "Admin dashboard access successful"
    else
        print_error "Admin dashboard access failed"
    fi
else
    print_error "Admin user not found (create manually in Keycloak)"
    echo "See KEYCLOAK_SETUP.md for instructions"
fi

# Test 12: Test Authorization Failure
print_section "Test 12: Authorization Test (Customer accessing Admin)"
AUTH_FAIL=$(curl -s -X GET $BASE_URL/admin/dashboard \
  -H "Authorization: Bearer $CUSTOMER_TOKEN")

echo "$AUTH_FAIL" | jq '.'
if echo "$AUTH_FAIL" | grep -q -E "(Forbidden|403|Access Denied)"; then
    print_success "Authorization correctly denied"
else
    print_error "Authorization should have been denied"
fi

# Test 13: Get User Info for each role
print_section "Test 13: Get User Info (Customer)"
curl -s -X GET $BASE_URL/me \
  -H "Authorization: Bearer $CUSTOMER_TOKEN" | jq '.'

print_section "Test 14: Get User Info (Vendor)"
curl -s -X GET $BASE_URL/me \
  -H "Authorization: Bearer $VENDOR_TOKEN" | jq '.'

print_section "Test 15: Get User Info (Rider)"
curl -s -X GET $BASE_URL/me \
  -H "Authorization: Bearer $RIDER_TOKEN" | jq '.'

# Summary
echo ""
echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  Test Summary${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""
echo -e "Customer Username: ${GREEN}$CUSTOMER_USERNAME${NC}"
echo -e "Vendor Username:   ${GREEN}$VENDOR_USERNAME${NC}"
echo -e "Rider Username:    ${GREEN}$RIDER_USERNAME${NC}"
echo ""
echo -e "${YELLOW}Tokens saved for manual testing:${NC}"
echo -e "CUSTOMER_TOKEN=$CUSTOMER_TOKEN"
echo -e "VENDOR_TOKEN=$VENDOR_TOKEN"
echo -e "RIDER_TOKEN=$RIDER_TOKEN"
echo ""
echo -e "${GREEN}All tests completed!${NC}"
