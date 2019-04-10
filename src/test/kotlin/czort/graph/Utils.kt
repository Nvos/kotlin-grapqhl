package czort.graph

import com.graphql.spring.boot.test.GraphQLResponse
import com.karumi.kotlinsnapshot.KotlinSnapshot
import com.karumi.kotlinsnapshot.core.KotlinSerialization
import com.karumi.kotlinsnapshot.core.SerializationModule
import io.kotlintest.TestContext

val snapshot = KotlinSnapshot(
  testClassAsDirectory = true,
  snapshotsFolder = "src/test/kotlin"
)

fun Any?.scopedMatchSnapshot() {
    snapshot.matchWithSnapshot(this)
}

fun GraphQLResponse.scopedMatchSnapshot(snapshotName: String? = null) {
    snapshot.matchWithSnapshot(this.get("$", Map::class.java), snapshotName)
}

fun TestContext.path(): String {
    return this.description()
      .parents
      .joinToString("/") { it.replace("Feature: ", "") }
      .plus("/")
      .plus(this.description().name.replace("Scenario: ", ""))
}