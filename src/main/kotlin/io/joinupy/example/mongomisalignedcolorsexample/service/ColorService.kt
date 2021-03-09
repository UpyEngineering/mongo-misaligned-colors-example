package io.joinupy.example.mongomisalignedcolorsexample.service

import io.joinupy.example.mongomisalignedcolorsexample.model.entity.Color
import io.joinupy.example.mongomisalignedcolorsexample.repo.ColorRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ColorService(
	private val colorRepo: ColorRepo,
) {
	private val logger = LoggerFactory.getLogger(this.javaClass)
	fun find(name: String? = null): Flux<Color> {
		return Mono.fromSupplier {
			logger.info("searching with the query name = $name")
			""
		}
			.flatMapMany { colorRepo.findAll() }
	}
}
