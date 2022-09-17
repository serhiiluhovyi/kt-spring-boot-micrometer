package com.example.countertimergauge

import io.micrometer.core.annotation.Timed
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

@SpringBootApplication
class CounterTimerGaugeApplication

fun main(args: Array<String>) {
    runApplication<CounterTimerGaugeApplication>(*args)
}

@RestController
@RequestMapping("/demo")
class DemoController(
    private val service: DemoService,
    private val metricsService: MetricsService,
) {

    @GetMapping("/{str}")
    fun demo(@PathVariable str: String): ResponseEntity<String> {

        metricsService.incrementTotalCount()

        if (str.startsWith("a", true)) {
            metricsService.startsWithA.incrementAndGet()
        }

        var res: String
        val execTime = measureTimeMillis {
            try {
                res = service.doSmth(str)
                metricsService.incrementProcessedCount()
            } catch (ex: Exception) {
                metricsService.incrementFailedCount()
                return ResponseEntity.ok("NOT_OK")
            }

        }
        return ResponseEntity.ok("OK - $res - $execTime ms.")
    }
}

@Service
class DemoService(
    private val metricsService: MetricsService,
) {

    fun doSmth(s: String): String {
        val start = Instant.now().toEpochMilli()

        val randomMillis = ThreadLocalRandom.current().nextInt(10, 150).toLong()
        Thread.sleep(randomMillis)
        val uppercase = s.uppercase()

        metricsService.smthTimer.record(Instant.now().minusMillis(start).toEpochMilli(), TimeUnit.MILLISECONDS)

        return uppercase
    }
}