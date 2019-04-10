package czort.graph.config

import brave.Tracer
import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.SimpleInstrumentationContext.whenCompleted
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
import graphql.execution.instrumentation.parameters.InstrumentationValidationParameters
import graphql.language.Document
import graphql.validation.ValidationError
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class CustomInstrumentation(
  val tracer: Tracer
): SimpleInstrumentation() {

    @Configuration
    inner class RegistryConfig {
        @Bean
        internal fun metricsCommonTags(): MeterRegistryCustomizer<MeterRegistry> {
            return MeterRegistryCustomizer {
                registry -> registry.config().commonTags("app.name", "graphql")
            }
        }

        @Bean
        internal fun timedAspect(registry: MeterRegistry): TimedAspect {
            return TimedAspect(registry)
        }
    }

    private val log = LoggerFactory.getLogger(CustomInstrumentation::class.java)
    override fun beginExecution(parameters: InstrumentationExecutionParameters): InstrumentationContext<ExecutionResult> {
        tracer.currentSpanCustomizer().annotate("Begin execution")
        
        return super.beginExecution(parameters)
    }

    override fun beginFieldFetch(parameters: InstrumentationFieldFetchParameters): InstrumentationContext<Any> {
//        val step = parameters.executionContext.executionId

//        val scoped = tracer.startScopedSpan(step.path.toList().joinToString("->"))

        return whenCompleted {result, t ->  }
    }

    override fun beginParse(parameters: InstrumentationExecutionParameters): InstrumentationContext<Document> {
        val scoped = tracer.startScopedSpan("Begin parse")

        return whenCompleted {result, t -> scoped.finish() }
    }

    override fun beginValidation(parameters: InstrumentationValidationParameters): InstrumentationContext<MutableList<ValidationError>> {
        val scoped = tracer.startScopedSpan("Begin validation")
        return whenCompleted {result, t -> scoped.finish() }
    }


}