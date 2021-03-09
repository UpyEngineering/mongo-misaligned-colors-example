package io.joinupy.example.mongomisalignedcolorsexample

import io.joinupy.example.mongomisalignedcolorsexample.config.StartupDataConfig
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@SpringBootApplication
class MongoMisalignedColorsExampleApplication {
}

fun main(args: Array<String>) {
	runApplication<MongoMisalignedColorsExampleApplication>(*args)
}

@Profile("prod")
@Component
class StartupInjection(private val startupDataConfig: StartupDataConfig): ApplicationListener<ApplicationReadyEvent> {
	private val logger = LoggerFactory.getLogger(this.javaClass)

	override fun onApplicationEvent(event: ApplicationReadyEvent) {
		startupDataConfig.clean()
		startupDataConfig.injectColors()
		logger.info("injection done")
	}
}
