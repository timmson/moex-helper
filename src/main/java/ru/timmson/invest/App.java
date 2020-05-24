package ru.timmson.invest;

import lombok.extern.java.Log;
import ru.timmson.invest.moex.MoexApiFactory;
import ru.timmson.invest.moex.model.ProfitableBond;
import ru.timmson.invest.tinkoff.TinkoffApi;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Log
public class App {

    private static final String MOEX_BONDS = "https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json?iss.meta=off";
    private static final String TINKOFF_TOKEN = "XXX";

    public static void main(String[] args) throws MalformedURLException, InterruptedException, ExecutionException, TimeoutException {
        //listBond("RU000A0JXQJ4");
        listProfitable();
    }

    protected static void listBond(String secId) {
        MoexApiFactory.createLocalClient("securities.json")
                .getBond(secId)
                .map(ProfitableBond::new)
                .ifPresent(b -> log.info(b.toString()));
    }

    protected static void listProfitable() throws MalformedURLException, InterruptedException, ExecutionException, TimeoutException {
        //final var moexApi = MoexApiFactory.createRemoteClient(MOEX_BONDS);
        final var moexApi = MoexApiFactory.createLocalClient("securities.json");
        final var tinkoffApi = TinkoffApi.createClient(TINKOFF_TOKEN);

        final var tinkoffFutureBonds = tinkoffApi.getMarketContext().getMarketBonds();
        final var allBonds = moexApi.getBonds();

        final var tinkoffBondNames = tinkoffFutureBonds.get(5, TimeUnit.SECONDS)
                .instruments
                .parallelStream()
                .map(b -> b.isin).collect(Collectors.toSet());

        log.info(tinkoffBondNames.toString());

        final var viableBonds = allBonds.parallelStream()
                .filter(b -> tinkoffBondNames.contains(b.getSecId()))
                .map(ProfitableBond::new)
                .filter(b -> b.getProfitValue() < 15)
                .sorted(Collections.reverseOrder())
                .limit(20)
                .collect(Collectors.toList());

        viableBonds.forEach(b -> log.info(b.toString()));

        log.info("Done");
    }

}
