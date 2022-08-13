package org.aerovek.chartr.data.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.aerovek.chartr.data.model.ResponseBase
import java.io.IOException

/**
 * This class has two different versions of GET and POST methods. The main reason
 * being that the Elrond Gateway has a different response body than requests to Elrond API.
 * [apiGet] handles any generic response body
 * [gatewayGet] handles the response body that is in the [ResponseBase] format with nested T object
 */
class RestClient(
    private val httpClient: OkHttpClient,
    private val baseUrl: String,
    private val gson: Gson
) {

    @Throws(IOException::class)
    internal inline fun <reified T> apiGet(resourceUrl: String): T {
        val url = "$baseUrl/$resourceUrl"
        val request: Request = Request.Builder().url(url).build()
        val responseJson = httpClient.newCall(request).execute().use { response ->
            response.body?.string()
        }
        println(responseJson)
        val response: T? = responseJson?.let { gson.fromJson(it) }
        requireNotNull(response)
        return response
    }

    @Throws(IOException::class)
    internal inline fun <reified T> apiPost(resourceUrl: String, json: String): T {
        val url = "$baseUrl/$resourceUrl"
        val body = json.toRequestBody(JSON)

        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val result = httpClient.newCall(request).execute()
        val responseJson = result.run {
            this.body?.string()
        }
        val response: T? = responseJson?.let { gson.fromJson(it) }
        requireNotNull(response)
        return response
    }

    @Throws(IOException::class)
    internal inline fun <reified T : ResponseBase<*>> gatewa