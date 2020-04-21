package ar.edu.davinci.init_database_with_http.routine

import ar.edu.davinci.domain.model.routine.detail.MealNutrition
import ar.edu.davinci.domain.types.MealNutritionType
import ar.edu.davinci.util.HttpClient
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(classOf[JUnit4])
class _1MealNutritionTest() {

  val mealNutritionList = List(
    new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0),
    new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0),
    new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0),
    new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0),
    new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0),
    new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0),
    new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0),
  )

  @Test
  def init(httpClient: HttpClient): Unit = {
    val response = httpClient.get[MealNutrition]("/meal-nutrition")

    System.out.println("************* Iniciando Meal-nutrition\n")

    mealNutritionList.map(mealNutrition => {
      System.out.println(s"Posteando... ${mealNutrition.getName}")
      httpClient.post[MealNutrition]("/meal-nutrition", mealNutrition)
    })

    assert(response.isRight)
    System.out.println("--- Terminado\n")

  }
}
