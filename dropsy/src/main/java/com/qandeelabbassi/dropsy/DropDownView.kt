package com.qandeelabbassi.dropsy

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.card.MaterialCardView
import com.skydoves.powermenu.CustomPowerMenu
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnDismissedListener
import com.skydoves.powermenu.OnMenuItemClickListener
import kotlinx.android.synthetic.main.dropsy_layout_drop_down.view.*


/**
 * Created by qandeel.rasheed on 1/19/2021 at 9:11 PM.
 */
class DropDownView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : MaterialCardView(context, attrs, defStyleAttr),
        OnMenuItemClickListener<DropDownItem>, OnDismissedListener, View.OnClickListener {

    private lateinit var dropDownItems: List<DropDownItem>
    private val dropDownAdapter = DropDownAdapter()
    private val dropDownPopup: CustomPowerMenu<DropDownItem?, DropDownAdapter?> by lazy {
        val popup: CustomPowerMenu<DropDownItem?, DropDownAdapter?> =
                CustomPowerMenu.Builder(context, dropDownAdapter)
                        .setWidth(width)
                        .addItemList(dropDownItems)
                        .setMenuRadius(radius)
                        .setPadding(resources.getDimension(R.dimen.dropsy_white_space_margin).toInt())
                        .setShowBackground(false)
                        .setOnDismissListener(this)
                        .setDismissIfShowAgain(true)
                        .setFocusable(true)
                        .setOnMenuItemClickListener(this)
                        .setAnimation(MenuAnimation.DROP_DOWN)
                        .setLifecycleOwner(context as LifecycleOwner)
                        .build()
        if (dropDownItems.isNotEmpty())
            dropDownAdapter.setSelection(0, dropDownItems[0])
        popup
    }

    init {
        // Add body layout
        val dropDownBody = LayoutInflater.from(context).inflate(
                R.layout.dropsy_layout_drop_down,
                this,
                false
        ) as LinearLayout
        addView(dropDownBody)

        // get attrs
        val dropsyAttrs = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.Dropsy,
                0, 0
        )
        setStyles(dropsyAttrs)
        initData(dropsyAttrs)
        dropsyAttrs.recycle()

        setOnClickListener(this)
    }

    private fun setStyles(dropsyAttrs: TypedArray) {
        val resources = context.resources
        val dropsyElevation = dropsyAttrs.getDimension(R.styleable.Dropsy_dropsyElevation, 0.0f)

        val padding = resources.getDimension(R.dimen.dropsy_dropdown_padding).toInt()
        setContentPadding(padding, padding, padding, padding)
        setStrokeColor(ContextCompat.getColorStateList(context, R.color.dropsy_stroke_state_list))
        radius = resources.getDimension(R.dimen.dropsy_dropdown_corner_radius)
        strokeWidth = resources.getDimension(R.dimen.dropsy_dropdown_stroke_width).toInt()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            elevation = dropsyElevation
    }

    private fun initData(dropsyAttrs: TypedArray) {
        val dropsyLabel = dropsyAttrs.getString(R.styleable.Dropsy_dropsyLabel)
        dropDownItems = dropsyAttrs.getTextArray(R.styleable.Dropsy_dropsyItems)?.map {
            DropDownItem(it.toString())
        } ?: listOf()

        txt_drop_drown_label.text = dropsyLabel
        if (dropsyLabel.isNullOrBlank())
            txt_drop_drown_label.visibility = View.GONE
        if (dropDownItems.isNotEmpty())
            txt_drop_drown_value.text = dropDownItems[0].text
    }

    override fun onClick(v: View?) {
        if (isSelected)
            dismiss()
        else
            showDropDown()
    }

    override fun onItemClick(position: Int, item: DropDownItem?) {
        txt_drop_drown_value.text = item?.text
        dropDownAdapter.setSelection(position, item)
        dismiss()
    }

    override fun onDismissed() {
        isSelected = false
        animateCollapse()
    }

    fun showDropDown() {
        isSelected = true
        dropDownPopup.showAsDropDown(this)
        animateExpand()
    }

    fun dismiss() {
        postDelayed({
            dropDownPopup.dismiss()
        }, 150)
    }

    private fun animateExpand() {
        val rotate = RotateAnimation(
                360f,
                180f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        )
        rotate.duration = 300
        rotate.fillAfter = true
        img_arrow.startAnimation(rotate)
    }

    private fun animateCollapse() {
        val rotate = RotateAnimation(
                180f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        )
        rotate.duration = 300
        rotate.fillAfter = true
        img_arrow.startAnimation(rotate)
    }
}