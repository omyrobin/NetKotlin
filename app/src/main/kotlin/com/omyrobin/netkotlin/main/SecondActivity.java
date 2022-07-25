package com.omyrobin.netkotlin.main;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.omyrobin.netkotlin.api.ApiService;
import com.omyrobin.netkotlin.base.BaseVMActivity;
import com.omyrobin.netkotlin.bean.BaseResponse;
import com.omyrobin.netkotlin.bean.UserListBean;
import com.omyrobin.netkotlin.network.calladapter.NetworkResponse;
import com.omyrobin.netkotlin.network.callback.ContinueCallback;
import com.omyrobin.netkotlin.network.callback.OmyCallback;
import com.omyrobin.network.Api;
import com.omyrobin.network.exception.IError;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import retrofit2.Call;

/**
 * @Author: omyrobin
 * @CreateDate: 2021/7/1 3:11 PM
 * @Description:
 */
public class SecondActivity extends BaseVMActivity<MainViewModel> {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            final CompletableFuture<NetworkResponse<BaseResponse<List<? extends UserListBean>>,Error>> future = new CompletableFuture<>();
        Object result = Api.create(ApiService.class).getUserListBean((Continuation<? super NetworkResponse<? extends BaseResponse<List<? extends UserListBean>>, ? extends Error>>) new Continuation<NetworkResponse<BaseResponse<List<? extends UserListBean>>, Error>>(){
//            @NotNull
//            @Override
//            public CoroutineContext getContext() {
//                return null;
//            }
//
//            @Override
//            public void resumeWith(@NotNull Object o) {
//                if (o instanceof NetworkResponse) {
//                    future.complete((NetworkResponse<BaseResponse<List<? extends UserListBean>>, Error>)o);
//                } else {
//                    future.completeExceptionally((Throwable) o);
//                }
//            }


            @NonNull
            @Override
            public CoroutineContext getContext() {
                return null;
            }

            @Override
            public void resumeWith(@NonNull Object o) {

            }
        });
        if(result != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
//            future.complete((BaseResponse<List<? extends UserListBean>>) result);
        }
        result = future.join();

    }
}
