package dev.omariteck.ramoblog.services

import dev.omariteck.ramoblog.DuplicateTitleException
import dev.omariteck.ramoblog.models.Blog
import dev.omariteck.ramoblog.repositories.BlogRepository
import org.springframework.stereotype.Service

@Service
class BlogService(private val blogRepository: BlogRepository) {
    fun getBlogs() = blogRepository.findAllByOrderByCreatedDateDesc()
    fun getBlog(id: Int) = blogRepository.findById(id)
    fun createBlog(title: String, content: String) {
        if (title.isBlank() || content.isBlank()) {
            throw Exception("Title and content cannot be blank")
        }
        if (blogRepository.existsByTitle(title)) {
            throw DuplicateTitleException("A blog with the title '$title' already exists.")
        }
        blogRepository.save(Blog(title = title, content = content))
    }
}