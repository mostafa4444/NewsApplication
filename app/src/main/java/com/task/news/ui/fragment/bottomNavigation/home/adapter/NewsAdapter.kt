package com.task.news.ui.fragment.bottomNavigation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.news.R
import com.task.news.databinding.NewsItemBinding
import com.task.news.model.response.news.Article

class NewsAdapter (myList: List<Article> = listOf()) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    var trxList: List<Article> = ArrayList()
    private var parent: ViewGroup? = null


    init {
        this.trxList = myList
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: NewsItemBinding =
            NewsItemBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }

    fun submitMyList(myList: List<Article>) {
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



    inner class ViewHolder(var itemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)  , View.OnClickListener{
        var myItemTX: Article? = null
        init {
        }

        fun bind(myItem: Article?) {
            myItem?.urlToImage?.let {
                Glide.with(itemBinding.root.context).load(it)
                    .apply(RequestOptions().error(R.drawable.ic_launcher_background))
                    .into(itemBinding.itemImg)
            }
            itemBinding.myItem = myItem
        }

        override fun onClick(v: View?) {
        }
    }

}