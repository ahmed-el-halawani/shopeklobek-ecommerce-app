package com.stash.shopeklobek.model.shareprefrances

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.stash.shopeklobek.model.entities.Customer
import com.stash.shopeklobek.model.entities.currencies.Currency
import com.stash.shopeklobek.utils.Constants.ALL_DATA_ROUTE
import com.stash.shopeklobek.utils.CurrencyUtil
import java.util.*

class SettingsPreferences(private val application:Application) : ISettingsPreferences {

    companion object{
        private var instance: SettingsPreferences? = null
        private val Lock = Any()

        operator fun invoke(application:Application) = instance ?:synchronized(Lock){
            instance ?: SettingsPreferences(application)
        }
    }

    val settings:MutableLiveData<Settings> by lazy {
        MutableLiveData<Settings>().apply {
            postValue(getSettings())
        }
    }

    private val sp: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    }

    private fun settingsToJson(settings: Settings):String{
        val json = Gson()
        return json.toJson(settings)
    }

    private fun settingsFromJson(settings:String): Settings {
        val json = Gson()
        return json.fromJson(settings, Settings::class.java)
    }

    override fun insert(settings: Settings) {
        sp.edit {
            putString(ALL_DATA_ROUTE,settingsToJson(settings))
            apply()
        }
        getSettingsLiveData()
    }

    override fun update(update:(Settings)-> Settings) {
        sp.edit {
            putString(ALL_DATA_ROUTE,settingsToJson(update(getSettings())))
            apply()
        }
        getSettingsLiveData()
    }

    override fun getSettingsLiveData(): MutableLiveData<Settings> {
        sp.getString(ALL_DATA_ROUTE,null)?.let { settings.postValue(settingsFromJson(it)) }
        return settings
    }

    override fun getSettings(): Settings {
        println(sp.getString(ALL_DATA_ROUTE,null)?.let { settingsFromJson(it) })
        return sp.getString(ALL_DATA_ROUTE,null)?.let { settingsFromJson(it) }?: Settings.getDefault()
    }

}

data class Settings(
    var language: Language,
    var customer:Customer?,
    var currancy:Currency
    ){
        companion object{
            fun getDefault(): Settings = Settings(
                Language.Arabic,
                null,
                CurrencyUtil.getCurrency(CurrenciesEnum.USD)
            )
        }
    }
enum class Language{
    English,Arabic,Default
}

enum class CurrenciesEnum {
    EGP, USD, EUR
}