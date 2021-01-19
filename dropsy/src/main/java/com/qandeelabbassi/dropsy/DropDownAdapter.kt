package com.qandeelabbassi.dropsy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.skydoves.powermenu.MenuBaseAdapter

/**
 * Created by qandeel.rasheed on 1/19/2021 at 11:55 PM.
 */
class DropDownAdapter internal constructor() :
    MenuBaseAdapter<DropDownItem?>() {
    private var selectedIndex = -1

    override fun getView(index: Int, v: View?, viewGroup: ViewGroup): View {
        var view: View? = v
        val context: Context = viewGroup.context
        if (view == null) {
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.dropsy_item_drop_down, viewGroup, false)
        }
        val item = getItem(index) as DropDownItem
        val txtLabel: CustomTextView? = view?.findViewById(R.id.txt_label)
        //val imgCheck = view?.findViewById<ImageView>(R.id.img_check)

        txtLabel?.text = item.text
        if (item.checked)
            txtLabel?.applyCustomFont(context, "roboto_bold")
        else
            txtLabel?.applyCustomFont(context, "roboto_regular")

        //imgCheck?.visibility = if (item.checked) View.VISIBLE else View.INVISIBLE
        return super.getView(index, view, viewGroup)
    }

    fun setSelection(index: Int, item: DropDownItem?) {
        if (selectedIndex != -1 && selectedIndex < itemList.size)
            (getItem(selectedIndex) as DropDownItem).toggleState()
        item?.toggleState()
        notifyDataSetChanged()
        selectedIndex = index
    }
}