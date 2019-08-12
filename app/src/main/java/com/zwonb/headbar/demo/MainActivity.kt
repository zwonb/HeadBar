package com.zwonb.headbar.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 常规用法
        head_bar.setTitle("我是标题啊")
        head_bar1.setTitle("我是标题啊", "保存") {
            head_bar1.startLoading()
        }
        head_bar2.setTitle("我是标题啊", R.mipmap.ic_login_quest, img0Click = {
            it.setImageResource(R.drawable.ic_share_black_24dp)
        })
        head_bar3.setTitle("我是标题啊", R.mipmap.ic_login_quest, R.drawable.ic_share_black_24dp, {
            it.setImageResource(R.drawable.ic_share_black_24dp)
        }, {
            it.setImageResource(R.mipmap.ic_login_quest)
        })

        head_bar.backClick {
            finish()
        }

        // 重新设置标题
        head_bar.setTitle("标题啊啊啊", "点我") {
            Log.e("zwonb", "onCreate: 0")
        }
        head_bar1.setTitle("标题啊啊啊", "点我") {
            Log.e("zwonb", "onCreate: 1")
        }
        head_bar2.setTitle("标题啊啊啊", R.drawable.ic_share_black_24dp, img0Click = {
            Log.e("zwonb", "onCreate: 2")
        })
        head_bar3.setTitle("标题啊啊啊", R.drawable.ic_share_black_24dp, R.mipmap.ic_login_quest, {
            Log.e("zwonb", "onCreate: 3-0")
        }, {
            Log.e("zwonb", "onCreate: 3-1")
        })

        // 自定义标题
//        head_bar.titleLayoutRes = R.layout.custom_title
//        head_bar.setTitle("我是自定义标题部分", "自定义")
//        val img = head_bar.titleParent.findViewById<ImageView>(R.id.img)
//        img.setOnClickListener {
//            img.setImageResource(R.drawable.ic_share_black_24dp)
//        }

        // 自定义右边
//        head_bar.rightLayoutRes = R.layout.custom_right
//        head_bar.setTitle("我是自定义右边部分")
//        val img1 = head_bar.rightParent.findViewById<ImageView>(R.id.img)
//        img1.setOnClickListener {
//            img1.setImageResource(R.drawable.ic_share_black_24dp)
//        }

        // 双击回到顶部
        head_bar.titleDoubleClick {
            Log.e("zwonb", "onCreate: 双击了，执行回到顶部操作")
        }
        head_bar1.titleDoubleClick {
            Log.e("zwonb", "onCreate1: 双击了，执行回到顶部操作")
        }

    }
}
