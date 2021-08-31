package com.task.news.utils

import android.view.View
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import com.task.news.R

object WidgetUtils {
    fun SearchView.setTextColor(@ColorInt color: Int) {
        findViewById<EditText>(R.id.search_src_text).setTextColor(color)
    }
    fun SearchView.setHintTextColor(@ColorInt color: Int) {
        findViewById<EditText>(R.id.search_src_text).setHintTextColor(color)
    }

    fun View.setVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.setInvisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.setGone() {
        this.visibility = View.GONE
    }
}