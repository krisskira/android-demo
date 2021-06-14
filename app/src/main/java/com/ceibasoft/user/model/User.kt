package com.ceibasoft.user.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Nullable
import org.json.JSONArray
import org.json.JSONException

class User(@Nullable val id: Int? = null, val name: String?, val phone: String?, val email: String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        const val name = "POST"
        private object KEYS {
            const val ID = "id"
            const val NAME = "name"
            const val EMAIL = "email"
            const val PHONE = "phone"
        }
        fun fromJSON(json: String): ArrayList<User> {
            val users: ArrayList<User> = ArrayList()
            try {
                val jsonArray = JSONArray(json)
                for (i in 0 until jsonArray.length()) {
                    val jsonUser = jsonArray.getJSONObject(i)
                    val objUser = User(
                        id = jsonUser.getInt(KEYS.ID),
                        name = jsonUser.getString(KEYS.NAME),
                        email = jsonUser.getString(KEYS.EMAIL),
                        phone = jsonUser.getString(KEYS.PHONE),
                    )
                    users.add(objUser)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return users
        }
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}