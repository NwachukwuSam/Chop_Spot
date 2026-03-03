#!/bin/bash

# Test Script for Render Deployment
# Usage: ./test-render-deployment.sh https://your-gateway-url.onrender.com

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if URL is provided
if [ -z "$1" ]; then
    echo -e "${RED}Error: Please provide your API Gateway URL${NC}"
    echo "Usage: ./test-render-deployment.sh https://delichops-gateway.onrender.com"
    exit 1
fi

API_URL="$1"
echo -e "${YELLOW}Testing Delichops API at: $API_URL${NC}\n"

# Generate random username to avoid conflicts
RANDOM_USER="testuser_$(date +%s)"
EMAIL="${RANDOM_USER}@example.com"
PASSWORD="Test123!"

echo "=========================================="
echo "Test 1: Register New User"
echo "=========================================="
echo "Username: $RANDOM_USER"
echo "Email: $EMAIL"
echo ""

REGISTER_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$API_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d "{
    \"username\": \"$RANDOM_USER\",
    \"email\": \"$EMAIL\",
    \"password\": \"$PASSWORD\",
    \"firstName\": \"Test\",
    \"lastName\": \"User\",
    \"userType\": \"CUSTOMER\"
  }")

HTTP_CODE=$(echo "$REGISTER_RESPONSE" | tail -n1)
RESPONSE_BODY=$(echo "$REGISTER_RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 200 ] || [ "$HTTP_CODE" -eq 201 ]; then
    echo -e "${GREEN}✓ Registration successful!${NC}"
    echo "Response: $RESPONSE_BODY"
else
    echo -e "${RED}✗ Registration failed (HTTP $HTTP_CODE)${NC}"
    echo "Response: $RESPONSE_BODY"
    exit 1
fi

echo ""
echo "=========================================="
echo "Test 2: Login"
echo "=========================================="

LOGIN_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$API_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
    \"username\": \"$RANDOM_USER\",
    \"password\": \"$PASSWORD\"
  }")

HTTP_CODE=$(echo "$LOGIN_RESPONSE" | tail -n1)
RESPONSE_BODY=$(echo "$LOGIN_RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 200 ]; then
    echo -e "${GREEN}✓ Login successful!${NC}"
    
    # Extract access token (basic extraction, works for most cases)
    ACCESS_TOKEN=$(echo "$RESPONSE_BODY" | grep -o '"access_token":"[^"]*' | cut -d'"' -f4)
    
    if [ -z "$ACCESS_TOKEN" ]; then
        echo -e "${YELLOW}⚠ Could not extract access token${NC}"
        echo "Response: $RESPONSE_BODY"
    else
        echo "Access token received (first 50 chars): ${ACCESS_TOKEN:0:50}..."
    fi
else
    echo -e "${RED}✗ Login failed (HTTP $HTTP_CODE)${NC}"
    echo "Response: $RESPONSE_BODY"
    exit 1
fi

echo ""
echo "=========================================="
echo "Test 3: Get User Info"
echo "=========================================="

if [ -n "$ACCESS_TOKEN" ]; then
    USER_INFO_RESPONSE=$(curl -s -w "\n%{http_code}" -X GET "$API_URL/api/auth/me" \
      -H "Authorization: Bearer $ACCESS_TOKEN")
    
    HTTP_CODE=$(echo "$USER_INFO_RESPONSE" | tail -n1)
    RESPONSE_BODY=$(echo "$USER_INFO_RESPONSE" | sed '$d')
    
    if [ "$HTTP_CODE" -eq 200 ]; then
        echo -e "${GREEN}✓ User info retrieved successfully!${NC}"
        echo "Response: $RESPONSE_BODY"
    else
        echo -e "${RED}✗ Failed to get user info (HTTP $HTTP_CODE)${NC}"
        echo "Response: $RESPONSE_BODY"
    fi
else
    echo -e "${YELLOW}⚠ Skipping (no access token)${NC}"
fi

echo ""
echo "=========================================="
echo "Test 4: Get User Profile"
echo "=========================================="

if [ -n "$ACCESS_TOKEN" ]; then
    PROFILE_RESPONSE=$(curl -s -w "\n%{http_code}" -X GET "$API_URL/api/users/profile" \
      -H "Authorization: Bearer $ACCESS_TOKEN")
    
    HTTP_CODE=$(echo "$PROFILE_RESPONSE" | tail -n1)
    RESPONSE_BODY=$(echo "$PROFILE_RESPONSE" | sed '$d')
    
    if [ "$HTTP_CODE" -eq 200 ]; then
        echo -e "${GREEN}✓ Profile retrieved successfully!${NC}"
        echo "Response: $RESPONSE_BODY"
    else
        echo -e "${YELLOW}⚠ Profile not found or error (HTTP $HTTP_CODE)${NC}"
        echo "Response: $RESPONSE_BODY"
        echo "(This is normal if user-service hasn't created profile yet)"
    fi
else
    echo -e "${YELLOW}⚠ Skipping (no access token)${NC}"
fi

echo ""
echo "=========================================="
echo -e "${GREEN}All tests completed!${NC}"
echo "=========================================="
echo ""
echo "Summary:"
echo "- API URL: $API_URL"
echo "- Test User: $RANDOM_USER"
echo "- Email: $EMAIL"
echo ""
echo "You can now use this API with your frontend or Postman!"
