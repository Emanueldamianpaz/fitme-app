package ar.edu.davinci.http.entities

import ar.edu.davinci.domain.model.routine.RoutineTemplate
import ar.edu.davinci.domain.model.routine.detail.{MealNutrition, WorkoutExercise}
import ar.edu.davinci.domain.types._

import scala.collection.JavaConverters._

object EntitiesStore {

  val endpointMealNutrition = "/meal-nutrition"
  val endpointWorkoutExercise = "/workout-exercise"
  val endpointRoutineTemplate = "/routine-template"

  val mealNutritionList = List(
    new MealNutrition("4 tostadas", MealNutritionType.TEA, 50.0),
    new MealNutrition("Kiwi", MealNutritionType.BREAKFAST, 100.0),
    new MealNutrition("Pechugas de pollo", MealNutritionType.LUNCH, 450.0),
    new MealNutrition("Manzana", MealNutritionType.BREAKFAST, 150.0),
    new MealNutrition("Zumo de naranja", MealNutritionType.TEA, 80.0),
    new MealNutrition("Yogur desnatado con fruta", MealNutritionType.BREAKFAST, 120.0),
    new MealNutrition("Fresas troceadas", MealNutritionType.ELEVENSES, 200.0),
    new MealNutrition("Café con leche", MealNutritionType.BREAKFAST, 150.0),
    new MealNutrition("Filetes de perca a la plancha", MealNutritionType.LUNCH, 500.0),
    new MealNutrition("Puñado de nueces", MealNutritionType.BRUNCH, 50.0),
    new MealNutrition("Filete de pavo con ensalada verde", MealNutritionType.LUNCH, 500.0),
    new MealNutrition("Zumo de melocotón", MealNutritionType.TEA, 80.0),
    new MealNutrition("Brócoli con refrito de ajos", MealNutritionType.DINNER, 200.0),
    new MealNutrition("Un bol de cerezas", MealNutritionType.BRUNCH, 140.0),
    new MealNutrition("Ensalada de tomate y mozarella fresca con orégano", MealNutritionType.DINNER, 180.0),
    new MealNutrition("Filete de ternera con ensalada de canónigos", MealNutritionType.LUNCH, 600.0)
  )

