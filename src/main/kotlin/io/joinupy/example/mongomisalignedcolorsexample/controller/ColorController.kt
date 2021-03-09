package io.joinupy.example.mongomisalignedcolorsexample.controller

import io.joinupy.example.mongomisalignedcolorsexample.model.entity.UserPreference
import io.joinupy.example.mongomisalignedcolorsexample.service.ColorService
import io.joinupy.example.mongomisalignedcolorsexample.service.UserPreferenceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class ColorController(
	private val colorService: ColorService,
	private val preferenceService: UserPreferenceService,
) {
	@GetMapping("/")
	fun find(): Flux<UserPreference> {
		return colorService.find()
			.flatMap { preferenceService.findByName(it.name) }
	}

	@GetMapping("/init")
	fun init(): Flux<UserPreference> {
		return colorService.find()
			.flatMap { preferenceService.findByName(it.name) }
	}
}
