#!/bin/bash

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  Delichops - Start & Test${NC}"
echo -e "${BLUE}========================================${NC}"
echo ""

# Check Docker services
echo -e "${YELLOW}Checking Docker services...${NC}"
if docker ps | grep -q delichops-keycloak; then
    echo -e "${GREEN}✓ Keycloak is running${NC}"
else
    echo -e "${RED}✗ Keycloak not running. Starting...${NC}"
    docker-compose up -d
    echo "Waiting 30 seconds for Keycloak to start..."
    sleep 30
fi

# Check if Spring Boot services are running
echo ""
echo -e "${YELLOW}Checking Spring Boot services...${NC}"

if lsof -i :8761 > /dev/null 2>&1; then
    echo -e "${GREEN}✓ Eureka Server is running${NC}"
else
    echo -e "${RED}✗ Eureka Server not running${NC}"
    echo "Start it with: cd eureka-server && mvn spring-boot:run"
fi

if lsof -i :8081 > /dev/null 2>&1; then
    echo -e "${GREEN}✓ Auth Service is running${NC}"
else
    echo -e "${RED}✗ Auth Service not running${NC}"
    echo "Start it with: cd auth-service && mvn spring-boot:run"
fi

if lsof -i :8080 > /dev/null 2>&1; then
    echo -e "${GREEN}✓ API Gateway is running${NC}"
else
    echo -e "${RED}✗ API Gateway not running${NC}"
    echo "Start it with: cd api-gateway && mvn spring-boot:run"
fi

echo ""
echo -e "${YELLOW}To start all services, run these commands in separate terminals:${NC}"
echo ""
echo "Terminal 1: cd eureka-server && mvn spring-boot:run"
echo "Terminal 2: cd auth-service && mvn spring-boot:run"
echo "Terminal 3: cd api-gateway && mvn spring-boot:run"
echo ""
echo -e "${YELLOW}Once all services are running, execute:${NC}"
echo "./test-all-roles.sh"
echo ""
