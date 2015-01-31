/**
 * (c) 2003-2014 Arbuz LLC, The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.arbuzworks.modules.camel.oraclecommerce;

public enum EndpointType {

	PERFORM_R_Q_L_QUERY, PERFORM_R_Q_L_COUNT_QUERY, GET_REPOSITORY_ITEM;

    public static EndpointType fromUri(String uri) {
        for (EndpointType endpointType : EndpointType.values()) {
            if (endpointType.name().equalsIgnoreCase(uri)) {
                return endpointType;
            }
        }
        return EndpointType.PERFORM_R_Q_L_QUERY;
    }
}
