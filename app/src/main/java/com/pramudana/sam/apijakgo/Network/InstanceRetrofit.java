package com.pramudana.sam.apijakgo.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sampramudana on 9/4/18.
 */

public class InstanceRetrofit {
    private static final String Weburl = "http://api.jakarta.go.id/v1/";

    private static Retrofit setInit() {
        return new Retrofit.Builder()
                .baseUrl(Weburl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static ApiService getInstance() {
        return setInit().create(ApiService.class);
    }
}
