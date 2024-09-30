package com.example.demo.nachrichtenproxy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class NachrichtenproxyApplication {

	@Autowired
	lateinit var ctx: ConfigurableApplicationContext

	@Profile("usage_message")
	@Bean
	fun usage(): CommandLineRunner =
		CommandLineRunner {
			println("This app uses Spring Profiles to control its behavior.\n")
			println("Sample usage: java -jar rabbit-tutorials.jar --spring.profiles.active=hello-world,sender")

			println("- Tutorial 1:")
			println("  [x] java -jar target/nachrichtenproxy-0.0.1-SNAPSHOT.jar --spring.profiles.active=tut1,receiver")
			println("  [x] java -jar target/nachrichtenproxy-0.0.1-SNAPSHOT.jar --spring.profiles.active=tut1,sender")
		}

	@Profile("!usage_message")
	@Bean
	fun tutorial(): CommandLineRunner = RabbitAmqpTutorialsRunner(ctx)
}

fun main(args: Array<String>) {
	runApplication<NachrichtenproxyApplication>(*args)
}
