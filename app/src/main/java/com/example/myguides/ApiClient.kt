package com.example.myguides

import com.beust.klaxon.Klaxon
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.content.TextContent
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter

class UnauthorizedClient(private val apiUrl: String) {
    private val client: HttpClient = HttpClient()

    fun login(authData: AuthData): TokenInformationResult {
        val json = Klaxon().toJsonString(authData)
        val responseAsBytes = runBlocking {
            client.post<ByteArray>{
                url(apiUrl + "/" + "sign-up")
                body = TextContent(json, ContentType.Application.Json)
            }
        }

        val charset = Charsets.UTF_8;
        val responseString = responseAsBytes.toString(charset);
        val a = Klaxon().parse<TokenInformationResult>(responseString);
        return a as TokenInformationResult
    }
}

class ApiClient(private val apiUrl: String, private val token: String) {
    private val client: HttpClient = HttpClient()

    fun searchGuides(name: String) : GuideDescriptionListResult {
        val responseAsBytes = runBlocking {
            client.get<ByteArray>{
                url(apiUrl + "/" + "guides/description?name=$name&take=9999&skip=0")
                header("X-PRIVATE-TOKEN", TokenHelper.getToken())
            }
        }

        val charset = Charsets.UTF_8;
        val responseString = responseAsBytes.toString(charset);
        val a = Klaxon().parse<GuideDescriptionListResult>(responseString);
        return a as GuideDescriptionListResult;
    }

    fun getLikedGuides() : GuideDescriptionListResult {
        val responseAsBytes = runBlocking {
            client.get<ByteArray>{
                url(apiUrl + "/" + "guides/liked")
                header("X-PRIVATE-TOKEN", TokenHelper.getToken())
            }
        }

        val charset = Charsets.UTF_8;
        val responseString = responseAsBytes.toString(charset);
        val a = Klaxon().parse<GuideDescriptionListResult>(responseString);
        return a as GuideDescriptionListResult;
    }

    fun getFullGuide(id: String) : GuideResult {
        val responseAsBytes = runBlocking {
            client.get<ByteArray>{
                url(apiUrl + "/" + "guides/$id")
                header("X-PRIVATE-TOKEN", TokenHelper.getToken())
            }
        }

        val charset = Charsets.UTF_8;
        val responseString = responseAsBytes.toString(charset);
        val a = Klaxon().parse<GuideResult>(responseString);
        return a as GuideResult;
    }

    fun saveGuide(guide: Guide) : GuideDescriptionResult {
        val json = Klaxon().toJsonString(guide)
        val responseAsBytes = runBlocking {
            client.post<ByteArray>{
                url(apiUrl + "/" + "guides")
                header("X-PRIVATE-TOKEN", TokenHelper.getToken())
                body = TextContent(json, ContentType.Application.Json)
            }
        }

        val charset = Charsets.UTF_8;
        val responseString = responseAsBytes.toString(charset);
        val a = Klaxon().parse<GuideDescriptionResult>(responseString);
        return a as GuideDescriptionResult;
    }

    fun getUserId() : String {
        val responseAsBytes = runBlocking {
            client.get<ByteArray>{
                url(apiUrl + "/" + "user")
                header("X-PRIVATE-TOKEN", TokenHelper.getToken())
            }
        }

        val charset = Charsets.UTF_8;
        val responseString = responseAsBytes.toString(charset);
        val a = Klaxon().parse<User>(responseString);
        return a!!.id
    }

    fun likeGuide(guideId: String) : GuideDescriptionResult {
        val responseAsBytes = runBlocking {
            client.post<ByteArray>{
                url(apiUrl + "/" + "likes/$guideId")
                header("X-PRIVATE-TOKEN", TokenHelper.getToken())
            }
        }

        val charset = Charsets.UTF_8;
        val responseString = responseAsBytes.toString(charset);
        val a = Klaxon().parse<GuideDescriptionResult>(responseString);
        return a as GuideDescriptionResult;
    }
}

data class User(val id: String)

data class AuthData(val login: String, val password: String) {

}

data class GuideDescription(val id: String, val name: String, val description: String)
data class GuideDescriptionListResult(val value: List<GuideDescription>?, val error: String? = null) {
    fun isSuccessful(): Boolean {
        return value != null;
    }
}

data class GuideDescriptionResult(val value: GuideDescription?, val error: String? = null) {
    fun isSuccessful(): Boolean {
        return value != null;
    }
}

data class Slide(val base64Image: String, val text: String)
data class Guide(val description: GuideDescription, val slides: List<Slide>, val ownerId: String, val likes: List<String>, val tags: List<String>)
data class GuideResult(val value: Guide?, val error: String?) {
    fun isSuccessful(): Boolean {
        return value != null;
    }
}

data class TokenInformation(val token: String) {

}

data class TokenInformationResult(val value: TokenInformation?, val error: String? = null) {
    fun isSuccessful(): Boolean {
        return value != null;
    }
}

class TokenHelper() {
    companion object {

        var tokenValue: String = ""

        fun getToken (): String{
            return tokenValue
        }

        fun saveToken(token: String) {
            tokenValue = token
        }
    }
}