package czort.graph.config

import com.oembedler.moon.graphql.boot.error.ThrowableGraphQLError
import czort.graph.shared.ValidationError
import graphql.GraphQLError
import graphql.execution.DataFetcherResult
import graphql.servlet.DefaultGraphQLErrorHandler
import graphql.servlet.GraphQLErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import javax.validation.ConstraintViolationException

@Component
class GlobalExceptionHandler {

    @ExceptionHandler(Throwable::class)
    fun handleValidationError(ex: Throwable): GraphQLError = when(ex) {
        is ConstraintViolationException -> ValidationError(ex.constraintViolations)
        else -> ThrowableGraphQLError(ex)
    }

//    @Bean
//    fun errorHandler(): GraphQLErrorHandler {
//        return DefaultGraphQLErrorHandler()
//    }
}