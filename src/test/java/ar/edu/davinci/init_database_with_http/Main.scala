package ar.edu.davinci.init_database_with_http

import ar.edu.davinci.util.HttpClient
import ar.edu.davinci.init_database_with_http.routine.{_1MealNutritionTest, _2WorkoutExerciseTest}
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.{JUnit4, Suite}

@RunWith(classOf[JUnit4])
@Suite.SuiteClasses({
  Array(
    classOf[_1MealNutritionTest],
    classOf[_2WorkoutExerciseTest]
  )
})
class Main {

  private val endpoint = "http://localhost:4567/fitme/api" // TODO Tener cuidado con /users
  private val token = "asfa"
  private val httpClient = new HttpClient(endpoint, token)

  @Test
  def init(): Unit = {

    new _1MealNutritionTest().init(httpClient)
    new _2WorkoutExerciseTest().init(httpClient)

    ""
  }
}
