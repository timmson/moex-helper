package ru.timmson.invest;

import lombok.extern.java.Log;
import ru.timmson.invest.moex.MoexApiFactory;
import ru.timmson.invest.moex.model.ProfitableBond;
import ru.timmson.invest.tinkoff.TinkoffApi;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Log
public class App {

    private static final String MOEX_BONDS = "https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json?iss.meta=off";
    private static final String TINKOFF_TOKEN = "XXX";

    public static void main(String[] args) throws MalformedURLException, InterruptedException, ExecutionException, TimeoutException {
        final var moexApi = MoexApiFactory.createRemoteClient(MOEX_BONDS);
        final var tinkoffMarketApi = TinkoffApi.createClient(TINKOFF_TOKEN).getMarketContext();

        final var tinkoffFutureBonds = tinkoffMarketApi.getMarketBonds();
        final var allBonds = moexApi.getBonds();

        final var tinkoffBondNames = tinkoffFutureBonds.get(5, TimeUnit.SECONDS)
                .instruments
                .parallelStream()
                .map(b -> b.figi).collect(Collectors.toSet());

        log.info(tinkoffBondNames.toString());

        final var viableBonds = allBonds.parallelStream()
                .filter(b -> tinkoffBondNames.contains(b.getSecId()))
                .map(ProfitableBond::new)
                .filter(b -> b.getProfitValue() < 15)
                .sorted()
                .limit(20)
                .collect(Collectors.toList());

        viableBonds.forEach(System.out::println);

    }

}
