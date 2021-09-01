package com.task.news.ui.fragment.onboarding.landingCategory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.databinding.CategoryItemBinding
import com.task.news.model.prefsModel.CategoryModel

class CategoryAdapter (
        myList: List<CategoryModel> = listOf()
) :
        RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var trxList: List<CategoryModel> = ArrayList()
    private var parent: ViewGroup? = null

    init {
        this.trxList = myList
    }


    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: CategoryItemBinding =
                CategoryItemBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }

    fun submitMyList(myList: List<CategoryModel>) {
        this.trxList = myList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return trxList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myItemTX = trxList[position]
        holder.bind(holder.myItemTX!!)
    }



    inner class ViewHolder(var itemBinding: CategoryItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root)  , View.OnClickListener{
        var myItemTX: CategoryModel? = null
        val context: Context = itemBinding.root.context
        var selectedStrokeColor = ContextCompat.getColor(context , R.color.blue)
        var defaultStrokeColor = ContextCompat.getColor(context , R.color.white)
        init {
            itemBinding.categoryContainer.setOnClickListener(this)
        }

        fun bind(myItem: CategoryModel?) {
            itemBinding.itemIcon.setImageDrawable(AppCompatResources.getDrawable(context , myItem?.icon!!))
                if (myItem.isSelected){
                    itemBinding.categoryContainer.strokeColor = selectedStrokeColor
                }else{
                    itemBinding.categoryContainer.strokeColor = defaultStrokeColor
                }
            itemBinding.myItem = myItem

        }

        override fun onClick(v: View?) {
                if (myItemTX?.isSelected == true){
                    itemBinding.categoryContainer.strokeColor = defaultStrokeColor
                    myItemTX!!.isSelected = false
                }else{
                    if (getSelectedList().size == 3){
                        Toast.makeText(context , context.getString(R.string.max_cat_violation) , Toast.LENGTH_SHORT).show()
                    }else{
                        itemBinding.categoryContainer.strokeColor = selectedStrokeColor
                        myItemTX!!.isSelected = true
                    }
                }

        }

    }

    fun getSelectedList(): List<CategoryModel>{
        val myReturnedList: ArrayList<CategoryModel> = ArrayList()
        trxList.forEach{ model->
            if (model.isSelected){
                myReturnedList.add(model)
            }
        }
        return myReturnedList
    }
}