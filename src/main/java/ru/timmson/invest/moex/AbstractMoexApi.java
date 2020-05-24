package ru.timmson.invest.moex;

import ru.timmson.invest.moex.dto.MoexSecurity;
import ru.timmson.invest.moex.dto.MoexSecurityFactory;

import java.util.Optional;
import java.util.Scanner;

public abstract class AbstractMoexApi implements MoexApi {

    protected abstract Optional<String> getSource();

    protected Optional<String> readSource(Scanner scanner) {
        StringBuilder source = new StringBuilder();
        while (scanner.hasNextLine()) {
            source.append(scanner.nextLine());
        }
        return Optional.of(source.toString());
    }

    @Override
    public Optional<MoexSecurity> getBonds() {
        final var source = getSource();
        if (source.isPresent()) {
            final var moexSecurityResponse = MoexSecurityFactory.createMoexSecurityResponse(source.get());
            if (moexSecurityResponse.isPresent()) {
                return Optional.of(moexSecurityResponse.get().getSecurities());
            }
        }
        return Optional.empty();
    }
}
