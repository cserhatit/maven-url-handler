/*-
 * #%L
 * maven-url-handler
 * %%
 * Copyright (C) 2023 - 2024 i-Cell Mobilsoft Zrt.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package sun.net.www.protocol;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import org.jboss.logging.Logger;

import sun.net.www.protocol.MavenURLHandler;

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
