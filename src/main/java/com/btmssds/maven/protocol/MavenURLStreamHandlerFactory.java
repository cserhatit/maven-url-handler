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
package com.btmssds.maven.protocol;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Provider for accessing 'maven' URL protocol. Need registered in the
 * <code>src/main/resources/META-INF/services/java.net.URLStreamHandlerFactory</code> file
 *
 * @author tamas.cserhati
 * @author imre.scheffer
 * @since 1.0.0
 */
public class MavenURLStreamHandlerFactory implements URLStreamHandlerFactory {

    private static Map<String, URLStreamHandler> handlerMap = new HashMap<>(1);

    static {
        handlerMap.put("maven", new MavenURLHandler());
    }

    /** {@inheritDoc} */
    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return handlerMap.get(protocol);
    }

}
