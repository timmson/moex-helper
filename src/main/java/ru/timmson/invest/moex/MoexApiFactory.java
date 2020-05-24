package ru.timmson.invest.moex;

import java.net.MalformedURLException;

public class MoexApiFactory {

    public static MoexApi createLocalMoexApi(String fileName) {
        return new LocalMoexApi(fileName);
    }

    public static MoexApi createRemoteMoexApi(String url) throws MalformedURLException {
        return new RemoteMoexApi(url);
    }

}
