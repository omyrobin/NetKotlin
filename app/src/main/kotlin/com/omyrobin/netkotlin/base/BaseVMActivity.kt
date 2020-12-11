package com.omyrobin.netkotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 2:45 PM
 * @Description:
 */
open class BaseVMActivity<VM: BaseViewModel> : AppCompatActivity() {

    lateinit var vm : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //获取泛型VM的真实类型
        val parameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        val type = parameterizedType.actualTypeArguments.first()

        vm = ViewModelProvider(this).get(type as Class<VM>)
    }
}