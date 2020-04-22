package ar.edu.davinci.http

import ar.edu.davinci.http.entities.EntityWithLink
import ar.edu.davinci.util.HttpClient
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class HttpTest[T] {
  val log = org.slf4j.LoggerFactory.getLogger(classOf[HttpTest[_]])

  private val endpoint = "http://localhost:4567/fitme/api" // TODO Tener cuidado con /users
  private val token = "asfa"
  private val httpClient = new HttpClient[T](endpoint, token)

  @ParameterizedTest
  @MethodSource(Array(
    "ar.edu.davinci.http.entities.EntitiesStoreScala#getMealNutritions",
    "ar.edu.davinci.http.entities.EntitiesStoreScala#getWorkoutExercises"
  ))
  def get(entity: EntityWithLink[T]): Unit = {
    val algo = httpClient.get[T](entity.getLink)
    log.info(entity.toString)
  }

  @ParameterizedTest
  @MethodSource(Array(
    "ar.edu.davinci.http.entities.EntitiesStoreScala#getMealNutritions",
    "ar.edu.davinci.http.entities.EntitiesStoreScala#getWorkoutExercises"
  ))
  def post(entity: T): Unit = {
    log.info(entity.toString)
  }

  @ParameterizedTest
  @MethodSource(Array(
    "ar.edu.davinci.http.entities.EntitiesStore#getMealNutritions",
    "ar.edu.davinci.http.entities.EntitiesStore#getWorkoutExercises"))
  def patch(entity: T): Unit = {
    log.info(entity.toString)
  }
}
