package com.stash.shopeklobek.ui.checkout

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.ActivityCheckoutBinding
import com.stash.shopeklobek.utils.NavigationExtension.findNavController2
import com.stash.shopeklobek.utils.ViewHelpers

class CheckoutActivity : AppCompatActivity() {

    var activityResultLiveData = MutableLiveData<ActivityResultData?>()
    var activityPermissionResultData = MutableLiveData<ActivityPermissionResultData?>()

    val binding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    val checkoutVM by lazy {
        CheckoutViewModel.create(this)
    }


    fun showLoading(message: String?) {
        dialog = ProgressDialog.show(this, "", message ?: "Loading. Please wait...", true);
    }

    fun hideLoading() {
        dialog?.dismiss()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if(ViewHelpers.getLocale().language!= resources.configuration.locale.language){
            ViewHelpers.setAppLocale(this, resources)
        }


        setupActionBarWithNavController(nav,navConfiguration)

        binding.apply {
            stepperIndicator.apply {
                checkoutVM.pageIndexLiveData.observe(this@CheckoutActivity) {
                    currentStep = it
                }
                addOnStepClickListener {
                    if (currentStep > it && currentStep != it) {
                        currentStep = it
                        for (i in 0..currentStep - it) {
                            nav.popBackStack()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.checkout_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.btnClose){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        binding.stepperIndicator.apply {
            if (currentStep > 0) {
                currentStep -= 1
            }
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.stepperIndicator.apply {
            if (currentStep > 0) {
                currentStep -= 1
            }
        }
        return nav.navigateUp(navConfiguration) || super.onSupportNavigateUp()
    }

    private val nav by lazy {
        binding.navHostFragmentContentMain.findNavController2(this)
    }

    private val navConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.shippingAddressesFragment
            )
        )
    }

    private var dialog: AlertDialog? = null

    data class ActivityResultData(val requestCode: Int, val resultCode: Int, val data: Intent?)
    data class ActivityPermissionResultData(val requestCode: Int, val permissions: Array<out String>, val grantResults: IntArray) {
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

}