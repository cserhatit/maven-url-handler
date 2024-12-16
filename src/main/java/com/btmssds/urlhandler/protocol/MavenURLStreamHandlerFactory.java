package com.btmssds.urlhandler.protocol;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import org.jboss.logging.Logger;

import com.btmssds.urlhandler.protocol.MavenURLHandler;

/**
 * "maven" URL protocol olvasashoz szolgalo Provider. Regisztralni szukseges a
 * <code>src/main/resources/META-INF/services/java.net.spi.URLStreamHandlerProvider</code> fajlban
 *
 * @author tamas.cserhati
 * @author imre.scheffer
 * @since 1.0.0
 */
public class MavenURLStreamHandlerFactory implements URLStreamHandlerFactory {

    private static final Logger LOGGER = Logger.getLogger(MavenURLStreamHandlerFactory.class);
    private static final String PREFIX = "sun.net.www.protocol";
    private static final URLStreamHandler MAVEN_URL_HANDLER = new MavenURLHandler();

    /**
     * Default constructor, constructs a new object.
     */
    public MavenURLStreamHandlerFactory() {
        super();
        LOGGER.info("MavenURLStreamHandlerFactory initialized");
    }

    /** {@inheritDoc} */
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        LOGGER.info("MavenURLStreamHandlerProvider registered");
        return "maven".equals(protocol) ? MAVEN_URL_HANDLER : createDefaultURLStreamHandler(protocol);
    }

    /**
     * Copy of {@code URL$DefaultFactory} class
     * 
     * @param protocol
     *            the protocol
     * 
     * @return default {@code URLStreamHandler} or null in case of any exception
     *
     */
    private URLStreamHandler createDefaultURLStreamHandler(String protocol) {
        String name = PREFIX + "." + protocol + ".Handler";
        try {
            Object o = Class.forName(name).getDeclaredConstructor().newInstance();
            return (URLStreamHandler) o;
        } catch (Exception e) {
            LOGGER.warn("createDefaultURLStreamHandler was throwing an exception: " + e.getLocalizedMessage());
            // For compatibility, all Exceptions are ignored.
            // any number of exceptions can get thrown here
        }
        return null;
    }

}
