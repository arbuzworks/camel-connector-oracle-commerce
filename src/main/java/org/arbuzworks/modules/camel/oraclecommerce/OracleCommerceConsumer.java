/**
 * (c) 2003-2014 Arbuz LLC, The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.arbuzworks.modules.camel.oraclecommerce;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.arbuzworks.modules.mule.oraclecommerce.client.OracleCommerceClientException;

/**
 * The OracleCommerce consumer.
 */
public class OracleCommerceConsumer extends DefaultConsumer {
	private final OracleCommerceEndpoint endpoint;

	public OracleCommerceConsumer(OracleCommerceEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = endpoint;
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		Exchange exchange = getEndpoint().createExchange();
		Message msg = exchange.getOut();
		Object result = null;
		try {
			switch (EndpointType.fromUri(endpoint.getType())) {
				case PERFORM_R_Q_L_QUERY:
					result = endpoint.getClient().performRQLQuery(
							endpoint.getRepositoryComponentPath(), endpoint.getItemDescriptorName(), endpoint.getRQLString());
					break;
				case PERFORM_R_Q_L_COUNT_QUERY:
					result = endpoint.getClient().performRQLCountQuery(
							endpoint.getRepositoryComponentPath(), endpoint.getItemDescriptorName(), endpoint.getRQLString());
					break;
				case GET_REPOSITORY_ITEM:
					result = endpoint.getClient().getRepositoryItem(
							endpoint.getRepositoryComponentPath(), endpoint.getItemDescriptorName(), endpoint.getRepositoryItemId());

					break;
			}
		} catch (OracleCommerceClientException e1) {
			getExceptionHandler().handleException("Error processing exchange", exchange, e1);
		}
		if (result != null) {
			msg.setBody(result);
			exchange.setOut(msg);
			getProcessor().process(exchange);
		}
	}

}
