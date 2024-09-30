package com.example.demo.nachrichtenproxy.tut1

import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RabbitListener(queues = ["hello"])
class Tut1Receiver {
    @PostConstruct
    fun hello() {
        println("hello tut1 receiver")
    }

    @RabbitHandler
    fun receive(msg: String) {
        println(" [x] received [${msg}]")
    }
}