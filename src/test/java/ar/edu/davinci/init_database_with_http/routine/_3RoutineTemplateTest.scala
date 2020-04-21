package ar.edu.davinci.init_database_with_http.routine

import ar.edu.davinci.util.HttpClient
import ar.edu.davinci.domain.model.routine.detail.MealNutrition
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(classOf[JUnit4])
class _3RoutineTemplateTest() {

  @Test
  def init(httpClient: HttpClient): Unit = {
    val response = httpClient.get[MealNutrition]("/meal-nutrition")

    System.out.println("************* Iniciando Workout-exercise")
    assert(response.isRight)
    System.out.println("--- Terminado\n")

  }
}
