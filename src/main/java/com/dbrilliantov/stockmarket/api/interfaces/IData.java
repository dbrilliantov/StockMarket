package com.dbrilliantov.stockmarket.api.interfaces;

import com.dbrilliantov.stockmarket.api.domain.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IData {

    @GET("stock/{stock}/quote")
    Call<Result> getStock(@Path("stock") String stock, @Query("token") String token);

}