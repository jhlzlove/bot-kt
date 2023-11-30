package org.bot.common

import com.akuleshov7.ktoml.Toml
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import java.io.File

@Serializable
data class BotSetting(
    val qq: QQ,
    val miyoushe: MiYouShe,
)

/**
 * 米游社配置
 */
@Serializable
data class MiYouShe(val creator: String, val botId: String, val pubKey: String, val secret: String)

/**
 * QQ 配置
 */
@Serializable
data class QQ(val appId: String, val appSecret: String, val appToken: String)

fun getMiYouShe(): MiYouShe {
    return getProperty().miyoushe
}

fun getQQ(): QQ {
    return getProperty().qq
}

fun getProperty(): BotSetting {
    return parserFile()
}

/**
 * 解析 toml 配置文件
 */
private fun parserFile(): BotSetting {
    val file = File("src/main/resources/application.toml")
    val text = file.readText()
    return Toml.decodeFromString<BotSetting>(text)
}