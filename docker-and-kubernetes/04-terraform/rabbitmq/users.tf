locals {
  # depends_on = [ helm_release.rabbitmq-cluster-operator ]
  rabbitmq_users_common = {
    apiVersion = "rabbitmq.com/v1beta1"
    kind       = "User"
    metadata = {
      namespace = "default"
    }
    spec = {
      rabbitmqClusterReference = {
        name = "myrabbit"
      }
    }
  }
}

resource "kubernetes_manifest" "rabbitmq_user_producer" {
  manifest = merge(local.rabbitmq_users_common, {
    metadata = merge(local.rabbitmq_users_common.metadata, {
      name = "myrabbit-user-producer"
    })
    spec = merge(local.rabbitmq_users_common.spec, {
      importCredentialsSecret = {
        name = "myrabbit-secret-user-prod"
      }
    })
  })
}

resource "kubernetes_manifest" "rabbitmq_user_consumer" {
  manifest = merge(local.rabbitmq_users_common, {
    metadata = merge(local.rabbitmq_users_common.metadata, {
      name = "myrabbit-user-consumer"
    })
    spec = merge(local.rabbitmq_users_common.spec, {
      importCredentialsSecret = {
        name = "myrabbit-secret-user-consumer"
      }
    })
  })
}

# resource "kubernetes_manifest" "rabbitmq_user_producer" {
#   # depends_on = [ helm_release.rabbitmq-cluster-operator ]
#   manifest = {
#     apiVersion = "rabbitmq.com/v1beta1"
#     kind       = "User"
#     metadata = {
#       name      = "myrabbit-user-producer"
#       namespace = "default"
#     }
#     spec = {
#       rabbitmqClusterReference = {
#         name = "myrabbit"
#       }
#       importCredentialsSecret = {
#         name = "myrabbit-secret-user-prod"
#       }
#     }
#   }
# }

# resource "kubernetes_manifest" "rabbitmq_user_consumer" {
#   # depends_on = [ helm_release.rabbitmq-cluster-operator ]
#   manifest = {
#     apiVersion = "rabbitmq.com/v1beta1"
#     kind       = "User"
#     metadata = {
#       name      = "myrabbit-user-consumer"
#       namespace = "default"
#     }
#     spec = {
#       rabbitmqClusterReference = {
#         name = "myrabbit"
#       }
#       importCredentialsSecret = {
#         name = "myrabbit-secret-user-cons"
#       }
#     }
#   }
# }
