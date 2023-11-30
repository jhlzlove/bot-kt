package org.bot.miyoushe

import org.bot.common.getMiYouShe
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @author jhlz
 * @since 1.0
 */
class MiYouSheUtil {

    companion object {
        private val url = "https://bbs-api.miyoushe.com"
        private val headers = mapOf(
            "x-rpc-bot_secret" to secret(),
            "x-rpc-bot_id" to getMiYouShe().botId
        )

        @OptIn(ExperimentalStdlibApi::class)
        fun secret(): String {
            val signingKey = SecretKeySpec(getMiYouShe().pubKey.toByteArray(Charsets.UTF_8), "HmacSHA256");
            val mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            val rawHmac = mac.doFinal(getMiYouShe().secret.toByteArray(Charsets.UTF_8));
            val res = rawHmac.toHexString(format = HexFormat.Default)
            println(res)
            return res
        }

        fun getToken() {
            val request = request(url + MiyousheUrl.token_url, true, null)
            println(request)
        }

        fun getVala(): String {
            val body = request("$url/vila/api/bot/platform/getVilla", false, headers)
            return body
        }

        fun request(url: String, isPost: Boolean = false, headers: Map<String, String>?): String {
            val cli = HttpClient.newBuilder().build()
            val build = HttpRequest.newBuilder().uri(URI.create(url))
            build.setHeader("Content-Type", "application/json")
            headers?.run {
                {
                    entries.forEach {
                        build.setHeader(it.key, it.value)
                    }
                }
            }
            val request = if (isPost) build.POST(BodyPublishers.noBody()).build()
            else build.GET().build()
            val body = cli.send(request, BodyHandlers.ofString()).body()
            return body
        }
    }

}

fun main() {
    println(MiYouSheUtil.getVala())
}