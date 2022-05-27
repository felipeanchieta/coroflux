package io.github.felipeanchieta.coroflux

import com.mongodb.reactivestreams.client.MongoClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class Application

@Configuration
@EnableReactiveMongoRepositories
class DatabaseConfig(private val mongoClient: MongoClient) {
    @Bean
    fun reactiveMongoTemplate(): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(mongoClient, "test")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}