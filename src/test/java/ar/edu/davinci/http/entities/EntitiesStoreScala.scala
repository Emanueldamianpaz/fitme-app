package ar.edu.davinci.http.entities

import ar.edu.davinci.domain.model.routine.detail.{MealNutrition, WorkoutExercise}
import ar.edu.davinci.domain.types.{DifficultyType, MealNutritionType, WorkoutExerciseType}

import scala.collection.JavaConverters._

object EntitiesStoreScala {

  val endpointMealNutrition = "/meal-nutrition"
  val endpointWorkoutExercise = "/workout-exercise"

  def getMealNutritions() = {
    Stream(
      new EntityWithLink[MealNutrition](new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0), endpointMealNutrition)
    ).asJava
  }

  def getWorkoutExercises() = {
    Stream(
      new EntityWithLink[WorkoutExercise](new WorkoutExercise("Saltar", WorkoutExerciseType.FLEXIBILITY, "Esto es de prueba", DifficultyType.BASIC), endpointWorkoutExercise)
    ).asJava
  }
}
