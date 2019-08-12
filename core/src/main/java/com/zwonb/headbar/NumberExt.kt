package com.zwonb.headbar

import android.content.Context
import android.util.TypedValue

/**
 * number 拓展类
 *
 * @author zwonb
 * @date 2019/8/9
 */

/**
 * dp 转 px
 */
fun Float.dp2px(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this, context.resources.displayMetrics
    ).toInt()
}

fun Int.dp2px(context: Context): Int {
    return this.toFloat().dp2px(context)
}