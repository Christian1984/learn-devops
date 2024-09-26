locals {
  common_env = [
    {
      name  = "TUTORIAL_CLIENT_DURATION"
      value = "-1"
    },
    {
      name  = "SPRING_RABBITMQ_HOST"
      value = "myrabbit.default.svc.cluster.local"
    },
  ]
}

resource "kubernetes_manifest" "mq-demo-sender" {
  manifest = {
    apiVersion = "apps/v1"
    kind       = "Deployment"
    metadata = {
      name      = "mq-demo-sender"
      namespace = "default"
    }
    spec = {
      replicas = 1
      selector = {
        matchLabels = {
          app = "mq-demo-sender"
        }
      }
      template = {
        metadata = {
          labels = {
            app = "mq-demo-sender"
          }
        }
        spec = {
          containers = [
            {
              name            = "mq-demo-sender"
              image           = "mq-demo:latest"
              imagePullPolicy = "Never"
              env = concat(local.common_env, [
                {
                  name  = "SPRING_PROFILES_ACTIVE"
                  value = "tut1,sender"
                },
                {
                  name = "SPRING_RABBITMQ_USERNAME"
                  valueFrom = {
                    secretKeyRef = {
                      # name = data.kubernetes_secret.rabbitmq_default_user.metadata[0].name
                      name = "myrabbit-secret-user-prod"
                      key  = "username"
                    }
                  }
                },
                {
                  name = "SPRING_RABBITMQ_PASSWORD"
                  # value = "wrong"
                  valueFrom = {
                    secretKeyRef = {
                      # name = data.kubernetes_secret.rabbitmq_default_user.metadata[0].name
                      name = "myrabbit-secret-user-prod"
                      key  = "password"
                    }
                  }
                },
              ])
            }
          ]
        }
      }
    }
  }
}

resource "kubernetes_manifest" "mq-demo-receiver" {
  manifest = {
    apiVersion = "apps/v1"
    kind       = "Deployment"
    metadata = {
      name      = "mq-demo-receiver"
      namespace = "default"
    }
    spec = {
      replicas = 1
      selector = {
        matchLabels = {
          app = "mq-demo-receiver"
        }
      }
      template = {
        metadata = {
          labels = {
            app = "mq-demo-receiver"
          }
        }
        spec = {
          containers = [
            {
              name            = "mq-demo-receiver"
              image           = "mq-demo:latest"
              imagePullPolicy = "Never"
              env = concat(local.common_env, [
                {
                  name  = "SPRING_PROFILES_ACTIVE"
                  value = "tut1,receiver"
                },
                {
                  name = "SPRING_RABBITMQ_USERNAME"
                  valueFrom = {
                    secretKeyRef = {
                      # name = data.kubernetes_secret.rabbitmq_default_user.metadata[0].name
                      name = "myrabbit-secret-user-cons"
                      key  = "username"
                    }
                  }
                },
                {
                  name = "SPRING_RABBITMQ_PASSWORD"
                  valueFrom = {
                    secretKeyRef = {
                      # name = data.kubernetes_secret.rabbitmq_default_user.metadata[0].name
                      name = "myrabbit-secret-user-cons"
                      key  = "password"
                    }
                  }
                },
              ])
            }
          ]
        }
      }
    }
  }
}
