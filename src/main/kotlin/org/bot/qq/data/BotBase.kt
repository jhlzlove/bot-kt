package org.bot.qq.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.format.DateTimeFormatter

/**
 * 各种实体
 * @author jhlz
 * @since 1.0
 */

class BotBase {
    @Serializable
    data class AccessToken(val access_token: String, val expires_in: Int)

    @Serializable
    data class Guild(
        val id: String,
        val name: String,
        val icon: String,
        val owner_id: String,
        val owner: Boolean,
        val joined_at: String,
        val member_count: Int,
        val max_members: Int,
        val description: String
    )

    @Serializable
    data class SubGuild(
        val id: String,
        val guild_id: String,
        val name: String,
        val type: Int,
        val position: Int,
        val parent_id: String,
        val owner_id: String,
        val sub_type: Int,
        val private_type: Int = 0,
        val speak_permission: Int = 0,
        val application_id: String = "application_id",
        val permissions: String = "permissions",
    )

    @Serializable
    data class User(
        val id: String,
        val username: String,
        val avatar: String,
        val bot: Boolean,
        val union_openid: String = "",
        val union_user_account: String = "",
    )

    @Serializable
    data class Member(
        val user: User,
        val nick: String,
        val roles: Array<String>,
        // 时间为带时区的时间格式
        @Serializable(with = ZonedDateTimeSerializer::class)
        val joined_at: java.time.ZonedDateTime,
        val guild_id: String? = "",
    )

    @Serializable
    data class MessageSetting(
        val disable_create_dm: Boolean,
        val disable_push_msg: Boolean,
        val channel_ids: Array<String>,
        val channel_push_max_num: Long,
    )

    @Serializable
    data class Wss(val url: String, val shards: Int, val session_start_limit: SessionStartLimit)

    @Serializable
    data class SessionStartLimit(val total: Int, val remaining: Int, val reset_after: Int, val max_concurrency: Int)

    /**
     * 自定义时间序列化
     */
    object ZonedDateTimeSerializer : KSerializer<java.time.ZonedDateTime> {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")

        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ZonedDateTime", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: java.time.ZonedDateTime) {
            encoder.encodeString(value.format(formatter))
        }

        override fun deserialize(decoder: Decoder): java.time.ZonedDateTime {
            return java.time.ZonedDateTime.parse(decoder.decodeString(), formatter)
        }
    }
}