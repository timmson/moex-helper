package ru.timmson.invest.moex;

import lombok.extern.java.Log;
import ru.timmson.invest.moex.dto.MoexEntityFactory;
import ru.timmson.invest.moex.model.Bond;
import ru.timmson.invest.moex.model.Security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Log
public abstract class AbstractMoexClient implements MoexClient {

    private final String feeValue;
    private final String taxValue;

    public AbstractMoexClient(String feeValue, String taxValue) {
        this.feeValue = feeValue;
        this.taxValue = taxValue;
    }

    protected abstract Optional<String> getSource();

    protected Optional<String> readSource(Scanner scanner) {
        StringBuilder source = new StringBuilder();
        while (scanner.hasNextLine()) {
            source.append(scanner.nextLine());
        }
        return Optional.of(source.toString());
    }

    protected List<Security> callGetBonds() {

        final var source = getSource();

        return source.map(
                src -> MoexEntityFactory.createMoexSecurityResponse(src)
                        .parallelStream()
                        .filter(s -> !s.getInfo().get("STATUS").equals("N") && s.getMarketData().get("LAST") != null)
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
                .filter(s -> s.getInfo().get("SECID").equals(secId))
                .map(this::createBond)
                .findFirst();
    }

    private Bond createBond(Security s) {
        return Bond.builder()
                .name(s.getInfo().get("SHORTNAME"))
                .secId(s.getInfo().get("SECID"))
                .sector(s.getInfo().get("INSTRID"))
                .currency(s.getInfo().get("FACEUNIT"))
                .faceValue(s.getInfo().get("FACEVALUE"))
                .currentValue(s.getMarketData().get("LAST"), s.getInfo().get("PREVPRICE"))
                .couponValue(s.getInfo().get("COUPONVALUE"))
                .couponPeriod(s.getInfo().get("COUPONPERIOD"))
                .couponCurrentValue(s.getInfo().get("ACCRUEDINT"))
                .feeValue(feeValue)
                .taxValue(taxValue)
                .maturityDate(s.getInfo().get("MATDATE"))
                .build();
    }


}
