package org.bot.common

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * 基于 ktor CIO engine 编写的 HTTP 网络工具
 * @author jhlz
 * @since 1.0
 */
class HttpUtil {

    companion object {
        suspend fun get(url: String): String {
            val response = HttpClient().request(url)
            return response.call.body<String>()
        }

        suspend fun post(url: String, headers: Map<String, Any>?, body: String?): String {
            val requestBuilder = HttpRequestBuilder()
            requestBuilder.contentType(ContentType.Application.Json)
            requestBuilder.setAttributes {

            }
            headers?.forEach {
                requestBuilder.header(it.key, it.value)
            }
            requestBuilder.setBody(body)
            requestBuilder.url(url)
            return HttpClient().post(requestBuilder).call.body<String>()
        }
    }
}