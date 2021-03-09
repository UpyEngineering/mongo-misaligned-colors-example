package io.joinupy.example.mongomisalignedcolorsexample.service

import io.joinupy.example.mongomisalignedcolorsexample.model.entity.Color
import io.joinupy.example.mongomisalignedcolorsexample.repo.ColorRepo
import org.springframework.data.domain.Example
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ColorService(
	private val colorRepo: ColorRepo,
) {
	fun find(name: String? = null): Flux<Color> {
		return colorRepo.findAll(Example.of(Color(name = name)))
	}
}
