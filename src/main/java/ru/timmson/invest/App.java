package ru.timmson.invest;

import org.jetbrains.annotations.NotNull;
import ru.timmson.invest.moex.MoexApiFactory;
import ru.timmson.invest.moex.MoexClient;
import ru.timmson.invest.moex.model.Bond;
import ru.timmson.invest.moex.model.ProfitableBond;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        //listBond("RU000A0JUQB7");
        listBonds();
    }

    protected static void listBond(String secId) throws Exception {
        getRemoteClient(String.format("https://iss.moex.com/iss/engines/stock/markets/bonds/securities/%s.json?iss.meta=off", secId)).getBond(secId).map(ProfitableBond::new).ifPresent(System.out::println);
    }

    protected static void listBonds() throws Exception {
        getRemoteClient("https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json?iss.meta=off").getBonds().parallelStream()
                .filter(App::filter)
                .map(ProfitableBond::new)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    @NotNull
    private static MoexClient getRemoteClient(String url) throws MalformedURLException {
        return MoexApiFactory.createRemoteClient(url, "0.003", "0.13");
    }

    private static boolean filter(Bond b) {
        return b.getMaturityDate().isAfter(LocalDate.now().plusMonths(1)) && b.getMaturityDate().isBefore(LocalDate.now().plusYears(2)) && b.getCurrency().equals("SUR");
    }

}
