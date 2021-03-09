package io.joinupy.example.mongomisalignedcolorsexample.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.joinupy.example.mongomisalignedcolorsexample.model.entity.Color
import io.joinupy.example.mongomisalignedcolorsexample.model.entity.UserPreference
import org.apache.logging.log4j.LogManager
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.count
import org.springframework.data.mongodb.core.remove
import org.springframework.stereotype.Component
import java.io.File

@Component
class StartupDataConfig(
	private val mongoTemplate: MongoTemplate,
	private val objectMapper: ObjectMapper,
) {
	private val logger = LogManager.getLogger(this.javaClass)

	fun counts(): Pair<Long, Long> {
		return mongoTemplate.count<Color>() to mongoTemplate.count<UserPreference>()
	}

	fun clean() {
		mongoTemplate.remove<Color>().all()
		mongoTemplate.remove<UserPreference>().all()
	}

	fun injectColors() {
		mongoTemplate.remove<Color>().all()
			.also { logger.info("Clean up removed ${it.deletedCount} documents from color db") }
		mongoTemplate.remove<UserPreference>().all()
			.also { logger.info("Clean up removed ${it.deletedCount} documents from UserPreference db") }
		insert(Color::class.java, "colors", ::mapToDB)
		insert(UserPreference::class.java, "userPreferences", ::mapToDB)
		logger.info("color db is loaded")
	}

	private fun <T> insert(
		clazz: Class<T>,
		entityName: String,
		mapper: (T) -> T,
	): List<T> {
		return objectMapper.readResource(clazz, "$entityName.json")
			.map { mapper(it) }
			.also { logger.info("$it will be added to the $entityName db") }
			.let { mongoTemplate.insert(it, clazz) }
			.toList()
	}

	private fun fileFromResource(fileName: String): File {
		val classLoader = javaClass.classLoader
		return File(classLoader.getResource(fileName)!!.file)
	}

	private fun <T> ObjectMapper.readResource(clazz: Class<T>, fileName: String): List<T> {
		val type = typeFactory.constructCollectionType(List::class.java, clazz)
		return readValue(fileFromResource(fileName), type)
	}

	private fun mapToDB(toCopy: Color): Color {
		return toCopy.copy(name = toCopy.name?.toLowerCase())
	}

	private fun mapToDB(toCopy: UserPreference): UserPreference {
		return toCopy.copy(color = toCopy.color.toLowerCase())
	}
}

