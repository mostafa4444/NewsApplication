package com.task.news.ui.fragment.bottomNavigation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.databinding.CountryItemBinding
import com.task.news.databinding.SelectedCategoryItemBinding
import com.task.news.model.prefsModel.CategoryModel

class SelectedCategoryRecycler  (
        myList: List<CategoryModel> = listOf()
) :
        RecyclerView.Adapter<SelectedCategoryRecycler.ViewHolder>() {
    var trxList: List<CategoryModel> = ArrayList()
    private var checkedPosition = 0
    private var parent: ViewGroup? = null
    var categoriesClickListener: CategoryClickEvent ?= null

    init {
        this.trxList = myList
    }


    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: SelectedCategoryItemBinding =
                SelectedCategoryItemBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }

    fun submitMyList(myList: List<CategoryModel> , categoriesClickListener: CategoryClickEvent) {
        this.trxList = myList
        this.categoriesClickListener = categoriesClickListener
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return trxList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myItemTX = trxList[position]
        holder.bind(holder.myItemTX!!)
    }



    inner class ViewHolder(var Binding: SelectedCategoryItemBinding) :
            RecyclerView.ViewHolder(Binding.root)  , View.OnClickListener{
        var myItemTX: CategoryModel? = null
        init {
            Binding.categoryContainer.setOnClickListener(this)
        }

        fun bind(myItem: CategoryModel?) {
            Binding.myItem = myItem
            if (checkedPosition == -1){
                Binding.categoryContainer.deSelectBg()
                Binding.nameTxt.deSelectTextColor()
            }else{
                if (checkedPosition == bindingAdapterPosition){
                    Binding.categoryContainer.setSelectedBg()
                    Binding.nameTxt.setSelectedTextColor()
                }else{
                    Binding.categoryContainer.deSelectBg()
                    Binding.nameTxt.deSelectTextColor()
                }
            }
        }

        override fun onClick(v: View?) {
            Binding.categoryContainer.setSelectedBg()
            Binding.nameTxt.setSelectedTextColor()
            if (checkedPosition != bindingAdapterPosition){
                notifyItemChanged(checkedPosition)
                checkedPosition = bindingAdapterPosition
            }
            categoriesClickListener?.categoryClicked(myItemTX!!)
        }
    }

    fun View.setSelectedBg(){
        this.background = ContextCompat.getDrawable(this.context , R.drawable.custom_text_view_selected)
    }

    fun View.deSelectBg(){
        this.background = ContextCompat.getDrawable(this.context , R.drawable.custom_text_view)
    }
    fun TextView.setSelectedTextColor(){
        this.setTextColor(ContextCompat.getColor(this.context , R.color.white))
    }

    fun TextView.deSelectTextColor(){
        this.setTextColor(ContextCompat.getColor(this.context , R.color.main_color))
    }

    fun getCheckedCategory(): CategoryModel? {
        if (checkedPosition != -1){
            return trxList[checkedPosition]
        }
        return null
    }
}