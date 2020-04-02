package ar.edu.davinci.infraestructure.converters

import ar.edu.davinci.domain.model.training.detail.{ExerciseRunningSession, NutritionSession}
import com.fasterxml.jackson.core.`type`.TypeReference
import javax.persistence.Converter

@Converter(autoApply = true)
class ExerciseRunningSessionConverterJson extends JpaConverterJson[java.util.List[ExerciseRunningSession]] {
  override val typeReference = new TypeReference[java.util.List[ExerciseRunningSession]] {}
}

@Converter(autoApply = true)
class NutritionSessionConverterJson extends JpaConverterJson[java.util.List[NutritionSession]] {
  override val typeReference = new TypeReference[java.util.List[NutritionSession]] {}
}
