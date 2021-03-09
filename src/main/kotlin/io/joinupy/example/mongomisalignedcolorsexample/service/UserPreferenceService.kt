package io.joinupy.example.mongomisalignedcolorsexample.service

import io.joinupy.example.mongomisalignedcolorsexample.model.entity.UserPreference
import io.joinupy.example.mongomisalignedcolorsexample.repo.UserPreferenceRepo
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserPreferenceService(
	private val userPreferenceRepo: UserPreferenceRepo,
	private val mongoTemplate: ReactiveMongoTemplate,
) {
	private val logger = LoggerFactory.getLogger(this.javaClass)

	fun findByName(colorName: String?): Flux<UserPreference> {
		return Mono.justOrEmpty(colorName)
			.doOnNext {
				logger.info("broken here")
			}
//			.filter { false }
			.flatMapMany(userPreferenceRepo::findAllByColor)
			.doOnNext { logger.info("$colorName preference of ${it.name} found") }
	}
}
