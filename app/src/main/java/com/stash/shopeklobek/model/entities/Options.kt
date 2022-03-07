package com.stash.shopeklobek.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Options(
    @SerializedName( "values")
    val values:List<String>? = listOf()
) : Parcelable
