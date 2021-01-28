package com.qandeelabbassi.dropsy

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by qandeel.rasheed on 8/12/2020 at 10:28 PM.
 */
class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {
    var fonts = arrayOf(
        "roboto_regular", "roboto_thin", "roboto_light", "roboto_medium",
        "roboto_bold", "roboto_black"
    )

    init {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            0, 0
        )
        val fontType = fonts[a.getInteger(R.styleable.CustomTextView_dropsyFontType, 0)]
        a.recycle()
        applyCustomFont(context, fontType)
    }

    fun applyCustomFont(context: Context, fontName: String) {
        val customFont = FontCache.getTypeface(fontName, context)
        typeface = customFont
    }
}