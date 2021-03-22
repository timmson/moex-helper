package ru.timmson.invest.moex;

import java.net.MalformedURLException;

public class MoexApiFactory {

    public static MoexClient createLocalClient(String fileName) {
        return createLocalClient(fileName, "0", "0");
    }

    public static MoexClient createLocalClient(String fileName, String feeValue, String taxValue) {
        return new LocalMoexClient(fileName, feeValue, taxValue);
    }

    public static MoexClient createRemoteClient(String url) throws MalformedURLException {
        return createRemoteClient(url, "0", "0");
    }

    public static MoexClient createRemoteClient(String url, String feeValue, String taxValue) throws MalformedURLException {
        return new RemoteMoexClient(url, feeValue, taxValue);
    }

}
