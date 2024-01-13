package dev.omariteck.ramoblog.repositories

import dev.omariteck.ramoblog.models.Blog
import org.springframework.data.jpa.repository.JpaRepository

interface BlogRepository : JpaRepository<Blog, Int> {
    fun findAllByOrderByCreatedDateDesc(): List<Blog>
    fun existsByTitle(title: String): Boolean
}