package ru.timmson.invest;

import ru.timmson.invest.moex.MoexApiFactory;
import ru.timmson.invest.moex.model.ProfitableBond;
import ru.timmson.invest.tinkoff.TinkoffApi;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class App {

    private static final String MOEX_BONDS = "https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json?iss.meta=off";
    private static final String TINKOFF_TOKEN = "XXX";

    public static void main(String[] args) throws Exception {
        //listBond("XS0524610812");
        listProfitable();
    }

    protected static void listBond(String secId) throws Exception {
        MoexApiFactory.createRemoteClient(MOEX_BONDS).getBond(secId).map(ProfitableBond::new).ifPresent(System.out::println);
    }

    protected static void listProfitable() throws MalformedURLException, InterruptedException, ExecutionException, TimeoutException {
        final var moexApi = MoexApiFactory.createRemoteClient(MOEX_BONDS);
        final var tinkoffApi = TinkoffApi.createClient(TINKOFF_TOKEN);

        final var tinkoffFutureBonds = tinkoffApi.getMarketContext().getMarketBonds();
        final var allBonds = moexApi.getBonds();

        final var tinkoffBondNames = tinkoffFutureBonds.get(5, TimeUnit.SECONDS)
                .instruments
                .parallelStream()
                .map(b -> b.isin).collect(Collectors.toSet());

        final var viableBonds = allBonds.parallelStream()
                .filter(b -> tinkoffBondNames.contains(b.getSecId()))
                .map(ProfitableBond::new)
                .filter(b -> b.getProfitValue() > 3 && b.getCurrency().equals("USD"))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        viableBonds.forEach(b -> System.out.println("https://www.tinkoff.ru/invest/bonds/" + b.getSecId() + "/ " + b.getName() + " " + b.getProfitValue()));

        System.out.println("Done");
    }

}
