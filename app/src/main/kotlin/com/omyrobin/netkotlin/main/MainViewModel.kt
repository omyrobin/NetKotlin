package com.omyrobin.netkotlin.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.omyrobin.netkotlin.base.BaseViewModel
import com.omyrobin.netkotlin.bean.UserListBean
import com.omyrobin.network.exception.HandlerException
import com.omyrobin.network.exception.IError
import kotlinx.coroutines.launch

/**
 * @Author: omyrobin
 * @CreateDate: 2020/11/16 3:05 PM
 * @Description:
 */
class MainViewModel : BaseViewModel() {

    private val mainRepository = MainRepository()

    private var _success = MutableLiveData<List<UserListBean>?>()
    val success: MutableLiveData<List<UserListBean>?>
        get() = _success

    private var _error = MutableLiveData<String>()
    val error: MutableLiveData<String>
        get() = _error


    fun getUserList() {
        //不需要处理业务异常
        viewModelScope.launch(HandlerException()) {
            _success.value = mainRepository.userListRepository()
        }

        //需要处理业务异常
        viewModelScope.launch(HandlerException(object : IError {
            override fun onError(code: Int, msg: String?) {
                //业务异常
                _error.value = msg
            }

            override fun onFail(code: Int, msg: String?) {
                //网络失败
            }
        })) {
            _success.value = mainRepository.userListRepository()
        }

    }
}