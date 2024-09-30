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
    namespace = var.rabbitmq_namespace
  }
}

resource "kubernetes_secret" "rabbitmq_secret_user_producer" {
  metadata {
    name      = "rabbitmq-secret-user-producer"
    namespace = var.rabbitmq_namespace
  }

  data = local.user_producer_data
}

resource "kubernetes_secret" "rabbitmq_secret_user_consumer" {
  metadata {
    name      = "rabbitmq-secret-user-consumer"
    namespace = var.rabbitmq_namespace
  }

  data = local.user_consumer_data
}

resource "kubernetes_secret" "app_secret_user_producer" {
  metadata {
    name      = "app-secret-user-producer"
    namespace = var.app_namespace
  }

  data = local.user_producer_data
}

resource "kubernetes_secret" "app_secret_user_consumer" {
  metadata {
    name      = "app-secret-user-consumer"
    namespace = var.app_namespace
  }

  data = local.user_consumer_data
}
