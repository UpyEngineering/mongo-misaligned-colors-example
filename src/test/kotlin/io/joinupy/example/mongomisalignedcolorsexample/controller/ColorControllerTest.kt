package io.joinupy.example.mongomisalignedcolorsexample.controller

import io.joinupy.example.mongomisalignedcolorsexample.ColorTest
import io.joinupy.example.mongomisalignedcolorsexample.config.StartupDataConfig
import io.joinupy.example.mongomisalignedcolorsexample.model.entity.UserPreference
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@ColorTest
internal class ColorControllerTest(
	@Autowired private val webTestClient: WebTestClient,
	@Autowired private val startupData: StartupDataConfig,
) {
	@BeforeEach
	fun init() {
		startupData.clean()
		startupData.injectColors()
	}

	@Test
	fun find() {
		webTestClient.get()
			.uri("/")
			.exchange()
			.expectStatus().isOk
			.expectBody<List<UserPreference>>()
			.consumeWith {
				assertThat(it.responseBody).hasSize(startupData.counts().second.toInt())
			}
	}
}
