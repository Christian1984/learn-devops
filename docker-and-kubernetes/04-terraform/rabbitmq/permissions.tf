locals {
  rabbitmq_permission_common = {
    apiVersion = "rabbitmq.com/v1beta1"
    kind       = "Permission"
    metadata = {
      namespace = "default"
    }
    spec = {
      vhost = "/"
      user  = "producer"
      permissions = {
        read      = ".*"
        write     = ".*"
        configure = ".*"
      }
      rabbitmqClusterReference = {
        name = "myrabbit"
      }
    }
  }
}

resource "kubernetes_manifest" "rabbitmq_user_permissions_producer" {
  manifest = merge(local.rabbitmq_permission_common, {
    metadata = merge(local.rabbitmq_permission_common.metadata, {
      name = "myrabbit-user-permissions-producer"
    })
    spec = merge(local.rabbitmq_permission_common.spec, {
      user = "producer"
    })
  })
}

resource "kubernetes_manifest" "rabbitmq_user_permissions_consumer" {
  manifest = merge(local.rabbitmq_permission_common, {
    metadata = merge(local.rabbitmq_permission_common.metadata, {
      name = "myrabbit-user-permissions-consumer"
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
#       name      = "myrabbit-user-permissions-producer"
#       namespace = "default"
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
#         name = "myrabbit"
#       }
#     }
#   })
# }

# resource "kubernetes_manifest" "rabbitmq_user_permissions_consumer" {
#   manifest = {
#     apiVersion = "rabbitmq.com/v1beta1"
#     kind       = "Permission"
#     metadata = {
#       name      = "myrabbit-user-permissions-consumer"
#       namespace = "default"
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
#         name = "myrabbit"
#       }
#     }
#   }
# }
