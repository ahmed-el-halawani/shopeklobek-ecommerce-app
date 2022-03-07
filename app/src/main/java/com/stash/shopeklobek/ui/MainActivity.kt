package com.stash.shopeklobek.ui

import android.app.AlertDialog
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.orhanobut.hawk.Hawk
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.ActivityMainBinding
import com.stash.shopeklobek.model.shareprefrances.ISettingsPreferences
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.utils.NavigationExtension.findNavController2

import com.stash.shopeklobek.utils.ViewHelpers

import java.util.*


class MainActivity : AppCompatActivity() {
    var activityResultLiveData = MutableLiveData<ActivityResultData?>()
    var activityPermissionResultData = MutableLiveData<ActivityPermissionResultData?>()


    var onBackNavController: NavController? = null

    val drawerLayout by lazy{
        binding.drawerLayout
    }

    val bottomNavAppBarConfiguration by lazy {
       AppBarConfiguration(
            setOf(
                R.id.brandsFragment,
                R.id.cartFragment, R.id.favoritesFragment, R.id.categoriesFragment,
            ),drawerLayout
        )
    }

    private val drawerAppBarConfiguration by lazy {
       AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_settings, R.id.nav_home,R.id.nav_login,R.id.nav_register
            ),drawerLayout
        )
    }

    val mainNavController by lazy{
        binding.appBarMain.navHostFragmentContentMain.findNavController2(this)
    }

    val viewmodel by lazy {
        MainViewModel.create(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel.settingsLiveData.observe(this){
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        viewmodel.updateLanguage()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.appBarMain.toolbar)

        val navView: NavigationView = binding.navView

        setupActionBarWithNavController(mainNavController, drawerAppBarConfiguration)
        navView.setupWithNavController(mainNavController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.appBarMain.navHostFragmentContentMain.findNavController2(this)
        return onBackNavController?.navigateUp(bottomNavAppBarConfiguration)?:false||
                navController.navigateUp(drawerAppBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultLiveData.postValue(ActivityResultData(requestCode, resultCode, data))
        activityResultLiveData.postValue(null)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        activityPermissionResultData.postValue(ActivityPermissionResultData(requestCode, permissions, grantResults))
        activityPermissionResultData.postValue(null)
    }

    fun showLoading(message:String?) {
        dialog = ProgressDialog.show(this, "",message?:"Loading. Please wait...", true);
    }

    fun hideLoading() {
        dialog?.dismiss()
    }


    data class ActivityResultData(val requestCode: Int, val resultCode: Int, val data: Intent?)
    data class ActivityPermissionResultData(val requestCode: Int, val permissions: Array<out String>,val grantResults: IntArray) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ActivityPermissionResultData

            if (requestCode != other.requestCode) return false
            if (!permissions.contentEquals(other.permissions)) return false
            if (!grantResults.contentEquals(other.grantResults)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = requestCode
            result = 31 * result + permissions.contentHashCode()
            result = 31 * result + grantResults.contentHashCode()
            return result
        }
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var dialog: AlertDialog? = null


    override fun attachBaseContext(newBase: Context?) {
        val language = Hawk.get("language")?:"en"
        val locale = Locale(""+language)
        val res = newBase!!.resources
        val conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, res.displayMetrics)
        super.attachBaseContext(newBase)
    }


}