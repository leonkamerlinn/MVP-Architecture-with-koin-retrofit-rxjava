package com.leon.koin.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Leon on 16.11.2017..
 */

class Repo: Parcelable {
    val id: Long
    val name: String
    
    @SerializedName("full_name")
    @Expose
    val fullName: String
    val owner: User
   
    constructor(id: Long = NO_FIELD, name: String = "", fullName: String = "", owner: User = User()) {
        this.id = id
        this.name = name
        this.fullName = fullName
        this.owner = owner
    }
    
    constructor(parcel: Parcel) {
        id = parcel.readLong()
        name = parcel.readString()
        fullName = parcel.readString()
        owner = parcel.readParcelable(User::class.java.classLoader)
    }
    
    
    val hasId: Boolean
        get() = id != NO_FIELD
    
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(fullName)
        parcel.writeParcelable(owner, flags)
    }
    
    override fun describeContents(): Int = 0
    
    companion object CREATOR : Parcelable.Creator<Repo> {
        val NO_FIELD = Long.MIN_VALUE
        override fun createFromParcel(parcel: Parcel): Repo = Repo(parcel)
        
        override fun newArray(size: Int): Array<Repo?> = arrayOfNulls(size)
    }
}
