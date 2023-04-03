This readme and repository covers a code-along of amigos code's Docker and Kubernetes course at https://www.youtube.com/watch?v=bhBSlnQcq2k

# 1 - Run a "Stock" Container From the Docker Image Registry

´docker run --name website -p 8080:80 -d nginx:latest´

with -p forwarding the container port 80 to the host machines port 8080.

## Mounting a Volume

Mount the current working directory from the host machine as a volume with

´docker run --name website -p 8080:80 -v $(pwd):/usr/share/nginx/html -d nginx:latest´

This creates a bidirectional mounting between the host machine's folder and the container's folder.

## Add Content to the Volume

Add content by simply adding files to the current working directory, e.g. ´./01-host-and-containers´

# 2 - Build a "Custom" Container Image

To build a production ready, deployable container, all previously mounted content must be injected into the container image. This is done by creating a Dockerfile and then building an image based on that.

A simple Dockerfile may look like this:

´´´
FROM nginx:latest
ADD . /usr/share/nginx/html
´´´

Then, build an image with

´docker build --tag website:latest .´
