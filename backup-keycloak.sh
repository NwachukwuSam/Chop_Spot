#!/bin/bash

# Keycloak PostgreSQL Backup Script
# Run this daily with cron: 0 2 * * * /path/to/backup-keycloak.sh

BACKUP_DIR="./backups"
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="$BACKUP_DIR/keycloak_backup_$DATE.sql"

# Create backup directory if it doesn't exist
mkdir -p $BACKUP_DIR

echo "Starting Keycloak database backup..."

# Backup PostgreSQL database
docker exec delichops-postgres pg_dump -U keycloak keycloak > $BACKUP_FILE

if [ $? -eq 0 ]; then
    echo "Backup successful: $BACKUP_FILE"
    
    # Compress the backup
    gzip $BACKUP_FILE
    echo "Compressed: $BACKUP_FILE.gz"
    
    # Keep only last 7 days of backups
    find $BACKUP_DIR -name "keycloak_backup_*.sql.gz" -mtime +7 -delete
    echo "Old backups cleaned up (kept last 7 days)"
else
    echo "Backup failed!"
    exit 1
fi

echo "Backup completed successfully!"
