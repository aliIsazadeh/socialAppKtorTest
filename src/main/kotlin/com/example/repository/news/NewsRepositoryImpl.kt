package com.example.repository.news

import com.example.util.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.statement.*

class NewsRepositoryImpl : NewsRepository {
    override suspend fun getAllNews() : Response<String> {
        try {


            val client = HttpClient()
            val response = client.get("https://api.tiingo.com/tiingo/news?token=a0fbf7bd530960365bd5c1ca0caf5b738ba95c5e") {
                header("Content-Type","application/json")
            }
            return if (response.status == HttpStatusCode.OK) {

                println(response.body<Any>().toString())
                Response.Success(data = "successfully take data from news" )
            } else {
                println("the error is " + response.status.value + " " + response.status.description)
                Response.Error(code = response.status , data = response.status.description)

            }
        } catch (e: Exception) {
            println("thers was exeption")
            return Response.Error(code = HttpStatusCode.NotFound , data = "exception occur")

        }

    }
}