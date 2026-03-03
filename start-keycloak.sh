#!/bin/sh
exec /opt/keycloak/bin/kc.sh start --http-port=${PORT:-8080} --hostname-strict=false --proxy=edge
