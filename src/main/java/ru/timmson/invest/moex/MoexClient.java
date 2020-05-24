package ru.timmson.invest.moex;

import ru.timmson.invest.moex.model.Bond;

import java.util.List;

public interface MoexClient {

    List<Bond> getBonds();

}
