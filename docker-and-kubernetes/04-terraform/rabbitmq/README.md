# learned so far:

## clear minikube

run `minikube stop && minikube delete && minikube start`. after that, do not forget to

- add the crds
- recreate the docker images in the minikube docker context as described below!

## add crds

```
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install rabbitmq-cluster-operator bitnami/rabbitmq-cluster-operator
helm uninstall rabbitmq-cluster-operator
```

## use local docker images with minikube:

- run `eval $(minikube docker-env)` to switch to the minikube docker environment.
- build image, e.g. `docker build . -t myimage:latest
- use the image in the terraform script without a registry prefix
- set `imagePullPolicy = "Never"`

## startup

- run `tf init && tf apply`
- the messaging topology operator takes a while to provision the users and their permissions, so be patient; it can take several minutes!

## manage

- use `kr -n <namespace> manage <instance>`, e.g. `kr -n rabbitns manage rabbit`

## users

https://www.rabbitmq.com/kubernetes/operator/using-topology-operator#users-permissions

users and permissions can be created through the messaging-topology-operator. therefore, we need to create a secret per user and then User and Permission kubenetes resources.

# still to research:

## different namespaces for apps and rabbitmq

?

## storage

https://kubernetes.io/docs/concepts/storage/storage-classes/#azure-file

```
➜  rabbitmq git:(master) ✗ kubectl config use-context msf-dev-aks-cluster
Switched to context "msf-dev-aks-cluster".
➜  rabbitmq git:(master) ✗ k get storageclasses
NAME                     PROVISIONER          RECLAIMPOLICY   VOLUMEBINDINGMODE      ALLOWVOLUMEEXPANSION   AGE
akus-rwm-storage-class   file.csi.azure.com   Delete          Immediate              true                   64d <- generates ...-pvs object
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

## node affinity

?
