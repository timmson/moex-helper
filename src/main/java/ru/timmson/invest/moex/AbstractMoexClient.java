package ru.timmson.invest.moex;

import ru.timmson.invest.moex.dto.MoexEntityFactory;
import ru.timmson.invest.moex.dto.MoexSecurity;
import ru.timmson.invest.moex.dto.MoexSecurityResponse;
import ru.timmson.invest.moex.model.Bond;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public abstract class AbstractMoexClient implements MoexClient {

    protected abstract Optional<String> getSource();

    protected Optional<String> readSource(Scanner scanner) {
        StringBuilder source = new StringBuilder();
        while (scanner.hasNextLine()) {
            source.append(scanner.nextLine());
        }
        return Optional.of(source.toString());
    }

    protected List<List<String>> callGetBonds() {

        final var source = getSource();

        return source.map(
                s -> MoexEntityFactory.createMoexSecurityResponse(s)
                        .orElse(new MoexSecurityResponse(new MoexSecurity(emptyList(), emptyList())))
                        .getSecurities()
                        .getData()
                        .parallelStream()
                        .filter(row -> !row.get(12).equals("N") && row.get(8) != null)
                        .collect(Collectors.toList())
        ).orElse(Collections.emptyList());

    }

    @Override
    public List<Bond> getBonds() {
        return callGetBonds()
                .parallelStream()
                .map(this::createBond)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Bond> getBond(String secId) {
        return callGetBonds()
                .parallelStream()
                .filter(row -> row.get(0).equals(secId))
                .map(this::createBond)
                .findFirst();
    }

    private Bond createBond(List<String> row) {
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
