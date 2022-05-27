package io.github.felipeanchieta.coroflux

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import java.util.*

@SpringBootTest
@Execution(ExecutionMode.CONCURRENT)
class HelloFluxTest {

    private lateinit var client: WebTestClient

    @Autowired
    private lateinit var blogPostRepository: BlogPostRepository

    @BeforeEach
    fun setUp(context: ApplicationContext) {
        client = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    fun `should return hello world`() {
        client
            .get()
            .uri("/hello")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java).isEqualTo("Hello World!")
    }

    @Test
    fun `should return hello with name`() {
        client
            .get()
            .uri("/hello?name=Felipe")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java).isEqualTo("Hello Felipe!")
    }

    @Test
    fun `should return blog posts`() {
        runBlocking {
            blogPostRepository.save(BlogPost(
                id = UUID.randomUUID(),
                title = "title",
                text = "text",
            ))
        }

        client
            .get()
            .uri("/blogposts")
            .exchange()
            .expectStatus().isOk
            .expectBodyList<BlogPost>()
    }
}