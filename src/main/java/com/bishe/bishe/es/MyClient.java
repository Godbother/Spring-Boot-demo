package com.bishe.bishe.es;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class MyClient {
    //严格单例模式
    private static JestClient jestClient;
    public static JestClient getClient()throws Exception{
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://192.168.199.130:9200")
                .multiThreaded(true).readTimeout(20000)
                .build());
        if (jestClient==null) {
            jestClient = factory.getObject();
        }
        return jestClient;
    }


}
