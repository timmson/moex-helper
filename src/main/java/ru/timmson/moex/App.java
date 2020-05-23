package ru.timmson.moex;

import com.google.gson.GsonBuilder;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class App {

    /**
     * wget -N https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json
     * <p>
     * wget -N https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json?iss.meta=off
     */

    public static void main(String[] args) throws FileNotFoundException {
        final var security = new GsonBuilder().create().fromJson(new FileReader("securities.json"), Root.class);
        System.out.println("security = " + security);

    }

    @Data
    private static class Root {
        private Securities securities;
    }

    @Data
    private static class Securities {
        private List<String> columns;
        private List<List<String>> data;
    }

}
