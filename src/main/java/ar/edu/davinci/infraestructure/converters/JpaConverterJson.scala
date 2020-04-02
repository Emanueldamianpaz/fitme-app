package ar.edu.davinci.infraestructure.converters

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import javax.persistence.AttributeConverter

trait JpaConverterJson[T] extends AttributeConverter[T, String] {
  def typeReference: TypeReference[T]

  val objectMapper: ObjectMapper =
    new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)


  override def convertToDatabaseColumn(entity: T): String = {
    objectMapper.writeValueAsString(entity)
  }

  override def convertToEntityAttribute(dbData: String): T = {
    objectMapper.readValue(dbData, typeReference)
  }

}
