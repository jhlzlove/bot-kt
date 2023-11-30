package org.bot.common.util

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.bot.common.HttpUtil
import org.bot.common.getQQ
import org.bot.qq.constants.UrlConstants
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * QQ Open API 网络请求工具
 * @author jhlz
 * @since 1.0
 */
class QQUtil {

    companion object {

        /**
         * 获取请求 open api 需要的 token
         */
        fun getAccessToken(): String {
            val auth = """
            {
              "appId": "${getQQ().appId}",
              "clientSecret": "${getQQ().appSecret}"
            }
        """.trimIndent()
            var body = ""
            runBlocking {
                body = HttpUtil.post(UrlConstants.GET_TOKEN, null, body = auth)
                // val body = HttpClient.newHttpClient().send(
                //     HttpRequest.newBuilder()
                //         .setHeader("Content-Type", "application/json")
                //         .POST(HttpRequest.BodyPublishers.ofString(auth))
                //         .uri(URI.create(UrlConstants.GET_TOKEN))
                //         .build(), HttpResponse.BodyHandlers.ofString()
                // ).body()

            }
            return body
        }

        suspend fun getWssUrl(): String {
            val accessToken = Json.decodeFromString<Map<String, String>>(getAccessToken())
            val token = accessToken.get("access_token")
            val headers = mapOf("Authorization" to "QQBot $token", "X-Union-Appid" to getQQ().appId)
            return get(UrlConstants.QQ_PREFIX + UrlConstants.GET_WSS, headers, null)
        }

        fun get(url: String, headers: Map<String, String>?, params: Map<String, String>?): String {
            val sb = StringBuilder()
            params.let {
                it?.entries?.forEach { sb.append("${it.key}=${it.value}") }
            }
            val request = HttpRequest.newBuilder()
                .setHeader("Content-Type", "application/json")
                .GET()
                .uri(URI.create(url.let { "$it?$sb" }))
                .also { req ->
                    headers?.entries?.forEach {
                        req.header(it.key, it.value)
                    }

                }
                .build()
            return HttpClient.newHttpClient().send(
                request, HttpResponse.BodyHandlers.ofString()
            ).body()
        }
    }

}