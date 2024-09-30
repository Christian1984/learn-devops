package com.example.demo.nachrichtenproxy.tut2

import jakarta.annotation.PostConstruct
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.util.StopWatch

//@RabbitListener(queues = ["tut2-jobs"], containerFactory = "receiverContainerFactory")
@RabbitListener(queues = ["tut2-jobs"])
class Tut2Receiver(private val instance: Int) {
    @PostConstruct
    fun hello() {
        println("[${instance}] hello tut2 receiver")
    }

    @RabbitHandler
    fun receive(msg: String) {
        val watch = StopWatch()
        watch.start()
        println("[${instance}] \t [x] received [${msg}]")
        work(msg)
        watch.stop()
        println("[${instance}] \t [x] done in [${watch.totalTimeSeconds} s]")
    }

    private fun work(msg: String) {
        Thread.sleep(500L * msg.count { it == '.' })
    }
}