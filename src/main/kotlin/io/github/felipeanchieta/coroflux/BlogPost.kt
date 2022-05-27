package io.github.felipeanchieta.coroflux

import kotlinx.coroutines.flow.Flow
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Document(collection = "blog_post")
data class BlogPost(
    @Id val id: UUID,
    val title: String,
    val text: String,
)

@Repository
interface BlogPostRepository: CoroutineCrudRepository<BlogPost, UUID> {
    override fun findAll(): Flow<BlogPost>
}