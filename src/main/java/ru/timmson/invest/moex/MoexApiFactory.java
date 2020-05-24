package ru.timmson.invest.moex;

import java.net.MalformedURLException;

public class MoexApiFactory {

    public static MoexClient createLocalClient(String fileName) {
        return new LocalMoexClient(fileName);
    }

    public static MoexClient createRemoteClient(String url) throws MalformedURLException {
        return new RemoteMoexClient(url);
    }

}
