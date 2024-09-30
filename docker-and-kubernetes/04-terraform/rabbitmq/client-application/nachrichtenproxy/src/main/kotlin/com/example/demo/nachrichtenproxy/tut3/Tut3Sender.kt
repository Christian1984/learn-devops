package com.example.demo.nachrichtenproxy.tut3

import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger

class Tut3Sender(private val maxMessages: Int, private val maxLength: Int) {
    @PostConstruct
    fun hello() {
        println("hello tut3 sender")
    }

    @Autowired
    lateinit var template: RabbitTemplate

    @Autowired
    lateinit var fanoutExchange: FanoutExchange

    private var count = AtomicInteger(0)

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        val i = count.incrementAndGet()

        if (i > maxMessages) return

        val dots = i % maxLength + 1
        val arr = Array(dots) { "." }
        val message = "[${count.get()}] Hello ${arr.joinToString(separator = "")}"
        template.convertAndSend(fanoutExchange.name, "", message)
        println(" [x] Sent message [${message}] to fanout exchange [${fanoutExchange.name}], sent ${count.get()} messages")
    }
}