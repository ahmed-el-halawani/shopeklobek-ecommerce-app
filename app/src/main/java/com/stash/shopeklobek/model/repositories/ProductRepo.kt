package com.stash.shopeklobek.model.repositories

import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.interfaces.ShopifyServices

class ProductRepo (
    val ShopifyServices: ShopifyServices,
    val settingsPreferences: SettingsPreferences
) {

//      to update settings u can use this
//        settingsPreferences.update {
//            it.copy(
//
//            )
//        }

}