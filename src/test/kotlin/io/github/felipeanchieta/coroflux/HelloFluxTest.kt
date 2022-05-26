package io.github.felipeanchieta.coroflux

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.ResourceAccessMode
import org.junit.jupiter.api.parallel.ResourceLock
import org.junit.jupiter.api.parallel.Resources.SYSTEM_PROPERTIES
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.web.reactive.server.WebTestClient

@SpringJUnitConfig(Application::class)
class HelloFluxTest {

    private lateinit var client: WebTestClient

    @BeforeEach
    fun setUp(context: ApplicationContext) {
        client = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    @ResourceLock(value = SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ)
    fun `should return hello world`() {
        client
            .get()
            .uri("/hello")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java).isEqualTo("Hello World!")
    }

    @Test
    @ResourceLock(value = SYSTEM_PROPERTIES, mode = ResourceAccessMode.READ)
    fun `should return hello with name`() {
        client
            .get()
            .uri("/hello?name=Felipe")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java).isEqualTo("Hello Felipe!")
    }
}