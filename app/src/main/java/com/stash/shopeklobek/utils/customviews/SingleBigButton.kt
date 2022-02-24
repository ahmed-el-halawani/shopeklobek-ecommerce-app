package com.stash.shopeklobek.utils.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.ItemSingleBigButtonBinding


/**
 * TODO: document your custom view class.
 */
class SingleBigButton : FrameLayout {

    private var icon: Drawable? = null
    private var title: String? = null

    lateinit var binding: ItemSingleBigButtonBinding

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

//    fun setOnClick(l: OnClickListener?) {
//        binding.btnProceedToCheckout.setOnClickListener(l)
//    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.btnProceedToCheckout.setOnClickListener(l)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SingleBigButton,
            0, 0
        ).apply {

            try {
                title = getString(R.styleable.SingleBigButton_title)
                icon = getDrawable(R.styleable.SingleBigButton_icon)
            } finally {
                recycle()
            }
        }

        binding = ItemSingleBigButtonBinding.bind(inflate(context, R.layout.item_single_big_button, this)).apply {
            tvTitle.text = title
            icon?.also { ivIcon.setImageDrawable(it)}
        }

    }


    override fun onDraw(canvas: Canvas) {
        binding.tvTitle.text = title
        binding.ivIcon.setImageDrawable(icon)
        super.onDraw(canvas)
    }
}