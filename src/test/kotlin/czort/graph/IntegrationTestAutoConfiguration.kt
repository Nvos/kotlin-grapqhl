package czort.graph

import com.graphql.spring.boot.test.GraphQLTest
import org.junit.runner.RunWith
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit4.SpringRunner
import java.lang.annotation.Inherited
import java.lang.annotation.RetentionPolicy
import kotlin.annotation.Retention
import kotlin.annotation.Target

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention
@GraphQLTest
@ImportAutoConfiguration(ValidationAutoConfiguration::class, DataSourceAutoConfiguration::class)
annotation class IntegrationTestAutoConfiguration