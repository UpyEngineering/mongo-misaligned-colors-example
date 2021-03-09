package io.joinupy.example.mongomisalignedcolorsexample.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class UserPreference(
	val color: String,
	val name: String,
	val userId: String,
	@Id
	val id: ObjectId? = null,
)
