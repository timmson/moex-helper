package ru.timmson.invest;

import org.jetbrains.annotations.NotNull;
import ru.timmson.invest.moex.MoexApiFactory;
import ru.timmson.invest.moex.model.ProfitableBond;
import ru.timmson.invest.tinkoff.TinkoffApi;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class App {

    private static final String MOEX_BONDS = "https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json?iss.meta=off&iss.only=securities";
    private static final String TINKOFF_TOKEN = "XXX";

    public static void main(String[] args) throws Exception {
        //listBond("SU26205RMFS3");
        listProfitable();
    }

    protected static void listBond(String secId) throws Exception {
        MoexApiFactory.createRemoteClient(MOEX_BONDS).getBond(secId).map(ProfitableBond::new).ifPresent(System.out::println);
    }

    @NotNull
    private static boolean filter(ProfitableBond b) {
        return b.getProfitValue() > 5
                /*                && b.getMaturityDate().isBefore(LocalDate.of(2024,1,1))*/
                && b.getSector().equals("GOFZ");
    }

    protected static void listProfitable() throws Exception {
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
                .filter(App::filter)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        viableBonds.forEach(b -> System.out.println("https://www.tinkoff.ru/invest/bonds/" + b.getSecId() + "/ " + b.getName() + " " + b.getProfitValue()));

        tinkoffApi.close();

        System.out.println("Done");
    }

}
