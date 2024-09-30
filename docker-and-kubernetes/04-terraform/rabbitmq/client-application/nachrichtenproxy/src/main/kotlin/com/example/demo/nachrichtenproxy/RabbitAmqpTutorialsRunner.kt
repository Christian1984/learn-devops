package com.example.demo.nachrichtenproxy

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext

class RabbitAmqpTutorialsRunner(val ctx: ConfigurableApplicationContext) : CommandLineRunner {
    @Value("\${tutorial.client.duration:0}")
    var duration = 0

    @Value("\${spring.rabbitmq.username:''}")
    var username = ""


    override fun run(vararg args: String?) {
        println("Ready... Communicating as user [$username]")
        if (duration > 0) {
            println("Running for ${duration} ms")
            Thread.sleep(duration.toLong())
            println("Shutdown!")
            ctx.close()
        }
    }
}
