package io.joinupy.example.mongomisalignedcolorsexample.service

import io.joinupy.example.mongomisalignedcolorsexample.config.StartupDataConfig
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.kotlin.test.test

@SpringBootTest
class ColorServiceTest(
	@Autowired private val startupData: StartupDataConfig,
	@Autowired private val colorService: ColorService,
) {
	@BeforeEach
	fun init() {
		startupData.clean()
		startupData.injectColors()
	}

	@Test
	fun `Find all Colors`() {
		val dbCount = startupData.counts().first
		assertThat(dbCount).isNotZero
		colorService.find()
			.test()
			.expectSubscription()
			.expectNextCount(dbCount)
			.verifyComplete()
	}
}
