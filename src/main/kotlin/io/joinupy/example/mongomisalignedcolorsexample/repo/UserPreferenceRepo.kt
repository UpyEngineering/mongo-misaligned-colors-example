package io.joinupy.example.mongomisalignedcolorsexample.repo

import io.joinupy.example.mongomisalignedcolorsexample.model.entity.UserPreference
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserPreferenceRepo : ReactiveMongoRepository<UserPreference, ObjectId> {
	fun findAllByColor(color: String): Flux<UserPreference>
	fun findByUserIdAndId(userId: String, id: ObjectId): Mono<UserPreference>
	fun deleteAllByColor(color: String): Mono<Void>
}
