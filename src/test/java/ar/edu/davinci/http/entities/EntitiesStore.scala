package ar.edu.davinci.http.entities

import ar.edu.davinci.domain.model.routine.RoutineTemplate
import ar.edu.davinci.domain.model.routine.detail.{MealNutrition, WorkoutExercise}
import ar.edu.davinci.domain.types._

import scala.collection.JavaConverters._

object EntitiesStore {

  val endpointMealNutrition = "/meal-nutrition"
  val endpointWorkoutExercise = "/workout-exercise"

  val mealNutritionList = List(
    new MealNutrition("1 kiwi/café con leche desnatada", MealNutritionType.BREAKFAST, 80.0),
    new MealNutrition("4 tostadas", MealNutritionType.TEA, 50.0)
  )

  val workoutExerciseList = List(
    new WorkoutExercise("Saltar", WorkoutExerciseType.AEROBIC, "Esto es de prueba", DifficultyType.BASIC),
    new WorkoutExercise("Correr", WorkoutExerciseType.AEROBIC, "Esto es de prueba", DifficultyType.BEGINNER)
  )

  val routineTemplateList = List(
    new RoutineTemplate("Rutina para saltar", "Esta rutina está diseñada para saltar", ScoringType.UNKNOWN, workoutExerciseList.toSet[WorkoutExercise].asJava, mealNutritionList.toSet[MealNutrition].asJava, GoalType.UNKNOWN)
  )

  def mealNutritions() = {

    List(
      new EntitiesWithLink[MealNutrition](mealNutritionList, endpointMealNutrition)
    ).asJava
  }

  def workoutExercises() = {
    List(
      new EntitiesWithLink[WorkoutExercise](workoutExerciseList, endpointWorkoutExercise)
    ).asJava
  }

  def routineTemplates() = {
    List(new EntitiesWithLink[RoutineTemplate](routineTemplateList, endpointWorkoutExercise)
    )
  }

}
