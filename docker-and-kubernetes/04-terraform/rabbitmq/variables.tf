variable "rabbitmq_instance_name" {
  description = "The name of the RabbitMQ instance"
  type        = string
  default     = "rabbit"
}

variable "rabbitmq_namespace" {
  description = "The namespace for RabbitMQ"
  type        = string
  default     = "rabbitns"
}

variable "app_namespace" {
  description = "The namespace for the applications"
  type        = string
  default     = "app"
}
