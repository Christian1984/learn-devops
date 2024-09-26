locals {
  user_producer_data = {
    username = "producer"
    password = "prod-pw"
  }

  user_consumer_data = {
    username = "consumer"
    password = "cons-pw"
  }
}

data "kubernetes_secret" "rabbitmq_default_user" {
  metadata {
    name      = "rabbitmq-default-user"
    namespace = kubernetes_namespace.rabbitmq_namespace.metadata[0].name
  }
}

resource "kubernetes_secret" "rabbitmq_secret_user_producer" {
  metadata {
    name      = "rabbitmq-secret-user-producer"
    namespace = kubernetes_namespace.rabbitmq_namespace.metadata[0].name
  }

  data = local.user_producer_data
}

resource "kubernetes_secret" "rabbitmq_secret_user_consumer" {
  metadata {
    name      = "rabbitmq-secret-user-consumer"
    namespace = kubernetes_namespace.rabbitmq_namespace.metadata[0].name
  }

  data = local.user_consumer_data
}
