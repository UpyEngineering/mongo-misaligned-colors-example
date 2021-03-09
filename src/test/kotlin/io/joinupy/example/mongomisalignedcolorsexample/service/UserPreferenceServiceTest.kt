package io.joinupy.example.mongomisalignedcolorsexample.service

import brave.Tracing
import io.joinupy.example.mongomisalignedcolorsexample.ColorTest
import io.joinupy.example.mongomisalignedcolorsexample.config.StartupDataConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.kotlin.test.test

@ColorTest
internal class UserPreferenceServiceTest(
	@Autowired private val startupData: StartupDataConfig,
	@Autowired private val userPreferenceService: UserPreferenceService,
) {
	@BeforeEach
	fun init() {
		startupData.clean()
		startupData.injectColors()
	}

	@Test
	fun `findByColor should find all the users that love the blue`() {
		userPreferenceService.findByName("blue")
			.test()
			.expectSubscription()
			.expectNextCount(2)
			.verifyComplete()
	}
}
