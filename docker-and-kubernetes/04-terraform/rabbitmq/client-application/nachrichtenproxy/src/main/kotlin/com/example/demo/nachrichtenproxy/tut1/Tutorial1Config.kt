package com.example.demo.nachrichtenproxy.tut1

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut1", "hello-world")
@Configuration
class Tutorial1Config {
    @Bean
    fun hello(): Queue = Queue("hello")

    @Profile("receiver")
    @Bean
    fun receiver() = Tut1Receiver()

    @Profile("sender")
    @Bean
    fun sender() = Tut1Sender()
}