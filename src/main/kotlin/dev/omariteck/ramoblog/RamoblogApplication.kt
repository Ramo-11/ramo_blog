package dev.omariteck.ramoblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RamoblogApplication

fun main(args: Array<String>) {
    runApplication<RamoblogApplication>(*args)
}
