package com.example.demo.nachrichtenproxy.tut1

import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Tut1Sender {
    @Autowired
    lateinit var template: RabbitTemplate

    @Autowired
    lateinit var queue: Queue

    @PostConstruct
    fun hello() {
        println("hello tut1 sender")
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        val message = "Hello World! It is ${LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))}"
        template.convertAndSend(queue.name, message)
        println(" [x] Sent message [${message}] to queue [${queue.name}]")
    }
}