package dev.omariteck.ramoblog.controllers

import dev.omariteck.ramoblog.DuplicateTitleException
import dev.omariteck.ramoblog.models.BlogRequest
import dev.omariteck.ramoblog.responses.BlogResponse
import dev.omariteck.ramoblog.responses.MessageResponse
import dev.omariteck.ramoblog.services.BlogService
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/blogs")
class BlogController(val blogService: BlogService) {
    private val logger = LogManager.getLogger()

    @GetMapping
    fun blogs() : ResponseEntity<BlogResponse> {
        try {
            val blogs = blogService.getBlogs()
            return ResponseEntity.status(HttpStatus.OK).body(BlogResponse(blogs = blogs))
        } catch (e: Exception) {
            val errorMessage = "Error retrieving blogs: "
            logger.error(errorMessage, e)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BlogResponse(errorMessage))
        }
    }


    @GetMapping("{id}")
    fun getBlog(@PathVariable id: Int) : ResponseEntity<BlogResponse> {
        val blogOpt = blogService.getBlog(id)
        if (blogOpt.isPresent) {
            return ResponseEntity.status(HttpStatus.OK).body(BlogResponse(blogs = listOf(blogOpt.get())))
        }
        logger.info("couldn't retrieve blog with id $id: ")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BlogResponse("Blog with id $id not found"))
    }

    @PostMapping(consumes = ["application/json", MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun createBlog(blog: BlogRequest) : ResponseEntity<MessageResponse> {
        try {
            blogService.createBlog(blog.title, blog.content)
            val message = "Blog was created successfully"
            logger.info(message)
            return ResponseEntity.status(HttpStatus.OK).body(MessageResponse(message))
        } catch(e: DuplicateTitleException) {
            val errorMessage = "A blog with the title '${blog.title}' already exists."
            logger.info(errorMessage, e)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse(errorMessage))
        } catch (e: Exception) {
            val errorMessage = "Error saving the new blog: "
            logger.error(errorMessage, e)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResponse(errorMessage))
        }
    }
}