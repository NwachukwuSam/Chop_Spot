#!/bin/bash

echo "Checking Keycloak status..."
echo ""

# Check if container is running
if docker ps | grep -q keycloak; then
    echo "✅ Keycloak container is running"
else
    echo "❌ Keycloak container is not running"
    echo "Run: /snap/bin/docker-compose up -d"
    exit 1
fi

echo ""
echo "Checking if Keycloak is ready..."
echo ""

# Check logs for startup message
if docker logs delichops-keycloak 2>&1 | grep -q "Listening on"; then
    echo "✅ Keycloak is READY!"
    echo ""
    echo "Access Keycloak at: http://localhost:9090"
    echo "Login: admin / admin"
    echo ""
    echo "Next steps:"
    echo "1. Open KEYCLOAK_QUICK_SETUP.md"
    echo "2. Follow the setup guide"
    echo "3. Get your client secret"
    echo ""
else
    echo "⏳ Keycloak is still starting..."
    echo ""
    echo "Please wait 30-60 more seconds and run this script again:"
    echo "./check-keycloak.sh"
    echo ""
fi
