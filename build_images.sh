#!/bin/bash

# Array of microservice names
microservices=("discovery-server" "api-gateway" "order-service" "product-service" "inventory-service" "notification-service")

# Base directory path for the microservices
base_dir="$HOME/ecommerce-platform"

# Function to package and build Docker images
build_images() {
  for service in "${microservices[@]}"; do
    echo "Packaging and building Docker image for $service..."

    # Navigate to the service directory
    if cd "$base_dir/$service"; then
      # Run Maven package
      echo "Running Maven package for $service..."
      mvn package

      # Check if Maven build was successful
      if [ $? -eq 0 ]; then
        echo "Maven build for $service completed successfully."
        # Build the Docker image
        docker build -t $service .

        # Check if Docker build was successful
        if [ $? -eq 0 ]; then
          echo "$service image built successfully."
        else
          echo "Failed to build Docker image for $service."
          return 1  # Use return when the script is sourced; exit otherwise.
        fi
      else
        echo "Failed to complete Maven build for $service."
        return 1  # Use return when the script is sourced; exit otherwise.
      fi
    else
      echo "Failed to navigate to $service directory."
      return 1  # Use return when the script is sourced; exit otherwise.
    fi
  done
}

# Start the build process
build_images
result=$?

if [ $result -eq 0 ]; then
  echo "All images have been built successfully."
else
  echo "One or more images failed to build."
fi
