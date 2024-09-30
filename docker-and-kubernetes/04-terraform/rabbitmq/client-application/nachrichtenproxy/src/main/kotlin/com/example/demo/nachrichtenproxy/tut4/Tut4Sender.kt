package com.example.demo.nachrichtenproxy.tut4

import jakarta.annotation.PostConstruct
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class Tut4Sender(private val maxMessages: Int, private val maxLength: Int) {
    @PostConstruct
    fun hello() {
        println("hello tut4 sender")
    }

    @Autowired
    lateinit var template: RabbitTemplate

    @Autowired
    lateinit var directExchange: DirectExchange

    private var count = AtomicInteger(0)
    private val colors = arrayOf("orange", "green", "black")

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    fun send() {
        val i = count.incrementAndGet()

        if (i > maxMessages) return

        val key = colors[Random.nextInt(colors.size)]

        val dots = i % maxLength + 1
        val arr = Array(dots) { "." }
        val message = "[${count.get()}] Hello $key ${arr.joinToString(separator = "")}"

        template.convertAndSend(directExchange.name, key, message)
        println(" [x] Sent message [${message}] to fanout exchange [${directExchange.name}], sent ${count.get()} messages")
    }
}