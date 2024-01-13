package dev.omariteck.ramoblog.responses

import dev.omariteck.ramoblog.models.Blog

data class BlogResponse (val message: String? = null, val blogs: List<Blog>? = null)