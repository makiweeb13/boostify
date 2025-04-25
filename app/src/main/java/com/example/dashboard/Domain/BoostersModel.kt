package com.example.dashboard.Domain

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class BoostersModel(
    val Address:String="",
    val Biography:String="",
    val Id:Int=0,
    val Name:String="",
    val Picture:String="",
    val Special:String="",
    val Expriense:Int=0,
    val Location:String="",
    val Mobile:String="",
    val Clients:String="",
    val Rating:Double=0.0,
    val Site:String=""
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString()
    ){

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Address)
        parcel.writeString(Biography)
        parcel.writeInt(Id)
        parcel.writeString(Name)
        parcel.writeString(Picture)
        parcel.writeString(Special)
        parcel.writeInt(Expriense)
        parcel.writeString(Location)
        parcel.writeString(Mobile)
        parcel.writeString(Clients)
        parcel.writeDouble(Rating)
        parcel.writeString(Site)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BoostersModel> {
        override fun createFromParcel(parcel: Parcel): BoostersModel {
            return BoostersModel(parcel)
        }

        override fun newArray(size: Int): Array<BoostersModel?> {
            return arrayOfNulls(size)
        }
    }
}