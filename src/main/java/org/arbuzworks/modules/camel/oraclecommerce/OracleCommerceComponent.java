/**
 * (c) 2003-2014 Arbuz LLC, The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.arbuzworks.modules.camel.oraclecommerce;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

import java.net.URI;
import java.util.Map;

/**
 * OracleCommerce component.
 */
public class OracleCommerceComponent extends UriEndpointComponent {

	public OracleCommerceComponent() {
		super(OracleCommerceEndpoint.class);
	}

	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters)
			throws Exception {

		String baseUri = getBaseUri(uri);
		URI newUri = new URI(baseUri);
		String host = newUri.getHost();
		int port = newUri.getPort();
		String type = newUri.getPath().replaceFirst("[/]", "");

		parameters.put("host", host);
		parameters.put("port", port);
		parameters.put("type", type);
		Endpoint endpoint = new OracleCommerceEndpoint(uri, this);
		setProperties(endpoint, parameters);

		return endpoint;
	}

	/**
	 * Get the base uri part before the options as they can be non URI valid such as the expression using $ chars
	 * and the URI constructor will regard $ as an illegal character and we don't want to enforce end users to
	 * to escape the $ for the expression (file language)
	 */
	protected String getBaseUri(String uri) {
		String baseUri = uri;
		if (uri.contains("?")) {
			baseUri = uri.substring(0, uri.indexOf("?"));
		}
		return baseUri;
	}

}
