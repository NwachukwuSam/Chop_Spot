#!/bin/sh
echo "Starting Keycloak with database: ${KC_DB_URL}"
exec /opt/keycloak/bin/kc.sh start --optimized
