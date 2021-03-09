package io.joinupy.example.mongomisalignedcolorsexample.repo

import io.joinupy.example.mongomisalignedcolorsexample.model.entity.Color
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ColorRepo : ReactiveMongoRepository<Color, ObjectId>

