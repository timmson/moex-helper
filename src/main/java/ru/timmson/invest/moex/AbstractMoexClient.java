package ru.timmson.invest.moex;

import lombok.extern.java.Log;
import ru.timmson.invest.moex.dto.MoexEntityFactory;
import ru.timmson.invest.moex.model.Bond;

import java.util.*;
import java.util.stream.Collectors;

@Log
public abstract class AbstractMoexClient implements MoexClient {

    protected abstract Optional<String> getSource();

    protected Optional<String> readSource(Scanner scanner) {
        StringBuilder source = new StringBuilder();
        while (scanner.hasNextLine()) {
            source.append(scanner.nextLine());
        }
        return Optional.of(source.toString());
    }

    protected List<Map<String, String>> callGetBonds() {

        final var source = getSource();

        return source.map(
                s -> MoexEntityFactory.createMoexSecurityResponse(s)
                        .parallelStream()
                        .filter(sec -> !sec.get("STATUS").equals("N") && !sec.get("PREVPRICE").equals(""))
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
                .filter(sec -> sec.get("SECID").equals(secId))
                .map(this::createBond)
                .findFirst();
    }

    private Bond createBond(Map<String, String> security) {
        return Bond.builder()
                .name(security.get("SHORTNAME"))
                .secId(security.get("SECID"))
                .sector(security.get("INSTRID"))
                .currency(security.get("FACUNIT"))
                .faceValue(security.get("FACEVALUE"))
                .currentValue(security.get("PREVPRICE"))
                .couponValue(security.get("COUPONVALUE"))
                .couponPeriod(security.get("COUPONPERIOD"))
                .couponCurrentValue(security.get("ACCRUEDINT"))
                .feeValue("0.003")
                .maturityDate(security.get("MATDATE"))
                .build();
    }


}
