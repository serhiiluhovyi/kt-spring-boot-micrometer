package com.example.countertimergauge

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class MetricsService(
    private val meterRegistry: MeterRegistry
) {

    final fun createCounter(type: String, baseUnit: String) =
        Counter
            .builder("app.$baseUnit.counter")
            .baseUnit(baseUnit)
            .tags("type", type)
            .register(meterRegistry)

    final fun createTimer(type: String, name: String) =
        Timer
            .builder("app.$name.timer")
            .tags("type", type)
            .publishPercentiles(0.5, 0.95)
//            .publishPercentileHistogram()
            .register(meterRegistry)

    // counters
    private val totalCount = createCounter(CounterType.TOTAL.name, "demo")
    private val processedCount = createCounter(CounterType.PROCESSED.name, "demo")
    private val failedCount = createCounter(CounterType.FAILED.name, "demo")

    fun incrementTotalCount() = totalCount.increment()
    fun incrementProcessedCount() = processedCount.increment()
    fun incrementFailedCount() = failedCount.increment()

    //timers
    val smthTimer = createTimer("method-timer", "smth")

    //gauge
    val startsWithA = AtomicInteger(0)

    init {
        Gauge.builder("app.request.start.with.a") { startsWithA }
            .description("The number of requests start with A letter")
            .register(meterRegistry)
    }


}

enum class CounterType {
    TOTAL, PROCESSED, FAILED
}
