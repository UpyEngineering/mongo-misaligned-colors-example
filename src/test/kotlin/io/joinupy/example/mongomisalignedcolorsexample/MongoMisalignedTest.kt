package io.joinupy.example.mongomisalignedcolorsexample

import brave.Tracing
import io.joinupy.example.mongomisalignedcolorsexample.config.StartupDataConfig
import io.joinupy.example.mongomisalignedcolorsexample.service.ColorService
import io.joinupy.example.mongomisalignedcolorsexample.service.UserPreferenceService
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.kotlin.test.test

@SpringBootTest
class MongoMisalignedTest(
	@Autowired private val startupData: StartupDataConfig,
	@Autowired private val colorService: ColorService,
	@Autowired private val userPreferenceService: UserPreferenceService,
) {
	@BeforeEach
	fun init() {
		startupData.clean()
		startupData.injectColors()

	}

	private val logger = LoggerFactory.getLogger(this.javaClass)

	@Test
	fun `NextSpan && service calling service`() {
		Tracing.current().tracer()
			.apply { withSpanInScope(nextSpan()) }
		val dbCount = startupData.counts()
		assertThat(dbCount.second).isNotZero
		logger.info("starting test")
		colorService.find()
			.flatMap { userPreferenceService.findByName(it.name) }
			.test()
			.expectSubscription()
			.expectNextCount(dbCount.second)
			.verifyComplete()
	}
	@Test
	fun `NextSpan start on a single service works`() {
		Tracing.current().tracer()
			.apply { withSpanInScope(nextSpan().start()) }
		logger.info("Starting: NextSpan start on a single service")
		val dbCount = startupData.counts().first
		assertThat(dbCount).isNotZero
		colorService.find()
			.test()
			.expectSubscription()
			.expectNextCount(dbCount)
			.verifyComplete()
	}
}
