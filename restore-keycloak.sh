#!/bin/bash

# Keycloak PostgreSQL Restore Script
# Usage: ./restore-keycloak.sh backups/keycloak_backup_20240101_120000.sql.gz

if [ -z "$1" ]; then
    echo "Usage: ./restore-keycloak.sh <backup_file.sql.gz>"
    echo "Available backups:"
    ls -lh backups/keycloak_backup_*.sql.gz 2>/dev/null || echo "No backups found"
    exit 1
fi

BACKUP_FILE=$1

if [ ! -f "$BACKUP_FILE" ]; then
    echo "Error: Backup file not found: $BACKUP_FILE"
    exit 1
fi

echo "WARNING: This will replace the current Keycloak database!"
read -p "Are you sure you want to continue? (yes/no): " confirm

if [ "$confirm" != "yes" ]; then
    echo "Restore cancelled"
    exit 0
fi

echo "Stopping Keycloak..."
docker stop delichops-keycloak

echo "Decompressing backup..."
gunzip -c $BACKUP_FILE > /tmp/restore.sql

echo "Restoring database..."
docker exec -i delichops-postgres psql -U keycloak keycloak < /tmp/restore.sql

if [ $? -eq 0 ]; then
    echo "Restore successful!"
    rm /tmp/restore.sql
    
    echo "Starting Keycloak..."
    docker start delichops-keycloak
    
    echo "Restore completed successfully!"
else
    echo "Restore failed!"
    rm /tmp/restore.sql
    exit 1
fi
