package com.example.repository.news

import com.example.util.Response

interface NewsRepository {

    suspend fun getAllNews(): Response<String>

}