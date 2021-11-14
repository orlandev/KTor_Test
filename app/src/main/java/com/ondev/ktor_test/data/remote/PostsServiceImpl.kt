package com.ondev.ktor_test.data.remote

import com.ondev.ktor_test.data.remote.dto.PostRequest
import com.ondev.ktor_test.data.remote.dto.PostResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

class PostsServiceImpl(

    private val httpClient: HttpClient

) : PostsService {
    override suspend fun getPosts(): List<PostResponse> {
        return try {
            httpClient.get {
                url(HttpRoutes.POSTS)
            }
        } catch (e: RedirectResponseException) {
            //3xx - response
            println("Error ${e.response.status.description}")
            emptyList<PostResponse>()
        } catch (e: ServerResponseException) {
            //5xx - response
            println("Error ${e.response.status.description}")
            emptyList<PostResponse>()
        } catch (e: Exception) {
            //UNKNOW - response
            println("Error ${e.message}")
            emptyList<PostResponse>()
        }
    }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {
        return try {
            httpClient.post<PostResponse>() {
                url(HttpRoutes.POSTS)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
        } catch (e: RedirectResponseException) {
            //3xx - response
            println("Error ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            //5xx - response
            println("Error ${e.response.status.description}")
            null
        } catch (e: Exception) {
            //UNKNOW - response
            println("Error ${e.message}")
            null
        }
    }
}