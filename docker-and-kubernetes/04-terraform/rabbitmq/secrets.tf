data "kubernetes_secret" "rabbitmq_default_user" {
  metadata {
    name      = "myrabbit-default-user"
    namespace = "default"
  }
}

resource "kubernetes_secret" "rabbitmq_secret_user_producer" {
  metadata {
    name = "myrabbit-secret-user-prod"
  }

  data = {
    username = "producer"
    password = "prod-pw"
  }
}

resource "kubernetes_secret" "rabbitmq_secret_user_consumer" {
  metadata {
    name = "myrabbit-secret-user-cons"
  }

  data = {
    username = "consumer"
    password = "cons-pw"
  }
}
