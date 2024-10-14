package com.example.demo.nachrichtenproxy.nack

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Profile("nack")
@Configuration
class NackConfig {

    @Bean
    fun direct() = DirectExchange("tut.direct")

    @Profile("receiver")
    @Bean
    fun nackQueue(): Queue = Queue("nack")

    @Profile("receiver")
    @Bean
    fun binding(directExchange: DirectExchange, nackQueue: Queue): Binding? =
        BindingBuilder.bind(nackQueue).to(directExchange).with("nack")

    @Profile("receiver")
    @Bean
    fun receiver() = NackReceiver()

    @Profile("receiver")
    @Bean
    fun rabbitListenerContainerFactory(
        configurer: SimpleRabbitListenerContainerFactoryConfigurer,
        connectionFactory: ConnectionFactory?
    ): SimpleRabbitListenerContainerFactory {
        // Create factory for RabbitListener containers
        val factory = SimpleRabbitListenerContainerFactory()

        // Apply basic config (such as connection details) to the factory
        configurer.configure(factory, connectionFactory)

        // Add custom retry logic with a backoff delay
        factory.setAdviceChain(
            RetryInterceptorBuilder.stateless()
//                .maxAttempts(5) // Max retry attempts
                .backOffOptions(100, 2.0, 10000) // Initial delay 1s, multiplier 2x, max delay 10s
                .build()
        )

        // Optionally, configure more settings like acknowledgment mode, prefetch count, etc.
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO) // Default: Auto acknowledgment
        factory.setPrefetchCount(1) // Fetch one message at a time (optional)

        return factory
    }

    @Profile("sender")
    @Bean
    fun sender() = NackSender(3)

}