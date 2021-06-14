package com.ceibasoft.user.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Nullable
import org.json.JSONArray
import org.json.JSONException

class Post(@Nullable val id: Int? = null, val title: String?, val body: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        const val name = "POST"
        private object KEYS {
            const val ID = "id"
            const val TITLE = "title"
            const val BODY = "body"
        }

        fun fromJSON(json: String): ArrayList<Post> {
            val posts: ArrayList<Post> = ArrayList()
            try {
                val jsonArray = JSONArray(json)
                for (i in 0 until jsonArray.length()) {
                    val jsonPosts = jsonArray.getJSONObject(i)
                    val objPost = Post(
                        jsonPosts.getInt(KEYS.ID),
                        jsonPosts.getString(KEYS.TITLE),
                        jsonPosts.getString(KEYS.BODY),
                    )
                    posts.add(objPost)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return posts
        }

        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}