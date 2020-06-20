package ru.timmson.invest.moex.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoexEntityFactoryShould {

    @Test
    void createMoexSecurityResponseFromJSON() {
        final var src = "{\n" +
                "\"securities\": {\n" +
                "\t\"columns\": [\"SECID\", \"BOARDID\", \"SHORTNAME\", \"PREVWAPRICE\", \"YIELDATPREVWAPRICE\", \"COUPONVALUE\", \"NEXTCOUPON\", \"ACCRUEDINT\", \"PREVPRICE\", \"LOTSIZE\", \"FACEVALUE\", \"BOARDNAME\", \"STATUS\", \"MATDATE\", \"DECIMALS\", \"COUPONPERIOD\", \"ISSUESIZE\", \"PREVLEGALCLOSEPRICE\", \"PREVADMITTEDQUOTE\", \"PREVDATE\", \"SECNAME\", \"REMARKS\", \"MARKETCODE\", \"INSTRID\", \"SECTORID\", \"MINSTEP\", \"FACEUNIT\", \"BUYBACKPRICE\", \"BUYBACKDATE\", \"ISIN\", \"LATNAME\", \"REGNUMBER\", \"CURRENCYID\", \"ISSUESIZEPLACED\", \"LISTLEVEL\", \"SECTYPE\", \"COUPONPERCENT\", \"OFFERDATE\", \"SETTLEDATE\", \"LOTVALUE\"], \n" +
                "\t\"data\": [\n" +
                "\t\t[\"XS0524610812\", \"TQOD\", \"VEB-20\", 100.306, 1.61, 34.51, \"2020-07-09\", 31.4424, 100.315, 1, 1000, \"Т+: Облигации (USD) - безадрес.\", \"A\", \"2020-07-09\", 4, 182, 1600000, 100.3137, 100.3137, \"2020-06-18\", \"VEB FINANCE LIMITED 6.902 09\\/0\", null, \"FNDT\", \"EIUS\", \"EU-N\", 0.0001, \"USD\", null, \"0000-00-00\", \"XS0524610812\", \"VEB FINANCE LIMITED 6.902 09\\/0\", null, \"USD\", null, 3, \"6\", 6.902, null, \"2020-06-23\", 1000]\n" +
                "\t]\n" +
                "}}";

        final var result = MoexEntityFactory.createMoexSecurityResponse(src);

        assertEquals("TQOD", result.get(0).get("BOARDID"));
    }
}