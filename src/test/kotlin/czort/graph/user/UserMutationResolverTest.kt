package czort.graph.user

import com.graphql.spring.boot.test.GraphQLTest
import com.graphql.spring.boot.test.GraphQLTestTemplate
import com.karumi.kotlinsnapshot.matchWithSnapshot
import czort.graph.path
import czort.graph.scopedMatchSnapshot
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.extensions.SpecLevelExtension
import io.kotlintest.extensions.TestListener
import io.kotlintest.specs.BehaviorSpec
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.kotlintest.spring.SpringAutowireConstructorExtension
import io.kotlintest.spring.SpringListener
import mu.KLogger
import mu.KotlinLogging
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit4.SpringRunner
import java.time.Duration
import java.time.Instant

@RunWith(SpringRunner::class)
@GraphQLTest
@AutoConfigureJdbc
@ComponentScan("czort.graph")
@ImportAutoConfiguration(ValidationAutoConfiguration::class)
class UserMutationResolverTest(
  private val template: GraphQLTestTemplate,
  private val logger: KLogger = KotlinLogging.logger {}
) : FeatureSpec({
    feature("Standard user mutation") {
        scenario("Should fail name validation") {
            template.postMultipart(
              """
                mutation CreateUser {
                    createUser(input: {name: "Je"}) {
                        id
                        name
                    }
                }
            """.trimIndent(),
              """{}"""
            ).scopedMatchSnapshot(this.path())
        }
    }
}) {
    val startTime = mutableMapOf<String, Instant>()
    val endTime = mutableMapOf<String, Instant>()

    override fun beforeTest(testCase: TestCase) {
        startTime[testCase.description.id()] = Instant.now()
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        endTime[testCase.description.id()] = Instant.now()
    }

    override fun afterSpecClass(spec: Spec, results: Map<TestCase, TestResult>) {
       results.forEach {
           val id = it.key.description.id()
           logger.info {
               "Test case $id executed in ${Duration.between(startTime[id]!!, endTime[id]!!).toMillis()} ms"
           }
       }
    }
}