package ru.timmson.invest.moex;

import ru.timmson.invest.moex.dto.MoexSecurity;

import java.util.Optional;

public interface MoexApi {

    Optional<MoexSecurity> getBonds();

}
