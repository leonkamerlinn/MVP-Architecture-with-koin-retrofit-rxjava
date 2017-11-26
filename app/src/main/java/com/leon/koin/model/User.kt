package com.leon.koin.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Leon on 21.11.2017..
 */
class User: Parcelable {
    
    val id: Long
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String
    val url: String
    
    
    constructor(id: Long = NO_FIELD, avatarUrl: String = "", url: String = "") {
        this.id = id
        this.avatarUrl = avatarUrl
        this.url = url
        
    }
    
    constructor(parcel: Parcel) {
        id = parcel.readLong()
        avatarUrl = parcel.readString()
        url = parcel.readString()
    }
    
    val hasId: Boolean
        get() = id != NO_FIELD
    
    
    
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(avatarUrl)
        parcel.writeString(url)
    }
    
    override fun describeContents(): Int = 0
    
    companion object CREATOR : Parcelable.Creator<User> {
        val NO_FIELD = Long.MIN_VALUE
        override fun createFromParcel(parcel: Parcel): User = User(parcel)
        
        override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
    }
}