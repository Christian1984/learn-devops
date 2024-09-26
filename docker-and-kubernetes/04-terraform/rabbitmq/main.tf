
# resource "kubernetes_namespace" "rabbitmq-ns" {
#   metadata {
#     name = "r"
#   }
# }

resource "helm_release" "rabbitmq-cluster-operator" {
  name       = "rabbitmq-cluster-operator"
  # namespace  = "r"

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "rabbitmq-cluster-operator"
}

resource "kubernetes_manifest" "rabbitmq_cluster" {
  # defining the dependency ensures that the cluster operator takes
  # care of shutting down the cluster before being removed itself
  depends_on = [ helm_release.rabbitmq-cluster-operator ]
  manifest = {
    apiVersion = "rabbitmq.com/v1beta1"
    kind       = "RabbitmqCluster"
    metadata = {
      name      = "myrabbit"
      namespace = "default"
      # namespace = "r"
    }
    spec = {
      image = "rabbitmq:4.0.2-management" 
      replicas = 3
    }
  }
}
