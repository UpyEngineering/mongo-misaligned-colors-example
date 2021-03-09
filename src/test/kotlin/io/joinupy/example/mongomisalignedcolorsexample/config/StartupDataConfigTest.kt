package io.joinupy.example.mongomisalignedcolorsexample.config

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class StartupDataConfigTest(@Autowired private val startupData: StartupDataConfig) {
	@Test
	fun injectColors() {
		assertThat(startupData.counts().first).isEqualTo(0)
		assertThat(startupData.counts().second).isEqualTo(0)
		startupData.injectColors()
		assertThat(startupData.counts().first).isEqualTo(148)
		assertThat(startupData.counts().second).isEqualTo(4)
		startupData.clean()
		assertThat(startupData.counts().first).isEqualTo(0)
		assertThat(startupData.counts().second).isEqualTo(0)
	}
}
