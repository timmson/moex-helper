package ru.timmson.invest.moex;

import lombok.extern.java.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;

@Log
public class RemoteMoexClient extends AbstractMoexClient {

    private final URL url;

    public RemoteMoexClient(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    protected Optional<String> getSource() {
        try (final var scanner = new Scanner(url.openStream())) {

            return readSource(scanner);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e);
        }

        return Optional.empty();
    }

}
