package com.diandianzuan.aso.diandianzuan.net;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 自定义Retrofit请求回调
 */
public abstract class RetrofitCallBack implements Callback<String> {

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.code() == 200) {
            onSuccess(response.body());
        } else {
            try {
                onError(new Throwable(response.errorBody().string()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        onError(t);
    }

    public abstract void onSuccess(String response);

    public abstract void onError(Throwable t);

}
