package com.example.demo.nachrichtenproxy.tut4

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("tut4", "direct")
@Configuration
class Tutorial4Config {

    @Bean
    fun direct() = DirectExchange("tut.direct")

    @Profile("receiver")
    @Bean
    fun autoDeleteQueue1() = AnonymousQueue()

    @Profile("receiver")
    @Bean
    fun binding1a(directExchange: DirectExchange, autoDeleteQueue1: Queue): Binding? =
        BindingBuilder.bind(autoDeleteQueue1).to(directExchange).with("orange")

    @Profile("receiver")
    @Bean
    fun binding1b(directExchange: DirectExchange, autoDeleteQueue1: Queue): Binding? =
        BindingBuilder.bind(autoDeleteQueue1).to(directExchange).with("black")

    @Profile("receiver")
    @Bean
    fun autoDeleteQueue2() = AnonymousQueue()

    @Profile("receiver")
    @Bean
    fun binding2a(directExchange: DirectExchange, autoDeleteQueue2: Queue): Binding? =
        BindingBuilder.bind(autoDeleteQueue2).to(directExchange).with("green")

    @Profile("receiver")
    @Bean
    fun binding2b(directExchange: DirectExchange, autoDeleteQueue2: Queue): Binding? =
        BindingBuilder.bind(autoDeleteQueue2).to(directExchange).with("black")

    @Profile("receiver")
    @Bean
    fun receiver() = Tut4Receiver()

    @Profile("sender")
    @Bean
    fun sender() = Tut4Sender(20, 3)
}