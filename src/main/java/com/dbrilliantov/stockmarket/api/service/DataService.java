package com.dbrilliantov.stockmarket.api.service;

import com.dbrilliantov.stockmarket.api.domain.Result;
import com.dbrilliantov.stockmarket.api.interfaces.IData;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class DataService {

    private static final String API_BASE_URL = "https://cloud.iexapis.com/stable/";
    private static final String API_KEY = "pk_da10d338918f489ba17f78944f190ecf";

    private final IData data;

    public DataService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        data = retrofit.create(IData.class);
    }

    public Result getStock(String stock) {
        try {
            Response<Result> response = data.getStock(stock, API_KEY).execute();

            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}