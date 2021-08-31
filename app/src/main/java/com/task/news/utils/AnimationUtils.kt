package com.task.news.utils

import android.view.View
import android.view.animation.TranslateAnimation

object AnimationUtils {
    fun slideFromRightToLeft(view: View) {
        val animate = TranslateAnimation(view.width.toFloat(),0f, 0f, 0f);
        animate.duration = 1000
        animate.fillAfter = true
        view.startAnimation(animate)
    }
}