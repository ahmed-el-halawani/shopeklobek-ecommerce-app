package com.stash.shopeklobek.ui.checkout

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.ui.AppBarConfiguration
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.ActivityCheckoutBinding
import com.stash.shopeklobek.databinding.ActivityMainBinding
import com.stash.shopeklobek.ui.MainActivity
import com.stash.shopeklobek.utils.NavigationExtension.findNavController2

class CheckoutActivity : AppCompatActivity() {

    var activityResultLiveData = MutableLiveData<ActivityResultData?>()
    var activityPermissionResultData = MutableLiveData<ActivityPermissionResultData?>()


    val binding by lazy{
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    val checkoutVM by lazy{
        CheckoutViewModel.create(this)
    }

    val nav by lazy{
        binding.navHostFragmentContentMain.findNavController2(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            stepperIndicator.apply {
                checkoutVM.pageLiveData.observe(this@CheckoutActivity){
                    currentStep = it
                }
                addOnStepClickListener {
                    if(currentStep>it && currentStep!= it && currentStep!=stepCount-1){
                        currentStep = it
                        for( i in 0..currentStep-it){
                            nav.popBackStack()
                        }
                    }
                }
            }
        }

    }

    override fun onBackPressed() {

        if(checkoutVM.pageLiveData.value == binding.stepperIndicator.stepCount-1){
            finish()
        }else{
            binding.stepperIndicator.apply {
                if(currentStep>0){
                    currentStep -= 1
                }
            }
            super.onBackPressed()
        }
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
    private var dialog: AlertDialog? = null

}