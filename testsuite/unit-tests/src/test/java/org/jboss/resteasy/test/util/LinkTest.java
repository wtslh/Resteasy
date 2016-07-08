package org.jboss.resteasy.test.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.test.util.resource.LinkResource;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilderException;
import java.lang.reflect.Method;
import java.net.URI;



/**
 * @tpSubChapter Util tests
 * @tpChapter Unit tests
 * @tpTestCaseDetails Test for javax.ws.rs.core.Link class.
 * @tpSince EAP 7.0.0
 */
public class LinkTest {

    protected static final Logger logger = LogManager.getLogger(LinkTest.class.getName());

    /**
     * @tpTestDetails Check relative path option
     * @tpSince EAP 7.0.0
     */
    @Test
    public void testRelativized() throws Exception {
        URI uri = new URI("a").relativize(new URI("a/d/e"));
        logger.info(uri);

        Link link = Link.fromUri("a/d/e")
                .rel("update").type("text/plain")
                .buildRelativized(new URI("a"));
        logger.info(link.toString());

        link = Link.fromUri("a/d/e")
                .rel("update").type("text/plain")
                .baseUri("http://localhost/")
                .buildRelativized(new URI("http://localhost/a"));
        logger.info(link.toString());

    }

    /**
     * @tpTestDetails Check fromMethod method.
     * @tpSince EAP 7.0.0
     */
    @Test
    public void testFromMethod() {
        for (Method m : LinkResource.class.getDeclaredMethods()) {
            Link link = Link.fromMethod(LinkResource.class, m.getName()).build();
            String string = link.toString();
            logger.info("initial: " + string);
            Link fromValueOf = Link.valueOf(string);
            Assert.assertEquals(string, fromValueOf.toString());
        }
    }

    /**
     * @tpTestDetails Check wrong URI
     * @tpSince EAP 7.0.0
     */
    @Test
    public void testIllegalUri() {
        Link.Builder builder = Link.fromUri("http://:@");
        try {
            builder.build();
            Assert.fail();
        } catch (UriBuilderException e) {
        }
    }

}