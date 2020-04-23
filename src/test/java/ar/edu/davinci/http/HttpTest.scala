package ar.edu.davinci.http

import ar.edu.davinci.http.entities.EntitiesWithLink
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import scala.util.{Failure, Success}

class HttpTest[T] {
  val log = org.slf4j.LoggerFactory.getLogger(classOf[HttpTest[_]])

  private val endpoint = "http://localhost:4567/fitme/api" // TODO Tener cuidado con /users
  private val token = "asfa"
  private val httpClient = new HttpClient[T](endpoint, token)

  @ParameterizedTest
  @MethodSource(Array(
    "ar.edu.davinci.http.entities.EntitiesStore#mealNutritions",
    "ar.edu.davinci.http.entities.EntitiesStore#workoutExercises"
  ))
  def get(entity: EntitiesWithLink[T]): Unit = {

    log.info(s"******************* Testing Http [GET] - ${entity.getLink}")

    httpClient.get[T](entity.getLink) match {
      case Success(data: T) => log.info(s"Test passed. Result: ${data.toString}")
      case Failure(error) => Assertions.fail(error)
    }
  }

  @ParameterizedTest
  @MethodSource(Array(
    "ar.edu.davinci.http.entities.EntitiesStore#mealNutritions",
    "ar.edu.davinci.http.entities.EntitiesStore#workoutExercises"
  ))
  def post(entity: EntitiesWithLink[T]): Unit = {

    log.info(s"******************* Testing Http [POST] - ${entity.getLink}")

    entity.getListEntity.map(e => {
      log.info(s"Request [POST] ${entity.getLink} - ${e.toString}")
      httpClient.post[T](entity.getLink, e) match {
        case Success(data: T) => log.info(s"Test passed. Result: ${data.toString}")
        case Failure(error) => Assertions.fail(error)
      }
    })
  }

  @ParameterizedTest
  @MethodSource(Array(
    "ar.edu.davinci.http.entities.EntitiesStore#mealNutritions",
    "ar.edu.davinci.http.entities.EntitiesStore#workoutExercises"
  ))
  def patch(entity: EntitiesWithLink[T]): Unit = {
    log.info(entity.toString)
  }
}
