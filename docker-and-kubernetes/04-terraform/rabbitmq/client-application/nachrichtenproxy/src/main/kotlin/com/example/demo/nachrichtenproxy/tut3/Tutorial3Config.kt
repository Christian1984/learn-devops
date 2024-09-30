package com.example.demo.nachrichtenproxy.tut3

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut3", "pub-sub")
@Configuration
class Tutorial3Config {

    @Bean
    fun fanout() = FanoutExchange("tut.fanout")

    @Profile("receiver")
    @Bean
    fun autoDeleteQueue1() = AnonymousQueue()

    @Profile("receiver")
    @Bean
    fun binding1(fanoutExchange: FanoutExchange, autoDeleteQueue1: Queue): Binding? =
        BindingBuilder.bind(autoDeleteQueue1).to(fanoutExchange)

    @Profile("receiver")
    @Bean
    fun autoDeleteQueue2() = AnonymousQueue()

    @Profile("receiver")
    @Bean
    fun binding2(fanoutExchange: FanoutExchange, autoDeleteQueue2: Queue): Binding? =
        BindingBuilder.bind(autoDeleteQueue2).to(fanoutExchange)

    @Profile("receiver")
    @Bean
    fun receiver() = Tut3Receiver()

    @Profile("sender")
    @Bean
    fun sender() = Tut3Sender(20, 3)
}