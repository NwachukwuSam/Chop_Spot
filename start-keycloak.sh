#!/bin/sh
set -e

echo "=== Keycloak Startup Script ==="
echo "Environment variables received:"
echo "POSTGRES_HOST: ${POSTGRES_HOST:-NOT SET}"
echo "POSTGRES_PORT: ${POSTGRES_PORT:-NOT SET}"
echo "POSTGRES_DATABASE: ${POSTGRES_DATABASE:-NOT SET}"
echo "KC_DB_USERNAME: ${KC_DB_USERNAME:-NOT SET}"
echo "KC_DB: ${KC_DB:-NOT SET}"

# Build KC_DB_URL from individual components
if [ -n "$POSTGRES_HOST" ] && [ -n "$POSTGRES_PORT" ] && [ -n "$POSTGRES_DATABASE" ]; then
  export KC_DB_URL="jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DATABASE}"
  echo "Successfully built KC_DB_URL: ${KC_DB_URL}"
else
  echo "ERROR: Missing required database connection parameters"
  exit 1
fi

echo "Starting Keycloak with optimized build..."
exec /opt/keycloak/bin/kc.sh start --optimized
