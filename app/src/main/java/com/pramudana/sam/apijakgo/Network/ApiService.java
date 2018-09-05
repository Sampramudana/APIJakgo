package com.pramudana.sam.apijakgo.Network;

import com.pramudana.sam.apijakgo.Model.ResponseJakgo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by sampramudana on 9/4/18.
 */

public interface ApiService {

    @GET("emergency/petugaspemadam")
    Call<ResponseJakgo> readJakgoApi(
        @Header("Authorization") String authorization
    );
}
