package com.omyrobin.netkotlin.main

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.omyrobin.netkotlin.R
import com.omyrobin.netkotlin.R.layout.activity_main
import com.omyrobin.netkotlin.base.BaseVMActivity
import com.omyrobin.netkotlin.bean.UserListBean
import com.omyrobin.network.NetworkManager
import com.omyrobin.network.config.DefaultNetConfig
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseVMActivity<MainViewModel>() {

    private var i: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkManager.init(this, DefaultNetConfig())

        setContentView(activity_main)
        tv_user_list.setOnClickListener{
            getUserList()
        }
    }

    private fun getUserList(){
        i.let {
            vm.getUserList()

            val a = UserListBean()
            a.name = ""

            vm.success.value = mutableListOf(a)
        }
    }

    private fun initData() {

        //请求成功监听
        vm.success.observe(this , Observer {
            tv_user_list.text = it?.get(i)?.name
        })



        //业务异常监听
        vm.error.observe(this, Observer {
            tv_user_list.text = it
        })
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

}