  val workoutExerciseList = List(
    new WorkoutExercise("Ejercicios con bandas elásticas de resistencia", WorkoutExerciseType.STRENGTH, "Equipo necesario: bandas elásticas de resistencia o para ejercicios, ropa cómoda", DifficultyType.BASIC),
    new WorkoutExercise("Levantamiento de pesas", WorkoutExerciseType.STRENGTH, "Equipo necesario: barras y discos, mancuernas, pesas rusas, máquinas de fuerza, ropa cómoda", DifficultyType.BASIC),
    new WorkoutExercise("Andar en bicicleta", WorkoutExerciseType.AEROBIC, "Equipo necesario: bicicleta, ropa cómoda, casco", DifficultyType.INTERMEDIATE),
    new WorkoutExercise("Senderismo", WorkoutExerciseType.AEROBIC, "Equipo necesario: botas o zapatos para senderismo, calcetines, ropa cómoda, adecuada al clima, botellas de agua; gorra, mochila", DifficultyType.ADVANCED),
    new WorkoutExercise("Baloncesto", WorkoutExerciseType.AEROBIC, "Equipo necesario: calzado deportivo de baloncesto, ropa cómoda, pelota de baloncesto, aro", DifficultyType.BASIC),
    new WorkoutExercise("Caminar", WorkoutExerciseType.AEROBIC, "Equipo necesario: calzado para caminar, ropa cómoda", DifficultyType.BEGINNER),
    new WorkoutExercise("Correr y trotar", WorkoutExerciseType.AEROBIC, "Equipo necesario: calzado para correr, pantalones cómodos, cortos o para correr, camiseta cómoda, sostén deportivo", DifficultyType.BEGINNER),
    new WorkoutExercise("Hacer rodar un cilindro de goma espuma", WorkoutExerciseType.FLEXIBILITY, "Equipo necesario: cilindro de goma espuma o bola de lacrosse", DifficultyType.BEGINNER),
    new WorkoutExercise("Yoga", WorkoutExerciseType.FLEXIBILITY, "Equipo necesario: colchoneta de yoga, ropa cómoda", DifficultyType.BEGINNER),
    new WorkoutExercise("Pilates", WorkoutExerciseType.FLEXIBILITY, "Equipo necesario: colchoneta, ropa cómoda; aparatos que se pueden usar en un centro de Pilates", DifficultyType.BEGINNER),
    new WorkoutExercise("Estiramiento", WorkoutExerciseType.FLEXIBILITY, "Equipo necesario: en realidad, nada; se puede usar una toalla o un cinturón para profundizar algunos estiramientos sosteniendo la toalla con ambas manos y esforzándote con cuidado para lograr el estiramiento)", DifficultyType.BEGINNER),
    new WorkoutExercise("Esquí", WorkoutExerciseType.AEROBIC, "Equipo necesario: esquíes, bastones, botas, casco, gorra, gafas de esquí, chaquetas, pantalones o pecheras aislantes, guantes y calcetines", DifficultyType.ADVANCED),
    new WorkoutExercise("Máquina elíptica* o escalador", WorkoutExerciseType.AEROBIC, "Equipo necesario: máquina elíptica o escalador, ropa cómoda", DifficultyType.INTERMEDIATE),
    new WorkoutExercise("Golf", WorkoutExerciseType.AEROBIC, "Equipo necesario: palos, bolsa, zapatos, pelotas, tees (soportes para pelotas), ropa cómoda (en algunos campos de golf se pueden alquilar los palos y la bolsa)", DifficultyType.BASIC),
    new WorkoutExercise("Patinaje", WorkoutExerciseType.AEROBIC, "Equipo necesario: patines para hielo, con ruedas o rollers, calcetines, ropa cómoda, protectores de muñecas y codos, casco", DifficultyType.BASIC),
    new WorkoutExercise("Ejercicios con pesas rusas", WorkoutExerciseType.STRENGTH, "Equipo necesario: pesas rusas, ropa cómoda", DifficultyType.BASIC),
    new WorkoutExercise("Tenis*", WorkoutExerciseType.AEROBIC, "Equipo necesario: raqueta, pelotas, ropa cómoda, calzado deportivo de tenis", DifficultyType.BASIC),
    new WorkoutExercise("Remo*", WorkoutExerciseType.AEROBIC, "Equipo necesario: ropa cómoda, bote y remos, si haces remo al aire libre; máquina de remar, si lo haces en un lugar bajo techo", DifficultyType.ADVANCED),
    new WorkoutExercise("Ejercicios aeróbicos", WorkoutExerciseType.AEROBIC, "Equipo necesario: ropa cómoda, calzado deportivo", DifficultyType.BEGINNER),
    new WorkoutExercise("Danza (zumba, danza del vientre, flamenco, etc.)", WorkoutExerciseType.AEROBIC, "Equipo necesario: ropa cómoda; para algunas danzas (como el flamenco o el ballet) se necesitan zapatos especiales", DifficultyType.BEGINNER),
    new WorkoutExercise("Ejercicios con el peso corporal", WorkoutExerciseType.STRENGTH, "Equipo necesario: ropa cómoda", DifficultyType.BASIC),
    new WorkoutExercise("Taichí (antiguo arte chino de movimientos suaves)", WorkoutExerciseType.FLEXIBILITY, "Equipo necesario: ropa cómoda", DifficultyType.BASIC),
    new WorkoutExercise("Natación", WorkoutExerciseType.AEROBIC, "Equipo necesario: traje de baño y toalla; en algunas piscinas se exige usar gorra y gafas para el agua", DifficultyType.BASIC),
  )

  val routineTemplateList = List(
    new RoutineTemplate("Rutina para saltar", "Esta rutina está diseñada para saltar", ScoringType.UNKNOWN, workoutExerciseList.toSet[WorkoutExercise].asJava, mealNutritionList.toSet[MealNutrition].asJava, GoalType.UNKNOWN),
    new RoutineTemplate("Rutina para correr", "Esta rutina está diseñada para correr", ScoringType.GOOD, workoutExerciseList.toSet[WorkoutExercise].asJava, mealNutritionList.toSet[MealNutrition].asJava, GoalType.UNKNOWN)
  )

  def mealNutritions() = List(
    new EntitiesWithLink[MealNutrition](mealNutritionList, endpointMealNutrition)
  ).asJava

  def workoutExercises() = List(
    new EntitiesWithLink[WorkoutExercise](workoutExerciseList, endpointWorkoutExercise)
  ).asJava

  def routineTemplates() = List(
    new EntitiesWithLink[RoutineTemplate](routineTemplateList, endpointRoutineTemplate)
  ).asJava


}
