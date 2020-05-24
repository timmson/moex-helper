package ru.timmson.invest.tinkoff;

import lombok.extern.java.Log;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApiFactory;

import java.util.concurrent.Executors;

@Log
public class TinkoffApi {

    public static OpenApi createClient(String token) {
        return new OkHttpOpenApiFactory(token, log).createOpenApiClient(Executors.newSingleThreadExecutor());
    }

}
