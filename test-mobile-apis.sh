#!/bin/bash

# Test Script for Mobile APIs
# Tests: Signup, Login, Forgot Password, Reset Password

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

if [ -z "$1" ]; then
    API_URL="http://localhost:8080"
else
    API_URL="$1"
fi

echo -e "${BLUE}=========================================="
echo "Testing Mobile APIs"
echo "API URL: $API_URL"
echo -e "==========================================${NC}\n"

# Generate random email
TIMESTAMP=$(date +%s)
EMAIL="testuser${TIMESTAMP}@example.com"
PASSWORD="Test123!"

echo -e "${YELLOW}Test 1: Sign Up${NC}"
echo "Email: $EMAIL"
echo "Password: $PASSWORD"
echo ""

SIGNUP_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$API_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"$EMAIL\",
    \"password\": \"$PASSWORD\",
    \"confirmPassword\": \"$PASSWORD\"
  }")

HTTP_CODE=$(echo "$SIGNUP_RESPONSE" | tail -n1)
RESPONSE_BODY=$(echo "$SIGNUP_RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 201 ] || [ "$HTTP_CODE" -eq 200 ]; then
    echo -e "${GREEN}✓ Sign up successful!${NC}"
    echo "Response: $RESPONSE_BODY"
else
    echo -e "${RED}✗ Sign up failed (HTTP $HTTP_CODE)${NC}"
    echo "Response: $RESPONSE_BODY"
    exit 1
fi

echo ""
echo -e "${YELLOW}Test 2: Sign In${NC}"

LOGIN_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$API_URL/api/auth/login" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"$EMAIL\",
    \"password\": \"$PASSWORD\"
  }")

HTTP_CODE=$(echo "$LOGIN_RESPONSE" | tail -n1)
RESPONSE_BODY=$(echo "$LOGIN_RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 200 ]; then
    echo -e "${GREEN}✓ Sign in successful!${NC}"
    ACCESS_TOKEN=$(echo "$RESPONSE_BODY" | grep -o '"accessToken":"[^"]*' | cut -d'"' -f4)
    echo "Access token received: ${ACCESS_TOKEN:0:50}..."
else
    echo -e "${RED}✗ Sign in failed (HTTP $HTTP_CODE)${NC}"
    echo "Response: $RESPONSE_BODY"
    exit 1
fi

echo ""
echo -e "${YELLOW}Test 3: Get Current User${NC}"

if [ -n "$ACCESS_TOKEN" ]; then
    USER_RESPONSE=$(curl -s -w "\n%{http_code}" -X GET "$API_URL/api/auth/me" \
      -H "Authorization: Bearer $ACCESS_TOKEN")
    
    HTTP_CODE=$(echo "$USER_RESPONSE" | tail -n1)
    RESPONSE_BODY=$(echo "$USER_RESPONSE" | sed '$d')
    
    if [ "$HTTP_CODE" -eq 200 ]; then
        echo -e "${GREEN}✓ Get user info successful!${NC}"
        echo "Response: $RESPONSE_BODY"
    else
        echo -e "${RED}✗ Get user info failed (HTTP $HTTP_CODE)${NC}"
        echo "Response: $RESPONSE_BODY"
    fi
fi

echo ""
echo -e "${YELLOW}Test 4: Forgot Password (Request OTP)${NC}"

FORGOT_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST "$API_URL/api/auth/forgot-password" \
  -H "Content-Type: application/json" \
  -d "{
    \"email\": \"$EMAIL\"
  }")

HTTP_CODE=$(echo "$FORGOT_RESPONSE" | tail -n1)
RESPONSE_BODY=$(echo "$FORGOT_RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 200 ]; then
    echo -e "${GREEN}✓ OTP request successful!${NC}"
    echo "Response: $RESPONSE_BODY"
    echo ""
    echo -e "${BLUE}NOTE: Check server logs for the OTP code${NC}"
    echo -e "${BLUE}In development, OTP is logged to console${NC}"
else
    echo -e "${RED}✗ OTP request failed (HTTP $HTTP_CODE)${NC}"
    echo "Response: $RESPONSE_BODY"
fi

echo ""
echo -e "${YELLOW}Test 5: Verify OTP (Manual)${NC}"
echo "To test OTP verification, check server logs for the OTP and run:"
echo ""
echo -e "${BLUE}curl -X POST $API_URL/api/auth/verify-otp \\"
echo "  -H \"Content-Type: application/json\" \\"
echo "  -d '{\"email\": \"$EMAIL\", \"otp\": \"YOUR_OTP\"}'${NC}"

echo ""
echo -e "${YELLOW}Test 6: Reset Password (Manual)${NC}"
echo "To test password reset, use the OTP from server logs and run:"
echo ""
echo -e "${BLUE}curl -X POST $API_URL/api/auth/reset-password \\"
echo "  -H \"Content-Type: application/json\" \\"
echo "  -d '{\"email\": \"$EMAIL\", \"otp\": \"YOUR_OTP\", \"newPassword\": \"NewPass123!\", \"confirmPassword\": \"NewPass123!\"}'${NC}"

echo ""
echo -e "${GREEN}=========================================="
echo "Mobile API Tests Completed!"
echo -e "==========================================${NC}"
echo ""
echo "Summary:"
echo "- Email: $EMAIL"
echo "- Password: $PASSWORD"
echo "- API URL: $API_URL"
echo ""
echo "Next steps:"
echo "1. Check server logs for OTP code"
echo "2. Test OTP verification with the code"
echo "3. Test password reset with the code"
echo ""
echo "Share this documentation with mobile developer:"
echo "- MOBILE_API_DOCUMENTATION.md"
