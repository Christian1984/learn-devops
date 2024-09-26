# learned:

## use local docker images with minikube:

- run `eval $(minikube docker-env)` to switch to the minikube docker environment.
- build image, e.g. `docker build . -t myimage:latest
- use the image in the terraform script without a registry prefix
- set `imagePullPolicy = "Never"`

## users

https://www.rabbitmq.com/kubernetes/operator/using-topology-operator#users-permissions

users and permissions can be created through the messaging-topology-operator. therefore, we need to create a secret per user and then User and Permission kubenetes resources.

# research:

## storage

https://kubernetes.io/docs/concepts/storage/storage-classes/#azure-file

```
➜  rabbitmq git:(master) ✗ kubectl config use-context msf-dev-aks-cluster
Switched to context "msf-dev-aks-cluster".
➜  rabbitmq git:(master) ✗ k get storageclasses
NAME                     PROVISIONER          RECLAIMPOLICY   VOLUMEBINDINGMODE      ALLOWVOLUMEEXPANSION   AGE
akus-rwm-storage-class   file.csi.azure.com   Delete          Immediate              true                   64d
azurefile                file.csi.azure.com   Delete          Immediate              true                   285d
azurefile-csi            file.csi.azure.com   Delete          Immediate              true                   285d
azurefile-csi-premium    file.csi.azure.com   Delete          Immediate              true                   285d
azurefile-premium        file.csi.azure.com   Delete          Immediate              true                   285d
default (default)        disk.csi.azure.com   Delete          WaitForFirstConsumer   true                   285d
managed                  disk.csi.azure.com   Delete          WaitForFirstConsumer   true                   285d
managed-csi              disk.csi.azure.com   Delete          WaitForFirstConsumer   true                   285d
managed-csi-premium      disk.csi.azure.com   Delete          WaitForFirstConsumer   true                   285d
managed-premium          disk.csi.azure.com   Delete          WaitForFirstConsumer   true                   285d
```

## tls-secret

?

## monitoring

- prometheus?
- application insights?
