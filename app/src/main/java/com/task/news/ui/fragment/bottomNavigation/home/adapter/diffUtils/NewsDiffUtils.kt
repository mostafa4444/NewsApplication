package com.task.news.ui.fragment.bottomNavigation.home.adapter.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.task.news.model.response.news.Article

class NewsDiffUtils (
        private var oldItemList: List<Article>,
        private var newItemList: List<Article>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItemList.size
    }

    override fun getNewListSize(): Int {
        return newItemList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItemList[oldItemPosition].id == newItemList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val merchantItem = oldItemList[oldItemPosition]
        val newMerchantItem = newItemList[newItemPosition]
        return merchantItem.id == newMerchantItem.id
    }


}