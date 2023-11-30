import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlinx.serialization.json.Json
import org.bot.common.getQQ
import org.bot.common.util.QQUtil
import org.bot.qq.constants.UrlConstants
import org.bot.qq.data.BotBase
import org.junit.jupiter.api.Test
import java.io.FileReader

/**
 * @author jhlz
 * @since 1.0
 */
class QQBotTest {
    val headers =
        mapOf(
            "Authorization" to "QQBot JAXy0LCftQ3HZfMRU3mJOASxzFQfoowPLEREM2hSfppOlqEPbLTslhOs63Ge343bGXDvEPgK4_D3",
            // "Authorization" to "Bot ${BotConstants.appId}.${BotConstants.appToken}",
            "X-Union-Appid" to getQQ().appId
        )

    private fun assemble(link: String): String {
        return UrlConstants.QQ_PREFIX + link
    }

    @Test
    fun getAccessToken() {
        val accessToken = QQUtil.getAccessToken()
        println(accessToken)
    }

    @Test
    fun getToken() {
        FileReader("src/main/resources/token.json").use {
            val text = it.readText()
            val json = Json.decodeFromString<Map<String, String>>(text)
            val expireTime = json.get("expires_in")
            val now = Clock.System.now().plus(8, DateTimeUnit.HOUR)
            println(expireTime)
        }
    }

    @Test
    fun getMe() {
        val channel = QQUtil.get(assemble(UrlConstants.GET_ME), headers, null)
        val res = Json.decodeFromString<Map<String, String>>(channel)
        println("id : ${res.get("id")}")
    }

    @Test
    fun getGuilds() {
        val channel = QQUtil.get(assemble(UrlConstants.GET_GUILDS), headers, null)
        Json.decodeFromString<Array<BotBase.Guild>>(channel)
        println(channel)
    }

    /**
     * 子频道列表
     */
    @Test
    fun getSubGuildList() {
        val channel =
            QQUtil.get(
                assemble(String.format(UrlConstants.GET_SUB_GUILDS, "15465803430804453911")),
                headers, null
            )
        val res = Json.decodeFromString<Array<BotBase.SubGuild>>(channel)

        res.forEach {
            println("guild: $it")
        }
    }

    /**
     * test: 频道成员列表
     */
    @Test
    fun getMemberListTest() {
        val params = mapOf("limit" to "100")
        val res =
            QQUtil.get(assemble(String.format(UrlConstants.memberList, "15465803430804453911")), headers, params)
        println(res)
        val map = Json.decodeFromString<Array<BotBase.Member>>(res)
        map.forEach(::println)
    }

    /**
     * test: 消息频率的设置详情
     */
    @Test
    fun getGuildMessageRateTest() {
        val res =
            QQUtil.get(assemble(String.format(UrlConstants.messageRate, "15465803430804453911")), headers, null)
        println(res)
        val result = Json.decodeFromString<BotBase.MessageSetting>(res)
        println(result)
    }

    /**
     * 获取 Websocket 链接地址，程序启动时需要连接
     */
    @Test
    suspend fun getWss() {
        val message = QQUtil.getWssUrl()
        val wssUrl = Json.decodeFromString<BotBase.Wss>(message)
        println(wssUrl.url)
    }
}
