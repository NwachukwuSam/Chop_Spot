#!/bin/sh
# Convert DATABASE_URL (postgres://...) to JDBC format (jdbc:postgresql://...)
if [ -n "$DATABASE_URL" ]; then
  export KC_DB_URL=$(echo $DATABASE_URL | sed 's/^postgres:/jdbc:postgresql:/')
  echo "Converted DATABASE_URL to KC_DB_URL: ${KC_DB_URL}"
else
  echo "WARNING: DATABASE_URL not set"
fi

echo "Starting Keycloak..."
echo "KC_DB: ${KC_DB}"
echo "KC_DB_USERNAME: ${KC_DB_USERNAME}"
echo "KC_DB_URL: ${KC_DB_URL}"

exec /opt/keycloak/bin/kc.sh start --optimized
