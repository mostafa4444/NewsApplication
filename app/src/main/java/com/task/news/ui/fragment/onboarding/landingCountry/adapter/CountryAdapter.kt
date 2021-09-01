package com.task.news.ui.fragment.onboarding.landingCountry.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.databinding.CountryItemBinding
import com.task.news.model.prefsModel.CountryModel
import kotlin.collections.ArrayList

class CountryAdapter (
    myList: List<CountryModel> = listOf()
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    var trxList: List<CountryModel> = ArrayList()
    private var checkedPosition = 0
    private var parent: ViewGroup? = null


    init {
        this.trxList = myList
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: CountryItemBinding =
            CountryItemBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }

    fun submitMyList(myList: List<CountryModel>) {
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



    inner class ViewHolder(var Binding: CountryItemBinding) :
        RecyclerView.ViewHolder(Binding.root)  , View.OnClickListener{
        var myItemTX: CountryModel? = null
        val context: Context = Binding.root.context
        var selectedStrokeColor = ContextCompat.getColor(context , R.color.main_color)
        var defaultStrokeColor = ContextCompat.getColor(context , R.color.white)
        init {
            Binding.countryContainer.setOnClickListener(this)
        }

        fun bind(myItem: CountryModel?) {
            if (checkedPosition == -1){
                Binding.countryContainer.strokeColor = defaultStrokeColor
            }else{
                if (checkedPosition == bindingAdapterPosition){
                    Binding.countryContainer.strokeColor = selectedStrokeColor
                }else{
                    Binding.countryContainer.strokeColor = defaultStrokeColor
                }
            }
            Binding.itemIcon.setImageDrawable(AppCompatResources.getDrawable(Binding.root.context , myItem?.icon!!))
            Binding.myItem = myItem
        }

        override fun onClick(v: View?) {
            Binding.countryContainer.strokeColor = selectedStrokeColor
            if (checkedPosition != bindingAdapterPosition){
                notifyItemChanged(checkedPosition)
                checkedPosition = bindingAdapterPosition
            }
        }
    }

    fun getCheckedCountry(): CountryModel? {
        if (checkedPosition != -1){
            return trxList[checkedPosition]
        }
        return null
    }
}