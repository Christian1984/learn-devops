package com.example.demo.nachrichtenproxy.nack

import jakarta.annotation.PostConstruct
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicInteger

class NackReceiver() {
    @PostConstruct
    fun hello() {
        println("hello NACK receiver")
    }

    @RabbitListener(queues = ["#{nackQueue.name}"], containerFactory = "rabbitListenerContainerFactory", concurrency = "2")
    fun receive(msg: String) = doReceive(msg)

    private val count = AtomicInteger(0)

    private fun doReceive(msg: String) {
        val watch = StopWatch()
        watch.start()
        println(" [x] received [${msg}] (at ${LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))})")
        Thread.sleep(1000)
        println(" [x] will throw now! This should send a NACK and re-enqueue the message!)")
        watch.stop()
        throw Exception("Something went wrong. Yikes!")
        println(" [x] done in [${watch.totalTimeSeconds} s], processed ${count.incrementAndGet()} messages")
    }
}