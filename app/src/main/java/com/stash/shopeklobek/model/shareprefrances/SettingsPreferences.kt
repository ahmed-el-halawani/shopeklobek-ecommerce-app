package com.newcore.wezy.shareprefrances

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.stash.shopeklobek.model.entities.Customer
import com.stash.shopeklobek.utils.Constants.ALL_DATA_ROUTE
import java.io.Serializable

class SettingsPreferences(private val application:Application) {

    companion object{
        private var instance: SettingsPreferences? = null
        private val Lock = Any()

        operator fun invoke(application:Application) = instance ?:synchronized(Lock){
            instance ?: SettingsPreferences(application)
        }
    }

    val settings:MutableLiveData<Settings> by lazy {
        MutableLiveData<Settings>().apply {
            postValue(Settings.getDefault())
        }
    }

    private val sp: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    }

    private fun settingsToJson(settings:Settings):String{
        val json = Gson()
        return json.toJson(settings)
    }

    private fun settingsFromJson(settings:String):Settings{
        val json = Gson()
        return json.fromJson(settings,Settings::class.java)
    }

    fun insert(settings: Settings) {
        sp.edit {
            putString(ALL_DATA_ROUTE,settingsToJson(settings))
            apply()
        }
    }

    fun update(update:(Settings)->Settings) {
        sp.edit {
            putString(ALL_DATA_ROUTE,settingsToJson(update(settings.value?:Settings.getDefault())))
            apply()
        }
    }

    fun get(): MutableLiveData<Settings> {
        sp.getString(ALL_DATA_ROUTE,null)?.let { settings.postValue(settingsFromJson(it)) }
        return settings
    }

    fun getValue(): Settings {
        return sp.getString(ALL_DATA_ROUTE,null)?.let { settingsFromJson(it) }?:Settings.getDefault()
    }

}

data class Settings(
    var language:Language,
    var customer:Customer?,
    ){
        companion object{
            fun getDefault():Settings=Settings(
                Language.Default,
                null
            )
        }
    }
enum class Language{
    Arabic,English,Default
}