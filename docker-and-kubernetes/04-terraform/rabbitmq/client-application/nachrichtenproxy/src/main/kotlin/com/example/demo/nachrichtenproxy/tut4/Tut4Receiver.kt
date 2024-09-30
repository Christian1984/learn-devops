package com.example.demo.nachrichtenproxy.tut4

import jakarta.annotation.PostConstruct
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch
import java.util.concurrent.atomic.AtomicInteger

class Tut4Receiver() {
    @PostConstruct
    fun hello() {
        println("hello tut4 receiver")
    }

    @RabbitListener(queues = ["#{autoDeleteQueue1.name}"])
    fun receive1(msg: String) = receive(msg, 1)

    @RabbitListener(queues = ["#{autoDeleteQueue2.name}"])
    fun receive2(msg: String) = receive(msg, 2)

    val counts = arrayOf(AtomicInteger(0), AtomicInteger(0))

    private fun receive(msg: String, instance: Int) {
        val watch = StopWatch()
        watch.start()
        println("[${instance}] \t [x] received [${msg}]")
        work(msg)
        watch.stop()
        println("[${instance}] \t [x] done in [${watch.totalTimeSeconds} s], processed ${counts[instance-1].incrementAndGet()} messages")
    }

    private fun work(msg: String) {
        Thread.sleep(500L * msg.count { it == '.' })
    }
}