package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.entity

import org.springframework.data.repository.findByIdOrNull
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.repository.CommentRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.post.repository.PostRepository
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.response.CommentResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.request.CreateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.dto.request.UpdateCommentRequest
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.entity.Comment
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.comment.entity.toResponse
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.entity.MemberRole
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.infra.security.UserPrincipal
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException

@Service
class CommentService (
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository

) {

    fun getComment(
        postId: Long
    ): List<CommentResponse>{

        if(!postRepository.existsById(postId)) throw ModelNotFoundException("Post", postId)
        return commentRepository.findAllByPostId(postId).map{ it.toResponse()}
    }


    @Transactional
    fun createComment(
        postId: Long,
        createCommentRequest: CreateCommentRequest
    ): CommentResponse {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw ModelNotFoundException("Post", postId)
//        val comment= commentRepository.findByIdOrNull(postId) ?: throw RuntimeException("몰라")

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal

        val user = memberRepository.findByEmail(principal.email)
            ?: throw RuntimeException("너 누구야")



        return commentRepository.save(
            Comment(
            content = createCommentRequest.content,
            post = post,
            member = user,
        )
        ).toResponse()
    }


    @Transactional
    fun updateComment(postId:Long, commentId:Long, updateCommentRequest: UpdateCommentRequest): CommentResponse {
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal


        val user = memberRepository.findByEmail(principal.email)
            ?: throw RuntimeException("User not found")

        if(comment.post.member!!.id !=user.id  && (user.role == MemberRole.PLATFORM_USER ||(user.role== MemberRole.CHANNEL_USER )) ) throw RuntimeException("작성한 본인만 수정 가능함!!!!!!! 요것도 예외처리 추가 필요함~!~!~!~!~")




        comment.content = updateCommentRequest.content
        return commentRepository.save(comment).toResponse()

    }

    @Transactional
    fun deleteComment(postId: Long,commentId: Long){
        postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as UserPrincipal


        val user = memberRepository.findByEmail(principal.email)
            ?: throw RuntimeException("User not found")


        if(comment.post.member!!.id !=user.id && (user.role == MemberRole.PLATFORM_USER ||(user.role== MemberRole.CHANNEL_USER ))) throw RuntimeException("작성한 본인만 삭제 가능함!!!!!!! 요것도 예외처리 추가 필요함~!~!~!~!~")


        commentRepository.delete(comment)
    }


}

