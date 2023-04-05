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

## Executing Commands: RUN vs CMD

There are two commands that allow you to execute code inside the container. ´RUN´ and ´CMD´, with the difference being:

> RUN is an image build step, the state of the container after a RUN command will be committed to the container image. A Dockerfile can have many RUN steps that layer on top of one another to build the image.
>
> CMD is the command the container executes by default when you launch the built image. A Dockerfile will only use the final CMD defined. The CMD can be overridden when starting a container with docker run $image $other_command.

(https://stackoverflow.com/a/37462208/1934396)

## .dockerignore

Similar to ´.gitignore´, a ´.dockerignore´ file should be created to exclude certain files and directories from an image. In a node project, for example, a ´.dockerignore´ file might look like this:

´´´
node_modules/
.git/

.dockerignore
Dockerfile
´´´

Note that the ´Dockerfile´ will be copied to the image itself on the ´ADD´ command if it sits in the same folder as the content that is supposed to be added, so it can/should be ignored here.

## Execution Order, Caching and Layers

If you copy the `package.json` and `yarn.lock` files before copying the actual source code, docker can use layer caching mechanisms so that it does nor have to run `yarn install` over and over again.

## Alpine

For most images, there is an Alpine-Version available in the official docker registry/hub. Alpine is a lightweight linux distro that focuses on security and a minimal footprint. Images based on Alpine can be 10x smaller than the respective non-Alpine images, so make sure to reference those images in your own Dockerfiles

## Versioning and Tagging

When building an Image for production, make sure to specify a precise, versioned tag for the source image in the ´FROM´ line. Otherwise builds will be non-deterministic and images may break in the future when they are rebuild at a later point in time. For example, use

´FROM node:18.15.0-alpine3.16´

instead of

´FROM node:latest´

When tagging your own images, first create a version-tagged image like so

´docker built -t website:1 .´

and then create the latest tag from it by calling

´docker tag website:1 website:latest´

## Debugging

Inspect a container config with

´docker inspect <container_name|container_id>´

Monitor a container's logs with

´docker logs <container_name|container_id>´

# Kubernetes

At its core, Kubernetes uses **Pods** as an abstraction over individual containers.

Pods run as **Services** and services can communicate with each other via TCP/IP.

An **Ingress** exposes a service to the public.

**Deployments** and **Stateful Sets** are abstractions over Services, that allow for spreading an application over multiple **Nodes** (i.e. machines).

Data is stored in **Storages**. A cluster is configured via a **ConfigMap** and secrets are stored in a **Secrets** component.

## Testing Locally

**minikube** is a tool that allows developers to test kubernetes deployments locally. It creates a 1-node-Kubernetes-Cluster inside a VirtualBox on a local machine.

**kubectl**, on the other hand, is a CLI tool for managing Kubernetes clusters.

Install minikube with `brew install minikube`. Then run it with `minikube start`. This will use docker as default driver. An alternative is hyperkit which can also be installed via brew and then be activated with `minikube start --vm-driver=hyperkit`.

Use `kubectl get nodes` to see the status of the cluster nodes. Use `minikube status` to see the status of the minikube cluster.

## Setting up the Cluster

`kubectl create` is there to create Kubernetes entities. However, Pods are not created "manually" but via the abstraction layer of a **Deployment**.

An nginx deployment from an nginx docker image, for example, can be created with

`kubectl create deployment nginx-depl --image=nginx`

To stop/delete the deployment run `kubectl delete deployment <deployment-name>` (get name via `kubectl get deployment`).

## Debugging

Use `kubectl get <pod|deployment|replicaset>` to list all entities of a given type.

`kubectl logs <pod-name>` will print out the logs for the selected pod.

Use `kubectl describe <pod|deployment|replicaset> <name>` to get details about a given entity.

Get an interactive shell of a pod with `kubectl exec -it <pod-name> -- bin/bash`.

## Applying Config Files

`kubectl apply -f <configfile.yml>` takes a configuration file and applies it to the cluster.

`kubectl delete -f <configfile.yml>` deletes the deployment created with `<configfile.yml>`.

## Example Application

An examplatory application could consist of a mongodb and mongo express, with mongodb running as an internal service and mongo express being the only component exposed.

This setup will consist of:

- MongoDB pod as internal service
- Mongo Express pod as external service
- a config map
- a secret

### Secret

Values in a Kubernetes secret must be base64 encoded. This can easily be done in the terminal by typing

`echo -n 'username' | base64`

First create and `kubectl apply` the secret, then reference it from the deployment file with the `valueFrom`-field.

### Expose a Service with Minikube

At the end, after creating the external service and assigning it a `nodePort` (range 30000...32767), expose the service with

`minikube service <service-name>`

This will expose the service and open a browser window navigating to the proper url.

## Spinning it All up at Once

Use

`kubectl apply -f .`

and

`kubectl delete -f .`

respectively to spin up/stop the entire configuration at once.
