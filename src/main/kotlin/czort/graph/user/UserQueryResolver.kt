package czort.graph.user

import brave.sampler.Sampler
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import io.micrometer.core.annotation.Timed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Lazy
@Component
class UserQueryResolver(
  val userRepository: UserRepository
): GraphQLQueryResolver {

    @Bean
    fun defaultSampler(): Sampler {
        return Sampler.ALWAYS_SAMPLE
    }

    @Timed("users")
    fun users(): List<User> {
        return userRepository.findAll()
    }
}