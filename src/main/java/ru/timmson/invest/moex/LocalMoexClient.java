package ru.timmson.invest.moex;

import lombok.extern.java.Log;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;

@Log
class LocalMoexClient extends AbstractMoexClient {

    private final String fileName;

    public LocalMoexClient(String fileName, String feeValue, String taxValue) {
        super(feeValue, taxValue);
        this.fileName = fileName;
    }

    @Override
    protected Optional<String> getSource() {
        try (final var scanner = new Scanner(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(this.fileName)))) {

            return readSource(scanner);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error", e);
        }

        return Optional.empty();
    }

}
