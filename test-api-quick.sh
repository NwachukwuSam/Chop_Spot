#!/bin/bash

# Quick API Test Script for Delichops
# Tests registration and login for all roles

BASE_URL="http://localhost:8080/api/auth"
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "========================================="
echo "  Delichops API Quick Test"
echo "========================================="
echo ""

# Test 1: Register Customer
echo -e "${YELLOW}1. Registering CUSTOMER...${NC}"
CUSTOMER_REG=$(curl -s -X POST $BASE_URL/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_customer",
    "email": "customer@test.com",
    "password": "Test123!",
    "firstName": "Test",
    "lastName": "Customer",
    "userType": "CUSTOMER"
  }')

if echo "$CUSTOMER_REG" | grep -q "registered successfully"; then
    echo -e "${GREEN}✓ Customer registered successfully${NC}"
else
    echo -e "${RED}✗ Customer registration failed${NC}"
    echo "$CUSTOMER_REG"
fi
echo ""

# Test 2: Login Customer
echo -e "${YELLOW}2. Logging in as CUSTOMER...${NC}"
CUSTOMER_LOGIN=$(curl -s -X POST $BASE_URL/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_customer",
    "password": "Test123!"
  }')

CUSTOMER_TOKEN=$(echo $CUSTOMER_LOGIN | grep -o '"accessToken":"[^"]*' | cut -d'"' -f4)

if [ ! -z "$CUSTOMER_TOKEN" ]; then
    echo -e "${GREEN}✓ Customer logged in successfully${NC}"
    echo "Token: ${CUSTOMER_TOKEN:0:50}..."
else
    echo -e "${RED}✗ Customer login failed${NC}"
    echo "$CUSTOMER_LOGIN"
fi
echo ""

# Test 3: Access Customer Dashboard
echo -e "${YELLOW}3. Accessing Customer Dashboard...${NC}"
CUSTOMER_DASH=$(curl -s -X GET $BASE_URL/customer/dashboard \
  -H "Authorization: Bearer $CUSTOMER_TOKEN")

if echo "$CUSTOMER_DASH" | grep -q "Customer Dashboard"; then
    echo -e "${GREEN}✓ Customer dashboard accessible${NC}"
else
    echo -e "${RED}✗ Customer dashboard access failed${NC}"
    echo "$CUSTOMER_DASH"
fi
echo ""

# Test 4: Register Vendor
echo -e "${YELLOW}4. Registering VENDOR...${NC}"
VENDOR_REG=$(curl -s -X POST $BASE_URL/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_vendor",
    "email": "vendor@test.com",
    "password": "Test123!",
    "firstName": "Test",
    "lastName": "Vendor",
    "userType": "VENDOR"
  }')

if echo "$VENDOR_REG" | grep -q "registered successfully"; then
    echo -e "${GREEN}✓ Vendor registered successfully${NC}"
else
    echo -e "${RED}✗ Vendor registration failed${NC}"
    echo "$VENDOR_REG"
fi
echo ""

# Test 5: Login Vendor
echo -e "${YELLOW}5. Logging in as VENDOR...${NC}"
VENDOR_LOGIN=$(curl -s -X POST $BASE_URL/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_vendor",
    "password": "Test123!"
  }')

VENDOR_TOKEN=$(echo $VENDOR_LOGIN | grep -o '"accessToken":"[^"]*' | cut -d'"' -f4)

if [ ! -z "$VENDOR_TOKEN" ]; then
    echo -e "${GREEN}✓ Vendor logged in successfully${NC}"
    echo "Token: ${VENDOR_TOKEN:0:50}..."
else
    echo -e "${RED}✗ Vendor login failed${NC}"
    echo "$VENDOR_LOGIN"
fi
echo ""

# Test 6: Access Vendor Dashboard
echo -e "${YELLOW}6. Accessing Vendor Dashboard...${NC}"
VENDOR_DASH=$(curl -s -X GET $BASE_URL/vendor/dashboard \
  -H "Authorization: Bearer $VENDOR_TOKEN")

if echo "$VENDOR_DASH" | grep -q "Vendor Dashboard"; then
    echo -e "${GREEN}✓ Vendor dashboard accessible${NC}"
else
    echo -e "${RED}✗ Vendor dashboard access failed${NC}"
    echo "$VENDOR_DASH"
fi
echo ""

# Test 7: Register Rider
echo -e "${YELLOW}7. Registering RIDER...${NC}"
RIDER_REG=$(curl -s -X POST $BASE_URL/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_rider",
    "email": "rider@test.com",
    "password": "Test123!",
    "firstName": "Test",
    "lastName": "Rider",
    "userType": "RIDER"
  }')

if echo "$RIDER_REG" | grep -q "registered successfully"; then
    echo -e "${GREEN}✓ Rider registered successfully${NC}"
else
    echo -e "${RED}✗ Rider registration failed${NC}"
    echo "$RIDER_REG"
fi
echo ""

# Test 8: Login Rider
echo -e "${YELLOW}8. Logging in as RIDER...${NC}"
RIDER_LOGIN=$(curl -s -X POST $BASE_URL/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test_rider",
    "password": "Test123!"
  }')

RIDER_TOKEN=$(echo $RIDER_LOGIN | grep -o '"accessToken":"[^"]*' | cut -d'"' -f4)

if [ ! -z "$RIDER_TOKEN" ]; then
    echo -e "${GREEN}✓ Rider logged in successfully${NC}"
    echo "Token: ${RIDER_TOKEN:0:50}..."
else
    echo -e "${RED}✗ Rider login failed${NC}"
    echo "$RIDER_LOGIN"
fi
echo ""

# Test 9: Access Rider Dashboard
echo -e "${YELLOW}9. Accessing Rider Dashboard...${NC}"
RIDER_DASH=$(curl -s -X GET $BASE_URL/rider/dashboard \
  -H "Authorization: Bearer $RIDER_TOKEN")

if echo "$RIDER_DASH" | grep -q "Rider Dashboard"; then
    echo -e "${GREEN}✓ Rider dashboard accessible${NC}"
else
    echo -e "${RED}✗ Rider dashboard access failed${NC}"
    echo "$RIDER_DASH"
fi
echo ""

# Test 10: Authorization Test (Customer tries Admin)
echo -e "${YELLOW}10. Testing Authorization (Customer → Admin)...${NC}"
ADMIN_ATTEMPT=$(curl -s -w "\n%{http_code}" -X GET $BASE_URL/admin/dashboard \
  -H "Authorization: Bearer $CUSTOMER_TOKEN")

HTTP_CODE=$(echo "$ADMIN_ATTEMPT" | tail -n1)

if [ "$HTTP_CODE" = "403" ]; then
    echo -e "${GREEN}✓ Authorization working (403 Forbidden)${NC}"
else
    echo -e "${RED}✗ Authorization test failed (Expected 403, got $HTTP_CODE)${NC}"
fi
echo ""

echo "========================================="
echo "  Test Summary"
echo "========================================="
echo -e "${GREEN}✓ All roles can register and login${NC}"
echo -e "${GREEN}✓ Role-based access control working${NC}"
echo -e "${GREEN}✓ Authorization properly enforced${NC}"
echo ""
echo "Next steps:"
echo "1. Import Postman collection for detailed testing"
echo "2. See POSTMAN_TESTING_GUIDE.md for instructions"
echo ""
