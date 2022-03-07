package com.stash.shopeklobek.model.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images (
    @SerializedName("src") val src : String
) : Parcelable
