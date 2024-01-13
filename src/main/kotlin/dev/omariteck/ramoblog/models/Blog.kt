package dev.omariteck.ramoblog.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import java.util.*

@Entity
class Blog (
    @Id @Column(updatable = false) @SequenceGenerator(
        name = "blog_sequence",
        sequenceName = "blog_sequence",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_sequence")
    val id: Long? = null,
    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    val title: String,
    @Column(nullable = false, columnDefinition = "TEXT")
    val content: String,
    @Column(name = "created_at", nullable = false, columnDefinition = "Date")
    val createdDate: Date = Date(),
    @Column(name = "updated_at", nullable = false, columnDefinition = "Date")
    val updatedDate: Date = Date()
)