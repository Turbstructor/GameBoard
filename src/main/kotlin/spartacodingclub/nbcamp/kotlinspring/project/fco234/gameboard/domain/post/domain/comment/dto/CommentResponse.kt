package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val postId: Long,
    val content: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?

)