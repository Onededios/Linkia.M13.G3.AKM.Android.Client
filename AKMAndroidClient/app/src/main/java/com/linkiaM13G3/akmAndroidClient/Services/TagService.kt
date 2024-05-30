package com.linkiaM13G3.akmAndroidClient.Services

import com.linkiaM13G3.akmAndroidClient.Entities.Tag
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID

interface TagService {
    @GET("/Tag/GetUserTags")
    suspend fun getTags(@Query("id_user") id: UUID ): List<Tag>?
    @POST("Tag/CreateTag")
    suspend fun createTag(@Body tag: Tag): Boolean
}