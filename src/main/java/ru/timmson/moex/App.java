package ru.timmson.moex;

import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApiFactory;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class App {

    /**
     * wget -N https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json
     * <p>
     * wget -N https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json?iss.meta=off
     */

    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {
        final var token = "XXX";
        final var client = new OkHttpOpenApiFactory(token, Logger.getLogger("bonds")).createOpenApiClient(Executors.newSingleThreadExecutor());
        final var marketBonds = client.getMarketContext().getMarketBonds().get();
        System.out.println("marketBonds = " + marketBonds);
/*        final var securities = new GsonBuilder().create().fromJson(new FileReader("securities.json"), Root.class).getSecurities();
        final var sec1 = securities.getData()
                .parallelStream()
                //.filter(row -> row.get(0).equals("SU29006RMFS2") && row.get(8) != null)
                .filter(row -> !row.get(12).equals("N"))
                .map(App::createBond)
                .filter(b -> b.getProfitValue() < 15)
                .sorted(Comparator.comparing(Bond::getProfitValue).reversed())
                .limit(20)
                .collect(Collectors.toList());
        sec1.forEach(System.out::println);*/
    }

    private static Bond createBond(List<String> row) {
        return Bond.builder()
                .name(row.get(2))
                .secId(row.get(0))
                .faceValue(row.get(10))
                .currentValue(row.get(8))
                .couponValue(row.get(5))
                .couponPeriod(row.get(15))
                .couponCurrentValue(row.get(7))
                .maturityDate(row.get(13))
                .build();
    }


}
