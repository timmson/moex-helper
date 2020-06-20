package ru.timmson.invest.moex;

import java.util.List;
import java.util.Map;

public interface MoexApi {

    List<Map<String, String>> getBonds();

}
