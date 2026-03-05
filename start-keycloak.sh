#!/bin/sh
# Build KC_DB_URL from individual components
if [ -n "$POSTGRES_HOST" ] && [ -n "$POSTGRES_PORT" ] && [ -n "$POSTGRES_DATABASE" ]; then
  export KC_DB_URL="jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DATABASE}"
  echo "Built KC_DB_URL: ${KC_DB_URL}"
else
  echo "ERROR: Missing database connection parameters"
  echo "POSTGRES_HOST: ${POSTGRES_HOST}"
  echo "POSTGRES_PORT: ${POSTGRES_PORT}"
  echo "POSTGRES_DATABASE: ${POSTGRES_DATABASE}"
  exit 1
fi

echo "Starting Keycloak..."
echo "KC_DB: ${KC_DB}"
echo "KC_DB_USERNAME: ${KC_DB_USERNAME}"

exec /opt/keycloak/bin/kc.sh start --optimized
