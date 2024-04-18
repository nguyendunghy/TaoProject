#!/bin/bash

# Directory containing the command files
COMMAND_DIR="/home/ubuntu/TaoProject/script"
TARGET_DIR="/home/ubuntu/TaoProject/processed"
mkdir $COMMAND_DIR
mkdir $TARGET_DIR
# Continuously scan the directory
while true; do
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
            mv "$command_file" "$TARGET_DIR"
        fi
    done

    # Wait a moment before re-scanning the same directory to avoid high CPU usage
    sleep 1
done

