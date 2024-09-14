#!/bin/bash

# Variables
DB_USER="PFT"
DB_PASS="PFT"
DB_CONNECT_STRING="localhost:1521/DESARROLLO"  # Adjust as needed
SQL_SCRIPT_PATH="/sql/basededatos.sql"

# Check if SQL*Plus is installed
if ! command -v sqlplus &> /dev/null
then
    echo "sqlplus could not be found. Please install Oracle Instant Client or SQL*Plus."
    exit 1
fi

# Execute the SQL script using SQL*Plus
echo "Executing SQL script..."

sqlplus -s "${DB_USER}/${DB_PASS}@${DB_CONNECT_STRING}" << EOF
@${SQL_SCRIPT_PATH}
EXIT;
EOF

# Check for errors
if [ $? -eq 0 ]; then
    echo "SQL script executed successfully."
else
    echo "An error occurred while executing the SQL script."
    exit 1
fi
