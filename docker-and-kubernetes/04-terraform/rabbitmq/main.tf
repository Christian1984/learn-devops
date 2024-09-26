
resource "kubernetes_namespace" "rabbitmq_namespace" {
  metadata {
    name = var.rabbitmq_namespace
  }
}

resource "helm_release" "rabbitmq-cluster-operator" {
  name      = "rabbitmq-cluster-operator"
  namespace = kubernetes_namespace.rabbitmq_namespace.metadata[0].name

  repository = "https://charts.bitnami.com/bitnami"
  chart      = "rabbitmq-cluster-operator"
}

resource "kubernetes_manifest" "rabbitmq_cluster" {
  # defining the dependency ensures that the cluster operator takes
  # care of shutting down the cluster before being removed itself
  depends_on = [helm_release.rabbitmq-cluster-operator]
  manifest = {
    apiVersion = "rabbitmq.com/v1beta1"
    kind       = "RabbitmqCluster"
    metadata = {
      name      = var.rabbitmq_instance_name
      namespace = kubernetes_namespace.rabbitmq_namespace.metadata[0].name
    }
    spec = {
      image    = "rabbitmq:4.0.2-management"
      replicas = 3

      # Resource limits and requests for RabbitMQ instances
      # "resources" = {
      #   "requests" = {
      #     "cpu"    = "500m"
      #     "memory" = "1Gi"
      #   }
      #   "limits" = {
      #     "cpu"    = "1"
      #     "memory" = "2Gi"
      #   }
      # }

      # Optional: Storage configuration for RabbitMQ
      # "storage" = {
      #   "storageClassName" = "standard"
      #   "resources" = {
      #     "requests" = {
      #       "storage" = "10Gi"
      #     }
      #   }
      # }
    }
  }
}
