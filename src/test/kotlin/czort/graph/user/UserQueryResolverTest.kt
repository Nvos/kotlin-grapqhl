package czort.graph.user

import com.graphql.spring.boot.test.GraphQLTest
import com.graphql.spring.boot.test.GraphQLTestTemplate
import czort.graph.path
import czort.graph.scopedMatchSnapshot
import io.kotlintest.specs.FeatureSpec
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@GraphQLTest
@AutoConfigureJdbc
@ComponentScan("czort.graph")
@ImportAutoConfiguration(ValidationAutoConfiguration::class)
class UserQueryResolverTest(
  @Value("graphql/fetch-user-query.graphql") query: String,
  private val template: GraphQLTestTemplate
): FeatureSpec({

    feature("User query") {
        scenario("Should return all users") {
            template.postForResource(query)
                .scopedMatchSnapshot(path())
        }
    }
})
