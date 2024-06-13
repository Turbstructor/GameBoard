package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.domain.comment.model.Comment

interface CommentRepository: JpaRepository<Comment, Long> {

    fun findAllByPostId(postId: Long): List<Comment>

}