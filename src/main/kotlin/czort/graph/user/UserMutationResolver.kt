package czort.graph.user

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import czort.graph.shared.ValidationError
import graphql.execution.DataFetcherResult
import org.springframework.stereotype.Component
import javax.validation.Validator


@Component
class UserMutationResolver(
  val validator: Validator,
  val userRepository: UserRepository
): GraphQLMutationResolver {

    fun createUser(input: CreateUser): DataFetcherResult<User?> {
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