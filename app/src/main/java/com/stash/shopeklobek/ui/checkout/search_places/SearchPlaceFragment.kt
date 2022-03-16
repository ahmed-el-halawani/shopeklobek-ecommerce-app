package com.stash.shopeklobek.ui.checkout.search_places

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stash.shopeklobek.databinding.FragmentSearchPlaceBinding
import com.stash.shopeklobek.ui.checkout.add_address.AddAddressViewModel


class SearchPlaceFragment() : BottomSheetDialogFragment() {

    val binding by lazy {
        FragmentSearchPlaceBinding.inflate(layoutInflater)
    }


    val vm by lazy {
        SearchPlacesViewModel.create(this)
    }

    val vmShared by lazy {
        AddAddressViewModel.getInstance(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState==null){
            binding.etSearchPlaces.requestFocus();
//            val imm: InputMethodManager? = getSystemService(
//                requireContext(),
//                InputMethodManager::class.java
//            )
//            imm?.showSoftInput(binding.etSearchPlaces, InputMethodManager.SHOW_IMPLICIT)
            dialog?.window?.setSoftInputMode(SOFT_INPUT_STATE_VISIBLE);

        }

        vm.loading.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                false -> {
                    binding.pbLoading.visibility = View.GONE
                }
            }
        }

        setupRecyclerView()

        binding.run {
            btnClose.setOnClickListener {
                dismiss()
            }
            etSearchPlaces.doOnTextChanged { s, _, _, _ ->
                vm.findAddress(s.toString())
            }
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }

        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.let { bottomSheet ->
                val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
                behavior.setBottomSheetCallback(object : BottomSheetCallback() {
                    override fun onStateChanged(view: View, i: Int) {
                        /*if (BottomSheetBehavior.STATE_EXPANDED == i) {

                        }*/
                        if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                            dismiss()
                        }
                        if (BottomSheetBehavior.STATE_HIDDEN == i) {
                            dismiss()
                        }
                    }

                    override fun onSlide(view: View, v: Float) {}
                })
                val layoutParams = bottomSheet.layoutParams
                val windowHeight = getWindowHeight()
                if (layoutParams != null) {
                    layoutParams.height = windowHeight
                }
                bottomSheet.layoutParams = layoutParams
                behavior.peekHeight = 0
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    private val searchResultAdapter by lazy {
        SearchedAddressesAdapter().apply {
            vm.addressList.observe(viewLifecycleOwner) {
                differ.submitList(it.features)
            }
            setOnItemClickListener {
                vmShared.setSearchedAddress(it)
                dismiss()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvSearchedPlaces.apply {
            adapter = searchResultAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

}