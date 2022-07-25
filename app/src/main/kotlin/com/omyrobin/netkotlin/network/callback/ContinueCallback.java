package com.omyrobin.netkotlin.network.callback;

import com.omyrobin.netkotlin.bean.BaseResponse;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;

/**
 * @Author: omyrobin
 * @CreateDate: 2021/7/1 5:48 PM
 * @Description:
 */
public class ContinueCallback<T extends BaseResponse> implements Continuation<T> {

    OmyCallback<T> callback;

    public ContinueCallback(OmyCallback<T> callback) {
        this.callback = callback;
    }

    @NotNull
    @Override
    public CoroutineContext getContext() {
        return null;
    }

    @Override
    public void resumeWith(@NotNull Object o) {
        if(o instanceof BaseResponse) {
            callback.onSuccess((T) o);
        }else {

        }

    }
}
