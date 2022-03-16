package com.stash.shopeklobek.utils.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.model.LatLng
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.ItemAddressCardBinding
import com.stash.shopeklobek.utils.NetworkingHelper
import com.stash.shopeklobek.utils.OnlineMapImageHelper.getImageFromLatLon


/**
 * TODO: document your custom view class.
 */
class AddressCardView : FrameLayout {

    fun setLocationImageResource(image: Int) {
        locationImage = ContextCompat.getDrawable(context, image)
    }

    var locationImage: Drawable? = null
    var locationImageUrl: String? = null
    var title: String? = null
    var address: String? = null
    var address2: String? = null

    inner class ST(val iv:ImageView,val image:String): SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            locationImageUrl = image
            Log.e("locationImageUrl", "onResourceReady: ", )
            refresh()
            iv.setImageDrawable(resource)
        }
    }

    fun setImageFromUrl(latLng: LatLng?) {
        locationImageUrl = null
        refresh()
        if (latLng==null)return
        if (!NetworkingHelper.hasInternet(context)) return
        val image = getImageFromLatLon(latLng)
        Log.e("locationImageUrl", "image: "+image, )
        Glide.with(context).load(image).into(ST(binding.ivAddress,image))
    }

    lateinit var binding: ItemAddressCardBinding

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AddressCardView,
            0, 0
        ).apply {

            try {
                locationImage = getDrawable(R.styleable.AddressCardView_locationImage)

                title = getString(R.styleable.AddressCardView_title)

                address = getString(R.styleable.AddressCardView_address)
            } finally {
                recycle()
            }
        }

        binding = ItemAddressCardBinding.bind(inflate(context, R.layout.item_address_card, this))

        if (locationImage == null && locationImageUrl == null)
            binding.cvAddressCard.visibility = View.GONE
        else {
            binding.cvAddressCard.visibility = View.VISIBLE
            if (locationImageUrl == null)
                binding.ivAddress.setImageDrawable(locationImage)
        }

        binding.tvLocationTitle.text = title
        binding.tvAddress.text = address


    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.cvContainer.setOnClickListener(l)
    }


    fun refresh() {
        if (locationImage == null && locationImageUrl == null)
            binding.cvAddressCard.visibility = View.GONE
        else {
            binding.cvAddressCard.visibility = View.VISIBLE
            if (locationImageUrl == null)
                binding.ivAddress.setImageDrawable(locationImage)
        }

        binding.tvLocationTitle.text = title
        binding.tvAddress.text = address
        binding.tvAddress2.text = address2
    }

    override fun onDraw(canvas: Canvas) {

        if (locationImage == null)
            binding.cvAddressCard.visibility = View.GONE
        else {
            binding.cvAddressCard.visibility = View.VISIBLE
            binding.ivAddress.setImageDrawable(locationImage)
        }

        binding.tvLocationTitle.text = title
        binding.tvAddress.text = address

        super.onDraw(canvas)
    }
}