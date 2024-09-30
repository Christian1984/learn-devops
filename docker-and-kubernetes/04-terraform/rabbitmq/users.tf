locals {
  rabbitmq_users_common = {
    apiVersion = "rabbitmq.com/v1beta1"
    kind       = "User"
    metadata = {
      namespace = var.rabbitmq_namespace
    }
    spec = {
      rabbitmqClusterReference = {
        name = var.rabbitmq_instance_name
      }
    }
  }
}

resource "kubernetes_manifest" "rabbitmq_user_producer" {
  # defining the dependency ensures that the messaging topology operator 
  # takes care of removing the users before being removed itself
  depends_on = [helm_release.rabbitmq-cluster-operator]
  manifest = merge(local.rabbitmq_users_common, {
    metadata = merge(local.rabbitmq_users_common.metadata, {
      name = "rabbitmq-user-producer"
    })
    spec = merge(local.rabbitmq_users_common.spec, {
      importCredentialsSecret = {
        name = "rabbitmq-secret-user-producer"
      }
    })
  })
}

resource "kubernetes_manifest" "rabbitmq_user_consumer" {
  # defining the dependency ensures that the messaging topology operator 
  # takes care of removing the users before being removed itself
  depends_on = [helm_release.rabbitmq-cluster-operator]
  manifest = merge(local.rabbitmq_users_common, {
    metadata = merge(local.rabbitmq_users_common.metadata, {
      name = "rabbitmq-user-consumer"
    })
    spec = merge(local.rabbitmq_users_common.spec, {
      importCredentialsSecret = {
        name = "rabbitmq-secret-user-consumer"
      }
    })
  })
}
