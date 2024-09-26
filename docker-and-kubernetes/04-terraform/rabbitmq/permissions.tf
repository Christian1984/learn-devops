locals {
  rabbitmq_permission_common = {
    apiVersion = "rabbitmq.com/v1beta1"
    kind       = "Permission"
    metadata = {
      namespace = kubernetes_namespace.rabbitmq_namespace.metadata[0].name
    }
    spec = {
      vhost = "/"
      permissions = {
        read      = ".*"
        write     = ".*"
        configure = ".*"
      }
      rabbitmqClusterReference = {
        name = var.rabbitmq_instance_name
      }
    }
  }
}

resource "kubernetes_manifest" "rabbitmq_user_permissions_producer" {
  manifest = merge(local.rabbitmq_permission_common, {
    metadata = merge(local.rabbitmq_permission_common.metadata, {
      name = "rabbitmq-user-permissions-producer"
    })
    spec = merge(local.rabbitmq_permission_common.spec, {
      user = "producer"
    })
  })
}

resource "kubernetes_manifest" "rabbitmq_user_permissions_consumer" {
  manifest = merge(local.rabbitmq_permission_common, {
    metadata = merge(local.rabbitmq_permission_common.metadata, {
      name = "rabbitmq-user-permissions-consumer"
    })
    spec = merge(local.rabbitmq_permission_common.spec, {
      user = "consumer"
    })
  })
}

# resource "kubernetes_manifest" "rabbitmq_user_permissions_producer" {
#   manifest = merge(local.rabbitmq_permission_common, {
#     apiVersion = "rabbitmq.com/v1beta1"
#     kind       = "Permission"
#     metadata = {
#       name      = "rabbitmq-user-permissions-producer"
#       namespace = kubernetes_namespace.rabbitmq_namespace.metadata[0].name
#     }
#     spec = {
#       vhost = "/"
#       user = "producer"
#       permissions = {
#         read = ".*"
#         write = ".*"
#         configure = ".*"
#       }
#       rabbitmqClusterReference = {
#         name = var.rabbitmq_instance_name
#       }
#     }
#   })
# }

# resource "kubernetes_manifest" "rabbitmq_user_permissions_consumer" {
#   manifest = {
#     apiVersion = "rabbitmq.com/v1beta1"
#     kind       = "Permission"
#     metadata = {
#       name      = "rabbitmq-user-permissions-consumer"
#       namespace = kubernetes_namespace.rabbitmq_namespace.metadata[0].name
#     }
#     spec = {
#       vhost = "/"
#       user = "consumer"
#       permissions = {
#         read = ".*"
#         write = ".*"
#         configure = ".*"
#       }
#       rabbitmqClusterReference = {
#         name = var.rabbitmq_instance_name
#       }
#     }
#   }
# }
