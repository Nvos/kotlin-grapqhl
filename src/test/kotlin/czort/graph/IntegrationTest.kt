package czort.graph

import com.graphql.spring.boot.test.GraphQLTestTemplate
import com.karumi.kotlinsnapshot.KotlinSnapshot
import org.springframework.beans.factory.annotation.Autowired

abstract class IntegrationTest {
    val snapshot = KotlinSnapshot(testClassAsDirectory = true, snapshotsFolder = "src/test/kotlin")

    @Autowired
    lateinit var graphQLTestTemplate: GraphQLTestTemplate
}