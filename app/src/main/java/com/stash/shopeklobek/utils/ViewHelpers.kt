package com.stash.shopeklobek.utils

import android.app.Activity
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.hawk.Hawk
import com.stash.shopeklobek.model.shareprefrances.Language
import java.util.*

object ViewHelpers {


    class SwipeToRemove(
        private val swipe: (position: Int) -> Unit,
    ) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition

            swipe(position)

        }


    }

    fun languageEnumFromLocale(): Language {
        return when (Locale.getDefault().language) {
            "en" -> Language.English
            "ar" -> Language.Arabic
            else -> Language.English
        }
    }

    fun languageEnumFromLocale(locale: Locale): Language {
        return when (locale.language) {
            "en" -> Language.English
            "ar" -> Language.Arabic
            else -> Language.English
        }
    }

    fun localeFromLanguage(language: Language): Locale {
        return returnByLanguage(language, Locale("ar"), Locale.ENGLISH)
    }

    fun <T> returnByLanguage(language: Language, arabic: T, english: T): T {
        return when (language) {
            Language.Arabic -> arabic
            Language.English -> english
            Language.Default -> when (languageEnumFromLocale()) {
                Language.Arabic -> arabic
                Language.English -> english
                else -> english
            }
        }
    }

    fun setAppLocale(reBuildActivity: Activity, localeCode: String? = null, resources: Resources) {
        val dm = resources.displayMetrics
        val config = resources.configuration
        config.setLocale(
            Locale(
                localeCode ?: Locale.getDefault().language
            )
        )

        resources.updateConfiguration(config, dm)

        ActivityCompat.recreate(reBuildActivity)
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                0
            )
        }
    }

    fun setAppLocale(localeCode: String? = null, resources: Resources) {
        val dm = resources.displayMetrics
        val config = resources.configuration
        config.setLocale(
            Locale(
                localeCode ?: Locale.getDefault().language
            )
        )

        resources.updateConfiguration(config, dm)

    }

    fun getLocale():Locale{
        val language:String? = Hawk.get("language")
        return if(language == null){
            localeFromLanguage(languageEnumFromLocale())
        }else{
            Locale(""+language)
        }
    }

    fun getLanguageFromSettings():Language{
        val language:String? = Hawk.get("language")
        return if(language == null){
            languageEnumFromLocale()
        }else{
            if(language == "ar"){
                Language.Arabic
            }else{
                Language.English
            }
        }
    }


    fun setAppLocale(reBuildActivity: Activity, resources: Resources) {
        val language:String? = Hawk.get("language")
        val locale = if(language == null){
            localeFromLanguage(languageEnumFromLocale())
        }else{
            Locale(""+language)
        }
        val dm = resources.displayMetrics
        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(config, dm)

        ActivityCompat.recreate(reBuildActivity)

    }



}