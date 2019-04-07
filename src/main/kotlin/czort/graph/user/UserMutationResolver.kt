package czort.graph.user

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.oembedler.moon.graphql.boot.error.ThrowableGraphQLError
import czort.graph.shared.ValidationError
import graphql.GraphQLError
import graphql.execution.DataFetcherResult
import graphql.servlet.GraphQLErrorHandler
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.Validator


@Service
@Validated

class UserMutationResolver(
  val validator: Validator,
  val userRepository: UserRepository
): GraphQLMutationResolver {

    fun createUser(@Valid input: CreateUser): DataFetcherResult<User?> {
        val violations = validator.validate(input)

        if (violations.isNotEmpty()) {
            return DataFetcherResult(
              null,
              listOf(ValidationError(violations))
            )
        }

        return DataFetcherResult(userRepository.insertUser(input), listOf())
    }
}