#!/bin/bash

# Directory containing the command files
COMMAND_DIR="/home/ubuntu/TaoProject/script"

# Continuously scan the directory
while true; do
    # Check if the directory exists
    if [ ! -d "$COMMAND_DIR" ]; then
        echo "Directory does not exist: $COMMAND_DIR"
        # Optionally wait some time before retrying to avoid spamming the log/output
        sleep 10
        continue
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

    # Wait a moment before re-scanning the same directory to avoid high CPU usage
    sleep 1
done

