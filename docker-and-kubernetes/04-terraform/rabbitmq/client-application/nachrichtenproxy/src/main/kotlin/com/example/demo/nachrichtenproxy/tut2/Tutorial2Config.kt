package com.example.demo.nachrichtenproxy.tut2

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Profile("tut2", "work-queues")
@Configuration
class Tutorial2Config {
    @Bean
    fun hello(): Queue = Queue("tut2-jobs")

//    @Bean
//    fun receiverContainerFactory(connectionFactory: ConnectionFactory): SimpleRabbitListenerContainerFactory {
//        val factory = SimpleRabbitListenerContainerFactory()
//        factory.setConnectionFactory(connectionFactory)
//        factory.setPrefetchCount(5) // Set prefetch count specific to receiver1
//        return factory
//    }

    @Profile("receiver")
    @Bean
    fun receiver1() = Tut2Receiver(1)

    @Profile("receiver")
    @Bean
    fun receiver2() = Tut2Receiver(2)

    @Profile("sender")
    @Bean
    fun sender() = Tut2Sender(20, 5)
}