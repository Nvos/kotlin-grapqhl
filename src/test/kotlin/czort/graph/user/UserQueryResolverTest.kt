package czort.graph.user

import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isNullOrEmpty
import assertk.assertions.isTrue
import com.graphql.spring.boot.test.TestUtils
import czort.graph.IntegrationTest
import czort.graph.IntegrationTestAutoConfiguration
import graphql.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit4.SpringRunner
import graphql.Assert.assertNotNull

@RunWith(SpringRunner::class)
@ComponentScan("czort.graph")
@IntegrationTestAutoConfiguration
class UserQueryResolverTest: IntegrationTest() {

    @Value("graphql/fetch-user-query.graphql")
    lateinit var query: String

    @Test
    fun `User query should return users`() {
        val response = graphQLTestTemplate.postForResource(query).rawResponse.body
        snapshot.matchWithSnapshot(response)
    }

    @Test
    fun `User creation validation should fail`() {
        val response = graphQLTestTemplate.postMultipart(
            """
                mutation CreateUser {
                    createUser(input: {name: "Je"}) {
                        id
                        name
                    }
                }
            """.trimIndent(),
            """{}"""
        )

        assertThat(response).isNotNull()
        assertThat(response.isOk).isTrue()
        assertThat(response.get("$.errors", List::class.java)).isNotNull()
    }
}