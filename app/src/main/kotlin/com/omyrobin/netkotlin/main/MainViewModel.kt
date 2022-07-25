package com.omyrobin.netkotlin.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.omyrobin.netkotlin.api.ApiService
import com.omyrobin.netkotlin.base.BaseViewModel
import com.omyrobin.netkotlin.bean.BaseResponse
import com.omyrobin.netkotlin.bean.UserListBean
import com.omyrobin.netkotlin.network.calladapter.NetworkResponse
import com.omyrobin.netkotlin.network.callback.OmyCallback
import com.omyrobin.netkotlin.network.transform.NetData
import com.omyrobin.network.Api
import com.omyrobin.network.exception.HandlerException
import com.omyrobin.network.exception.IError
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call

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

        viewModelScope.launch() {
            val state = mainRepository.userListRepository()

            when (state) {
                is NetworkResponse.ApiError -> {HandlerException().handleException(state.body)}
                is NetworkResponse.NetworkError -> {}
                is NetworkResponse.Success -> success.value = NetData.transform(state.body)
                is NetworkResponse.UnknownError -> {}
            }
        }

//        //需要处理业务异常
//        viewModelScope.launch(HandlerException(object : IError {
//            override fun onError(code: Int, msg: String?) {
//                //业务异常
//                _error.value = msg
//            }
//
//            override fun onFail(code: Int, msg: String?) {
//                //网络失败
//            }
//        })) {
//            _success.value = mainRepository.userListRepository()
//        }
    }

    var call: Call<BaseResponse<List<UserListBean>>>? = null

    fun getUserListCallback() {
        call = Api.create(ApiService::class.java).getUserListBean2()

        call?.enqueue(object : OmyCallback<BaseResponse<List<UserListBean>>>(object : IError {
                override fun onError(code: Int, msg: String?) {
                    //业务异常
                    _error.value = msg
                }

                override fun onFail(code: Int, msg: String?) {
                    //网络失败
                }
            }) {
                override fun onSuccess(t: BaseResponse<List<UserListBean>>?) {
                    _success.value = t?.data
                }
            })


    }

    override fun onCleared() {
        super.onCleared()
        call?.cancel()
    }
}