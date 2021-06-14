package com.ceibasoft.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.ceibasoft.user.model.Post
import com.ceibasoft.user.model.User
import okhttp3.*
import okio.IOException

class Request(context: Context) {

    private val url = "https://jsonplaceholder.typicode.com"
    private val client = OkHttpClient()
    private val storage = context.getSharedPreferences(context.packageName + ":request", 0)

    private object ENDPOINTS {
        const val USERS = "/users"
        const val POST = "/posts?userId="
    }

    fun getUserList(cb: (ArrayList<User>) -> Unit) {

        val localJson = storage.getString(User.name, null)
        if (localJson != null) {
            Log.d("***->", "User from local")
            val users = User.fromJSON(localJson)
            cb(users)
            return
        }

        request(ENDPOINTS.USERS) { json ->
            storage.edit().putString(User.name, json).apply()
            val users = User.fromJSON(json)
            cb(users)
        }
    }

    fun getPosts(userId: Int, cb: (ArrayList<Post>) -> Unit) {
        val localJson = storage.getString(Post.name + ":$userId", null)
        if (localJson != null) {
            Log.d("***->", "Post from local for the UserID: $userId")
            val posts = Post.fromJSON(localJson)
            cb(posts)
            return
        }
        request(ENDPOINTS.POST + userId) { json ->
            storage.edit().putString(Post.name+":$userId", json).apply()
            val posts = Post.fromJSON(json)
            cb(posts)
        }
    }

    private fun request(endpoint: String, cb: (String) -> Unit) {
        val req = okhttp3.Request.Builder()
            .url(url + endpoint)
            .build()

        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    cb(response.body!!.string())
                }
            }
        })
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: Request? = null

        fun geInstance(context: Context): Request =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Request(context).also { INSTANCE = it }
            }
    }
}