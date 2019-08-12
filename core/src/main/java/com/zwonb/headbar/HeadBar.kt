package com.zwonb.headbar

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 标题栏
 *
 * @author zwonb
 * @date 2019/8/8
 */
class HeadBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val leftImg by lazy(LazyThreadSafetyMode.NONE) { initLeftImg() }
    val leftLine by lazy(LazyThreadSafetyMode.NONE) { initLeftLine() }

    val rightParent by lazy(LazyThreadSafetyMode.NONE) { initRightLayout() }
    val rightImg0 by lazy(LazyThreadSafetyMode.NONE) { initRightImg0() }
    val rightImg1 by lazy(LazyThreadSafetyMode.NONE) { initRightImg1() }
    val rightText by lazy(LazyThreadSafetyMode.NONE) { initRightText() }
    /**
     * 自定义右边资源id
     */
    @LayoutRes
    var rightLayoutRes: Int = 0


    val titleParent by lazy(LazyThreadSafetyMode.NONE) { initLeftTitleLayout() }
    var title: TextView? = null
    var loadingBar: ProgressBar? = null
    /**
     * 自定义标题资源id
     */
    @LayoutRes
    var titleLayoutRes: Int = 0

    private var titleClickTime = 0L

    init {
        setBackgroundResource(R.color.head_bar_bg)
        minHeight = 48.dp2px(context)
        addView(leftImg)
        addView(leftLine)
        addView(titleParent)
        addView(rightParent)
    }

    fun setTitle(title: CharSequence, rightText: CharSequence? = null, click: ((titleTv: TextView) -> Unit)? = null) {
        addTitleView(title)

        addRightView(rightText)

        if (rightText != null) {
            this.rightText.setOnClickListener { click?.invoke(this.rightText) }
        } else {
            this.rightText.setOnClickListener(null)
        }
    }

    fun setTitle(title: CharSequence, @DrawableRes rightImg0: Int? = 0, @DrawableRes rightImg1: Int? = 0,
                 img0Click: ((img: ImageView) -> Unit)? = null, img1Click: ((img: ImageView) -> Unit)? = null) {
        if (this.title == null) {
            addTitleView(title)
        }
        this.title?.text = title

        addRightView(null, rightImg0, rightImg1)

        if (img0Click != null) {
            this.rightImg0.setOnClickListener { img0Click(this.rightImg0) }
        } else {
            this.rightImg0.setOnClickListener(null)
        }
        if (img1Click != null) {
            if (rightImg1 != 0) {
                this.rightImg1.setOnClickListener { img1Click(this.rightImg1) }
            }
        } else {
            this.rightImg1.setOnClickListener(null)
        }
    }

    fun startLoading() {
        loadingBar?.let {
            if (it.visibility != View.VISIBLE) {
                it.visibility = View.VISIBLE
            }
        }
    }

    fun stopLoading() {
        loadingBar?.let {
            if (it.visibility != View.GONE) {
                it.visibility = View.GONE
            }
        }
    }

    /**
     * 返回
     */
    fun backClick(back: (View) -> Unit) {
        leftImg.setOnClickListener(back)
    }

    /**
     * 双击
     */
    fun titleDoubleClick(click: (ConstraintLayout) -> Unit) {
        titleParent.setOnClickListener {
            val currentTimeMillis = System.currentTimeMillis()
            if ((currentTimeMillis - titleClickTime) < 500) {
                click(titleParent)
            }
            titleClickTime = currentTimeMillis
        }
    }

    /**
     * 添加标题布局，如果自定义不为空就添加自定义布局，否则添加默认标题
     */
    private fun addTitleView(title: CharSequence) {
        val layout = if (titleLayoutRes != 0) {
            LayoutInflater.from(context).inflate(titleLayoutRes, titleParent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.head_bar_left_title, titleParent, false)
        }
        this.title = layout.findViewById<TextView>(R.id.head_bar_title)
        this.title?.text = title
        loadingBar = layout.findViewById<ProgressBar>(R.id.head_bar_loading)
        loadingBar?.visibility = View.GONE
        titleParent.removeAllViews()
        titleParent.addView(layout)
    }

    private fun addRightView(rightText: CharSequence?, rightImg0: Int? = 0, rightImg1: Int? = 0) {
        when {
            rightLayoutRes != 0 -> {
                val layout = LayoutInflater.from(context).inflate(rightLayoutRes, rightParent, false)
                rightParent.removeAllViews()
                rightParent.addView(layout)
            }
            rightText != null -> {
                this.rightText.text = rightText
                rightParent.removeAllViews()
                if (rightParent.indexOfChild(this.rightText) == -1) {
                    rightParent.addView(this.rightText)
                }
            }
            rightImg0 != 0 && rightImg1 != 0 -> {
                this.rightImg0.setImageResource(rightImg0!!)
                rightParent.removeAllViews()
                if (rightParent.indexOfChild(this.rightImg0) == -1) {
                    rightParent.addView(this.rightImg0)
                }
                this.rightImg1.setImageResource(rightImg1!!)
                if (rightParent.indexOfChild(this.rightImg1) == -1) {
                    rightParent.addView(this.rightImg1)
                }
            }
            rightImg0 != 0 -> {
                this.rightImg0.setImageResource(rightImg0!!)
                rightParent.removeAllViews()
                if (rightParent.indexOfChild(this.rightImg0) == -1) {
                    rightParent.addView(this.rightImg0)
                }
            }
        }
    }

    private fun initLeftImg(): ImageView {
        val img = ImageView(context)
        img.id = View.generateViewId()
        img.scaleType = ImageView.ScaleType.CENTER_INSIDE
        img.setImageResource(R.drawable.head_bar_ic_back)
        img.setBackgroundResource(R.drawable.head_bar_back_bg)

        val lp = LayoutParams(48.dp2px(context), 48.dp2px(context))
        lp.startToStart = LayoutParams.PARENT_ID
        lp.topToTop = LayoutParams.PARENT_ID
        lp.bottomToBottom = LayoutParams.PARENT_ID
        img.layoutParams = lp
        return img
    }

    private fun initLeftLine(): ImageView {
        val lp = LayoutParams(1.dp2px(context), 42.dp2px(context))
        lp.startToEnd = leftImg.id
        lp.topToTop = leftImg.id
        lp.bottomToBottom = leftImg.id
        val img = ImageView(context)
        img.id = View.generateViewId()
        img.setImageResource(R.drawable.head_bar_divider)
        img.layoutParams = lp
        return img
    }

    private fun initRightLayout(): LinearLayout {
        val ll = LinearLayout(context)
        ll.id = View.generateViewId()
        ll.gravity = Gravity.CENTER_VERTICAL
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        lp.endToEnd = LayoutParams.PARENT_ID
        lp.topToTop = LayoutParams.PARENT_ID
        lp.bottomToBottom = LayoutParams.PARENT_ID
        ll.layoutParams = lp
        return ll
    }

    private fun initRightImg0(): ImageView {
        val img = ImageView(context)
        img.id = View.generateViewId()
        img.setBackgroundResource(R.drawable.head_bar_back_bg)
        val pd = 12.dp2px(context)
        img.setPadding(pd, pd, pd, pd)

        val lp = LinearLayout.LayoutParams(48.dp2px(context), 48.dp2px(context))
        lp.gravity = Gravity.CENTER_VERTICAL
        img.layoutParams = lp
        return img
    }

    private fun initRightImg1(): ImageView {
        val img = ImageView(context)
        img.id = View.generateViewId()
        img.setBackgroundResource(R.drawable.head_bar_back_bg)
        val pd = 12.dp2px(context)
        img.setPadding(pd, pd, pd, pd)

        val lp = LinearLayout.LayoutParams(48.dp2px(context), 48.dp2px(context))
        lp.gravity = Gravity.CENTER_VERTICAL
        img.layoutParams = lp
        return img
    }

    private fun initRightText(): TextView {
        val tv = TextView(context)
        tv.textSize = 14f
        tv.gravity = Gravity.CENTER
        tv.setBackgroundResource(R.drawable.head_bar_back_bg)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Widget_ActionBar_Menu)
        }
        tv.setTextColor(Color.WHITE)
        tv.text = "确定"

        val lp = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 48.dp2px(context))
        lp.marginEnd = 8.dp2px(context)
        tv.layoutParams = lp
        return tv
    }

    private fun initLeftTitleLayout(): ConstraintLayout {
        val cl = ConstraintLayout(context)
        val lp = LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT)
        lp.startToEnd = leftLine.id
        lp.topToTop = LayoutParams.PARENT_ID
        lp.bottomToBottom = LayoutParams.PARENT_ID
        lp.endToStart = rightParent.id
        cl.layoutParams = lp
        return cl
    }

}