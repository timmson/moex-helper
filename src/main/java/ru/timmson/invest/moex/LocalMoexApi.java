package ru.timmson.invest.moex;

import ru.timmson.invest.moex.dto.MoexSecurity;
import ru.timmson.invest.moex.dto.MoexSecurityFactory;

import java.io.FileReader;
import java.util.Optional;
import java.util.Scanner;

class LocalMoexApi extends AbstractMoexApi {

    private final String fileName;

    public LocalMoexApi(String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected Optional<String> getSource() {
        try (final var fileReader = new FileReader(this.fileName);
             final var scanner = new Scanner(fileReader)) {

            return readSource(scanner);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<MoexSecurity> getBonds() {
        Optional<MoexSecurity> result = Optional.empty();

        try (final var fileReader = new FileReader(this.fileName); final var scanner = new Scanner(fileReader)) {
            StringBuilder source = new StringBuilder();
            while (scanner.hasNextLine()) {
                source.append(scanner.nextLine());
            }
            final var moexSecurityResponse = MoexSecurityFactory.createMoexSecurityResponse(source.toString());
            if (moexSecurityResponse.isPresent()) {
                result = Optional.of(moexSecurityResponse.get().getSecurities());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
