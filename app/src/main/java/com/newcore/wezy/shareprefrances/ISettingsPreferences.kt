package com.newcore.wezy.shareprefrances

import androidx.lifecycle.MutableLiveData
import com.stash.shopeklobek.model.shareprefrances.Settings

interface ISettingsPreferences {
    fun insert(settings: Settings)
    fun update(update: (Settings) -> Settings)
    fun get(): MutableLiveData<Settings>
    fun getValue(): Settings
}