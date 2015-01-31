/**
 * (c) 2003-2014 Arbuz LLC, The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.arbuzworks.modules.camel.oraclecommerce;


import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriPath;
import org.arbuzworks.modules.mule.oraclecommerce.client.OracleCommerceProxyClient;

import javax.naming.directory.InvalidAttributesException;

/**
 * Represents a OracleCommerce endpoint.
 */
@UriEndpoint(scheme = "oraclecommerce", consumerClass = OracleCommerceConsumer.class)
public class OracleCommerceEndpoint extends DefaultEndpoint {
	@UriPath
	private String host;
	@UriPath
	private Integer port;
	@UriPath
	private String type;

	@UriPath
	private String repositoryComponentPath;
	@UriPath
	private String itemDescriptorName;
	@UriPath
	private String RQLString;

	@UriPath
	private String repositoryItemId;

	private OracleCommerceProxyClient client;

	public OracleCommerceEndpoint(String uri, OracleCommerceComponent component) {
		super(uri, component);
	}


	public Producer createProducer() throws Exception {
		throw new UnsupportedOperationException(
				"You cannot send messages to this endpoint:" + getEndpointUri());
	}

	public Consumer createConsumer(Processor processor) throws Exception {
		validateInputs();

		if (client == null) {
			client = new OracleCommerceProxyClient();
			client.initialize(host, String.valueOf(port));
		}
		return new OracleCommerceConsumer(this, processor);
	}

	private void validateInputs() throws InvalidAttributesException {
		if (host == null || host.length() == 0) {
			throw new InvalidAttributesException("A required parameter was not set when creating this Endpoint (host)");
		}
		if (port == 0) {
			throw new InvalidAttributesException("A required parameter was not set when creating this Endpoint (port)");
		}
		if (repositoryComponentPath == null || repositoryComponentPath.length() == 0) {
			throw new InvalidAttributesException("A required parameter was not set when creating this Endpoint (repositoryComponentPath)");
		}
		if (itemDescriptorName == null || itemDescriptorName.length() == 0) {
			throw new InvalidAttributesException("A required parameter was not set when creating this Endpoint (itemDescriptorName)");
		}
		if (type == null || type.length() == 0) {
			throw new InvalidAttributesException("A required path was not set when creating this Endpoint");
		} else {
			EndpointType endpointType = EndpointType.fromUri(type);
			if (endpointType == EndpointType.GET_REPOSITORY_ITEM) {
				if (repositoryItemId == null || repositoryItemId.length() == 0) {
					throw new InvalidAttributesException("A required parameter was not set when creating this Endpoint (repositoryItemId)");
				}
			} else if (RQLString == null || RQLString.length() == 0) {
				throw new InvalidAttributesException("A required parameter was not set when creating this Endpoint (RQLString)");

			}
		}
	}

	public boolean isSingleton() {
		return true;
	}

	public String getRepositoryComponentPath() {
		return repositoryComponentPath;
	}

	public void setRepositoryComponentPath(String repositoryComponentPath) {
		this.repositoryComponentPath = repositoryComponentPath;
	}

	public String getItemDescriptorName() {
		return itemDescriptorName;
	}

	public void setItemDescriptorName(String itemDescriptorName) {
		this.itemDescriptorName = itemDescriptorName;
	}

	public String getRQLString() {
		return RQLString;
	}

	public void setRQLString(String RQLString) {
		this.RQLString = RQLString;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRepositoryItemId() {
		return repositoryItemId;
	}

	public void setRepositoryItemId(String repositoryItemId) {
		this.repositoryItemId = repositoryItemId;
	}


	public OracleCommerceProxyClient getClient() {
		return client;
	}
}
