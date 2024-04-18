#!/bin/bash

# Directory containing the command files
COMMAND_DIR="/Users/nannan/IdeaProjects/TaoProject/script"

# Check if the directory exists
if [ ! -d "$COMMAND_DIR" ]; then
    echo "Directory does not exist: $COMMAND_DIR"
    exit 1
fi

# Loop through each file in the directory
for command_file in "$COMMAND_DIR"/*; do
    # Check if the file is a regular file
    if [ -f "$command_file" ]; then
        echo "Executing commands in: $command_file"
        # Execute the commands in the file
        while IFS= read -r cmd; do
            echo "Running: $cmd"
            eval "$cmd"
        done < "$command_file"
    fi
done
