package io.github.felipeanchieta.coroflux

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController(
    private val blogPostRepository: BlogPostRepository,
) {
    @GetMapping("/hello")
    suspend fun hello(
        @RequestParam(value = "name", defaultValue = "World") name: String
    ): String = coroutineScope {
        val asyncCompliment = async { getCompliment() }
        val asyncName = async { getName(name) }
        val exclamationMark = getExclamationMark().drop(2).first()

        return@coroutineScope "${asyncCompliment.await()} ${asyncName.await()}$exclamationMark"
    }

    @GetMapping("/goodbye")
    suspend fun goodbye(): Flow<String> = flow {
        emit("goodbye" + "\n")
        delay(2500)
        emit("cruel world")
    }

    @GetMapping("/blogposts")
    suspend fun blogPosts(): Flow<BlogPost> =
        blogPostRepository.findAll()

    private suspend fun getCompliment(): String {
        delay(1500)
        sequence { yield(1) }
        return "Hello"
    }

    private suspend fun getName(name: String): String {
        delay(4000)
        return name
    }

    private fun getExclamationMark(): Flow<String> = flow {
        delay(1000)
        emit("?")
        delay(1000)
        emit("@")
        delay(2000)
        emit("!")
    }
}