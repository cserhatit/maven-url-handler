package com.btmssds.urlhandler.protocol;

import java.net.URLStreamHandler;
import java.net.spi.URLStreamHandlerProvider;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tamas.cserhati
 * @since 1.0.0
 */
public class MavenUrlStreamHandlerProvider extends URLStreamHandlerProvider {

    private static Map<String, URLStreamHandler> handlerMap = new HashMap<>(1);

    static {
        handlerMap.put("maven", new MavenURLHandler());
    }

    @Override
    public URLStreamHandler createURLStreamHandler(final String protocol) {
        return handlerMap.get(protocol);
    }

}
