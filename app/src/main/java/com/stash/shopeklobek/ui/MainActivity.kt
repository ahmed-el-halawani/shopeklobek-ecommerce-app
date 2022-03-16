package com.stash.shopeklobek.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.ActivityMainBinding
import com.stash.shopeklobek.ui.home.HomeFragmentDirections
import com.stash.shopeklobek.utils.Constants
import com.stash.shopeklobek.utils.NavigationExtension.findNavController2
import com.stash.shopeklobek.utils.ViewHelpers.getLocale
import com.stash.shopeklobek.utils.ViewHelpers.setAppLocale
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    var activityResultLiveData = MutableLiveData<ActivityResultData?>()
    var activityPermissionResultData = MutableLiveData<ActivityPermissionResultData?>()


    var onBackNavController: NavController? = null

    val drawerLayout by lazy {
        binding.drawerLayout
    }

    val bottomNavAppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.brandsFragment, R.id.cartFragment, R.id.favoritesFragment,
                R.id.categoriesFragment, R.id.completeAction
            ), drawerLayout
        )
    }

    private val drawerAppBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.nav_profile, R.id.nav_settings, R.id.nav_home, R.id.nav_login, R.id
                    .nav_register, R.id.completeAction
            ), drawerLayout
        )
    }

    val mainNavController by lazy {
        binding.appBarMain.navHostFragmentContentMain.findNavController2(this)
    }

    val viewmodel by lazy {
        MainViewModel.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            lifecycleScope.launch {
                viewmodel.productRepo.updateCurrency()
            }

        }

        if (getLocale().language != resources.configuration.locale.language) {
            setAppLocale(this, resources)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val navView: NavigationView = binding.navView

        setupActionBarWithNavController(mainNavController, drawerAppBarConfiguration)
        navView.setupWithNavController(mainNavController)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                mainNavController.navigate(HomeFragmentDirections.actionNavHomeToNavSearch())
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.appBarMain.navHostFragmentContentMain.findNavController2(this)
        return (navController.currentDestination?.id == R.id.nav_home && onBackNavController?.navigateUp(
            bottomNavAppBarConfiguration
        ) ?: false) ||
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
        activityPermissionResultData.postValue(
            ActivityPermissionResultData(
                requestCode,
                permissions,
                grantResults
            )
        )
        activityPermissionResultData.postValue(null)
    }

    fun showLoading(message: String?) {
        dialog = ProgressDialog.show(this, "", message ?: "Loading. Please wait...", true);
    }

    fun hideLoading() {
        dialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(
            Constants.FILE_NAME,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(Constants.FIRST_FILTER_CATEGORIES, "women")
        editor.putString(Constants.SECOND_FILTER_CATEGORIES, "all")
        editor.putString(Constants.FIRST_FILTER_PRICE, "all")
        editor.apply()

    }


    data class ActivityResultData(val requestCode: Int, val resultCode: Int, val data: Intent?)
    data class ActivityPermissionResultData(
        val requestCode: Int,
        val permissions: Array<out String>,
        val grantResults: IntArray
    ) {
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


}