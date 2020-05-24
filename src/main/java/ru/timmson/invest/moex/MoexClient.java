package ru.timmson.invest.moex;

import ru.timmson.invest.moex.model.Bond;

import java.util.List;
import java.util.Optional;

public interface MoexClient {

    List<Bond> getBonds();

    Optional<Bond> getBond(String secId);

}
