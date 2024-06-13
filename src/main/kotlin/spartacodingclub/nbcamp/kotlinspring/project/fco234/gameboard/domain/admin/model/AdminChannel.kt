package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.model

import jakarta.persistence.*
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.dto.ChannelResponse
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.common.type.BaseTime

@Entity
@Table(name = "admin_channel")
class AdminChannel (
    @Column(name= "title")
    var title : String?,

    @Column(name= "content")
    var description : String?,

    @Column(name = "channel_id")
    var channelId: Long? = null,

    @Column(name = "Age_Limit")
    var ageLimit: Int? = null,

    @Column(name = "GameTheme")
    var gameTheme: String,

    @Column(name = "active")
    var active: Boolean = false // 활성화 상태를 저장하는 필드


):BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    fun toResponse(): ChannelResponse{
        return ChannelResponse(
            id = this.id,
            channelId = this.channelId!!,
            description = this.description!!,
            ageLimit = this.ageLimit!!,
            gameTheme = this.gameTheme,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}