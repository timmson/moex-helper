package ru.timmson.invest.moex.dto;

import lombok.Data;

@Data
public class MoexSecurityResponse {

    private final MoexSecurity securities;
}
