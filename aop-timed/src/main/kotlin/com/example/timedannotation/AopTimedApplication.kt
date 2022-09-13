package com.example.timedannotation

import io.micrometer.core.annotation.Timed
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ThreadLocalRandom
import kotlin.system.measureTimeMillis

@SpringBootApplication
class AopTimedApplication

fun main(args: Array<String>) {
    runApplication<AopTimedApplication>(*args)
}

@Configuration
class Config {
    /**
     * Required for @Timed annotation on method magic
     */
    @Bean
    fun timedAspect(registry: MeterRegistry): TimedAspect {
        return TimedAspect(registry)
    }
}

@RestController
@RequestMapping("/timed")
class DemoController(
    private val service: DemoService,
) {

    @GetMapping("/{str}")
    fun demo(@PathVariable str: String): ResponseEntity<String> {
        var res: String
        val execTime = measureTimeMillis {
            res = service.doSmth(str)
        }
        return ResponseEntity.ok("OK - $res - $execTime ms.")
    }
}

@Service
class DemoService(
    private val registry: MeterRegistry
) {

    @Timed(
        value = "smth.time", description = "Time taken to return smth",
        percentiles = [0.5, 0.95],
        extraTags = ["type", "aop-timed"]
    )
    fun doSmth(s: String): String {
        val randomMillis = ThreadLocalRandom.current().nextInt(500, 1000).toLong()
        Thread.sleep(randomMillis)
        return s.uppercase()
    }
}
