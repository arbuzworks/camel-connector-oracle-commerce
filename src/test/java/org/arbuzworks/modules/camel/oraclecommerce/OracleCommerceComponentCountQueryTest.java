/**
 * (c) 2003-2014 Arbuz LLC, The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.arbuzworks.modules.camel.oraclecommerce;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OracleCommerceComponentCountQueryTest extends CamelTestSupport {

    @Test
    public void testTimerInvokesBeanMethod() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);

		List<Exchange> exchanges = mock.getExchanges();
		for (Exchange e : exchanges) {
			Assert.assertTrue(e.getOut().getBody() instanceof Integer);
			Integer res = (Integer)e.getOut().getBody();
			Assert.assertTrue(res != 0);
		}

		assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("oraclecommerce://73.47.102.249:8080/perform_r_q_l_count_query?" +
						"repositoryComponentPath=/atg/userprofiling/ProfileAdapterRepository&" +
						"itemDescriptorName=user&" +
						"RQLString=id=1")
                  .to("mock:result");

            }
        };
    }
}
