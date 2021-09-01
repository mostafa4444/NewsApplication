package com.task.news.ui.fragment.bottomNavigation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.news.R
import com.task.news.databinding.NewsItemBinding
import com.task.news.model.response.news.Article
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.ArticleClickEvent
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.NewsFavoriteClick
import com.task.news.ui.fragment.bottomNavigation.home.adapter.diffUtils.NewsDiffUtils
import com.task.news.utils.WidgetUtils.setGone

class NewsAdapter (myList: List<Article> = listOf()) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    var trxList: List<Article> = ArrayList()
    private var parent: ViewGroup? = null
    var eventListener: NewsFavoriteClick?= null
    var articleListener: ArticleClickEvent?= null
    var showDelete = false

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

    fun submitMyList(myList: List<Article> , eventListener: NewsFavoriteClick , articleListener: ArticleClickEvent,  showDelete: Boolean = false) {
        this.trxList = myList
        this.eventListener = eventListener
        this.showDelete = showDelete
        this.articleListener = articleListener
        val diffResult =
            DiffUtil.calculateDiff(
                NewsDiffUtils(
                    this.trxList.toList(),
                    myList
                )
            )
        diffResult.dispatchUpdatesTo(this)
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
            itemBinding.favLinear.setOnClickListener(this)
            itemBinding.articleContainer.setOnClickListener(this)
        }

        fun bind(myItem: Article?) {
            if (showDelete){
                itemBinding.favIcon.setDelete()
            }else{
                itemBinding.favIcon.setFavorite()
            }
            myItem?.urlToImage?.let {
                Glide.with(itemBinding.root.context).load(it)
                    .apply(RequestOptions().error(R.drawable.ic_launcher_background))
                    .into(itemBinding.itemImg)
            }
            itemBinding.myItem = myItem
        }

        override fun onClick(v: View?) {
            when(v){
                itemBinding.favLinear->{
                    eventListener?.newClickFavorite(myItemTX!! , bindingAdapterPosition)
                    itemBinding.favLinear.setGone()
                }
                itemBinding.articleContainer->{
                    articleListener?.articleClickEvent(myItemTX!!)
                }
            }
        }
    }

    fun ImageView.setDelete(){
        this.setImageDrawable(ContextCompat.getDrawable(context , R.drawable.ic_baseline_delete_24))
    }
    fun ImageView.setFavorite(){
        this.setImageDrawable(ContextCompat.getDrawable(context , R.drawable.ic_baseline_favorite_border_24))
    }


}