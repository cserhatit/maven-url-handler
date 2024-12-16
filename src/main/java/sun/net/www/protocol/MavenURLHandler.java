/*-
 * #%L
 * Coffee
 * %%
 * Copyright (C) 2020 i-Cell Mobilsoft Zrt.
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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.jboss.logging.Logger;

/**
 * Handling URL protocol for this format:
 *
 * <pre>
 * maven:hu.icellmobilsoft.coffee:coffee-dto-xsd:jar::!/xsd/hu/icellmobilsoft/coffee/dto/common/common.xsd
 * </pre>
 *
 * Format is: <code>maven:groupId:atifactId:package:version:!file_path</code>
 * <ul>
 * <li>protocol - URL schema protocol, in this case "maven"</li>
 * <li>hu.icellmobilsoft.coffee.dto.xsd - maven groupId</li>
 * <li>coffee-dto-xsd - maven artifactId</li>
 * <li>jar - maven package</li>
 * <li>maven version</li>
 * </ul>
 *
 * @author tamas.cserhati
 * @author imre.scheffer
 * @since 1.0.0
 */
public class MavenURLHandler extends URLStreamHandler {

    private static final Logger LOGGER = Logger.getLogger(MavenURLHandler.class);
    
    private static final String SEPARATOR = "!";
    private static final String DIR_SEPARATOR = "/";

    /**
     * Default constructor, constructs a new object.
     */
    public MavenURLHandler() {
        super();
        LOGGER.info("MavenURLHandler initialized");
    }

    /** {@inheritDoc} */
    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        if( url == null ) {
            LOGGER.error("[MavenURLHandler] URL is null!");
            return null;
        }
        String path = url.getPath();
        // We cut until the first "!" and then look for the remainder in the classpath.
        if (path.contains(SEPARATOR)) {
            path = path.substring(path.indexOf(SEPARATOR) + SEPARATOR.length());
        }
        // Some runtime packaging (like Quarkus) may not be able to find the resource in the classpath.
        // Quarkus dev mode vs package works differently.
        // In dev mode, the resource is found in the classpath, but in the package needs to be searched for in the relative path.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL classPathUrl = classLoader.getResource(path);
        if (classPathUrl == null && path.startsWith(DIR_SEPARATOR)) {
            // If it cannot be found in the classpath, try to search for it using the relative path
            int index = path.indexOf(DIR_SEPARATOR);
            String result = (index != -1) ? path.substring(index + DIR_SEPARATOR.length()) : "";
            classPathUrl = classLoader.getResource(result);
        }

        // Later on, you can simply search within the actual class.
        return classPathUrl == null ? null : classPathUrl.openConnection();
    }
}
