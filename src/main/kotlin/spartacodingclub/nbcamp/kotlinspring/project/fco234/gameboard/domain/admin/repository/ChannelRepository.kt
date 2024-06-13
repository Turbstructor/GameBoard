package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.domain.admin.model.AdminChannel

interface ChannelRepository : JpaRepository<AdminChannel, Long>