package com.linkiaM13G3.akmAndroidClient.Clients

import android.util.Log
import com.linkiaM13G3.akmAndroidClient.API.Connection
import com.linkiaM13G3.akmAndroidClient.Entities.Tag
import com.linkiaM13G3.akmAndroidClient.Entities.UserSingleton
import retrofit2.HttpException

private val connection = Connection().tagService
class TagClient {
    suspend fun getTags() : List<Tag>? {
        return try {
            connection.getTags(UserSingleton.id)
        }
        catch (e: HttpException) {
            Log.e("Tags", "Error retreving user tags: ${e.message}")
            null
        }
    }

    suspend fun createTag(tag: Tag) : Boolean {
        return try {
            connection.createTag(tag)
        }  catch (e: HttpException) {
            Log.e("Tags", "Error creating tag: ${e.message}")
            false
        }
    }
}