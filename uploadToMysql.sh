#!/bin/bash

# MySQL credentials
USER="jackie"
PASSWORD="jackie_password"
DATABASE="ai_generated_text"
HOST="localhost"
PORT=3306
groupId=$1

# Directory containing your CSV files
CSV_DIR="/var/lib/mysql-files/group-$groupId"

# Loop through each CSV file in the directory
for CSV_FILE in $CSV_DIR/table*.csv; do
  # Extract the table name from the file name (removing path and .csv extension)
  TABLE_NAME=$(basename "$CSV_FILE" .csv)
  
  # Construct the MySQL LOAD DATA INFILE command
  SQL_CMD="LOAD DATA INFILE '"$CSV_FILE"'
           INTO TABLE $TABLE_NAME
           FIELDS TERMINATED BY ','
           ENCLOSED BY '\"'
           LINES TERMINATED BY '\n'
           IGNORE 0 ROWS;"
  
  # Execute the command
  mysql -u$USER -p$PASSWORD -h$HOST $DATABASE -e "$SQL_CMD"
  
  echo "Data from $CSV_FILE uploaded to $TABLE_NAME"
done