package org.bot.qq.constants

/**
 * QQ Open API 请求地址常量，除个别外，基本形式为 QQ_PREFIX + 具体
 * @author jhlz
 * @since 1.0
 */
class UrlConstants {
    companion object {
        // QQ Open API 统一的请求 prefix
        const val QQ_PREFIX = "https://api.sgroup.qq.com"

        // Open API 请求头需要的 token 获取地址
        const val GET_TOKEN = "https://bots.qq.com/app/getAppAccessToken"

        // 获取 websocket 连接地址
        const val GET_WSS = "/gateway/bot"

        // 获取当前登录用户/机器人详情
        const val GET_ME = "/users/@me"

        // 获取当前用户/机器人加入的频道列表
        const val GET_GUILDS = "/users/@me/guilds"

        // 子频道列表
        const val GET_SUB_GUILDS = "/guilds/%s/channels"

        // 子频道详情
        const val subGuildOfDetail = "/channels/%s"

        // 音视频/直播子频道在线人数, 子频道 id
        const val online_nums = "/channels/%s/online_nums"

        // 子频道成员详细信息，频道 id，用户 id
        const val memberDetail = "/guilds/%s/members/%s"

        // 频道成员列表，仅私域可使用，频道 guild_id
        const val memberList = "/guilds/%s/members"

        // 消息频率设置，guild_id
        const val messageRate = "/guilds/%s/message/setting"


    }


}