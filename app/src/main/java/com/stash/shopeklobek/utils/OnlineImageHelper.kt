package com.stash.shopeklobek.utils

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import retrofit2.http.Url
import java.net.URI
import java.net.URL

object OnlineMapImageHelper {

    const val BASE_URL = "maps.geoapify.com"
    const val API_KEY = "c1afab10544748dea1164cea3c06a4c2"
    const val STYLE= "maptiler-3d&width=1000&height=600"



    fun getImageFromLatLon(latLng: LatLng,zoom:Int=14):String{
       return "https://$BASE_URL/v1/staticmap?apiKey=$API_KEY&marker=${generateMarker(latLng)}&zoom=$zoom&center=${getCenter(latLng)}&style=$STYLE"
    }

    fun generateMarker(latLng: LatLng):String{
        return "lonlat:${latLng.longitude},${latLng.latitude};type:awesome;color:red;size:small;icon:cloud;iconsize:small;textsize:small"
    }

    fun getCenter(latLng: LatLng):String{
        return "lonlat:${latLng.longitude},${latLng.latitude}"
    }

}