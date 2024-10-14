package com.example.demo.nachrichtenproxy.nack

import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class NackSender(private val maxMessages: Int) {
    @PostConstruct
    fun hello() {
        println("hello NACK sender")
    }

    @Autowired
    lateinit var template: RabbitTemplate

    @Autowired
    lateinit var directExchange: DirectExchange

    private var count = AtomicInteger(0)

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        val i = count.incrementAndGet()

        if (i > maxMessages) return

        val message = "Hello NACK! This is message $i: It is ${LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))}"

        template.convertAndSend(directExchange.name, "nack", message)
        println(" [x] Sent message [${message}] to direct exchange [${directExchange.name}], sent ${count.get()} messages")
    }
}