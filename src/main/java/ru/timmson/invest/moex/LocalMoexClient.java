package ru.timmson.invest.moex;

import lombok.extern.java.Log;

import java.io.FileReader;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;

@Log
class LocalMoexClient extends AbstractMoexClient {

    private final String fileName;

    public LocalMoexClient(String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected Optional<String> getSource() {
        try (final var fileReader = new FileReader(this.fileName);
             final var scanner = new Scanner(fileReader)) {

            return readSource(scanner);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e);
        }

        return Optional.empty();
    }

}
