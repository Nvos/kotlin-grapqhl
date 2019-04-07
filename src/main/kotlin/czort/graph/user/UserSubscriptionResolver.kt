package czort.graph.user

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver
import org.reactivestreams.Publisher
import org.springframework.stereotype.Component

@Component
class UserSubscriptionResolver(
  val userPublisher: UserPublisher
): GraphQLSubscriptionResolver {

    fun addUser(): Publisher<User> {
        println("wtf")
        return userPublisher.publisher
    }
}