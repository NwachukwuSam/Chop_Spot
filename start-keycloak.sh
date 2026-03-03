#!/bin/sh
exec /opt/keycloak/bin/kc.sh start --optimized --http-port=${PORT:-8080} --hostname-strict=false --proxy=edge
