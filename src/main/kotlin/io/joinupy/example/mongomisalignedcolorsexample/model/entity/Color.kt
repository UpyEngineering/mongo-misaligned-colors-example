package io.joinupy.example.mongomisalignedcolorsexample.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Color(
	@Indexed(unique = true)
	val hexCode: String? = null,
	val name: String? = null,
	@Id
	val id: ObjectId? = null,
)
