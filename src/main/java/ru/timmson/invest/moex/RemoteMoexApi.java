package ru.timmson.invest.moex;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;

public class RemoteMoexApi extends AbstractMoexApi {

    private final URL url;

    public RemoteMoexApi(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    protected Optional<String> getSource() {
        try (final var is = url.openStream();
             final var isr = new InputStreamReader(is);
             final var scanner = new Scanner(isr)) {

            return readSource(scanner);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
