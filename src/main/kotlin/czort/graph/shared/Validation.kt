package czort.graph.shared

import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation
import javax.validation.ConstraintViolation

data class FieldValidationError(
  val field: String,
  val error: String
)

class ValidationError(
  private val constraintViolations: Set<ConstraintViolation<*>>
): GraphQLError {

    override fun getMessage(): String {
        return "Validation error"
    }

    override fun getErrorType(): ErrorType {
        return ErrorType.ValidationError
    }

    override fun getLocations(): MutableList<SourceLocation> {
        return mutableListOf()
    }

    override fun getExtensions(): MutableMap<String, Any> {
        val result = constraintViolations
          .map {
              FieldValidationError(
                field = it.propertyPath.joinToString("."),
                error = it.messageTemplate
              )
          }

        return mutableMapOf(
          "validation" to result
        )
    }
}