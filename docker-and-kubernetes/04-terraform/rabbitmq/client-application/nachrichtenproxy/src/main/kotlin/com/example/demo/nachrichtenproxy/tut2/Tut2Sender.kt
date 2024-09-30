package com.example.demo.nachrichtenproxy.tut2

import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger

class Tut2Sender(private val maxMessages: Int, private val maxLength: Int) {
    @Autowired
    lateinit var template: RabbitTemplate

    @Autowired
    lateinit var queue: Queue

    private var count = AtomicInteger(0)

    @PostConstruct
    fun hello() {
        println("hello tut2 sender")
    }

    @Scheduled(fixedDelay = 500, initialDelay = 500)
    fun send() {
        val i = count.incrementAndGet()

        if (i > maxMessages) return

        val dots = i % maxLength + 1
        val arr = Array(dots) { "." }
        val message = "Hello ${arr.joinToString(separator = "")}"
        template.convertAndSend(queue.name, message)
        println(" [x] Sent message [${message}] to queue [${queue.name}]")
    }
}