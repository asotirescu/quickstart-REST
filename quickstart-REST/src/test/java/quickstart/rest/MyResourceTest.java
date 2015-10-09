package quickstart.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import quickstart.rest.MessageConstants;
import quickstart.rest.SimpleResource;

public class MyResourceTest extends JerseyTest {

	/*
	 * (non-Javadoc)
	 * @see org.glassfish.jersey.test.JerseyTest#getTestContainerFactory()
	 * Run tests in Grizzly HTTP test container 
	 * (see https://jersey.java.net/documentation/latest/test-framework.html#d0e15474)
	 */
	
	private final static String resourceUrl = "/test-resource";
	@Override
	public TestContainerFactory getTestContainerFactory() {
		return new GrizzlyTestContainerFactory();
	}

	@Override
    protected Application configure() {
        return new ResourceConfig(SimpleResource.class);
    }
 
    @Test
    public void testGet() {
    	final WebTarget target = target(resourceUrl);
        final String res = target.request().get(String.class);
        System.out.println("Testing GET for " + target.toString());
        System.out.println("Resource sez: " + res);
        assertEquals(MessageConstants.GET_MESSAGE, res);
    }
    
    @Test
    public void testPost() {
    	final WebTarget target = target(resourceUrl);
        final Response res = target(resourceUrl).request().post(Entity.text(""));
        String stringRes = res.readEntity(String.class);
        System.out.println("Testing POST for " + target(resourceUrl));
        System.out.println("Resource says: " + stringRes);
        assertEquals(MessageConstants.POST_MESSAGE, stringRes);
    }
    
    @Test
    public void testFirstSecond() {
        final String res = target(resourceUrl+"/one-two").request().get(String.class);
        assertEquals("First: " + "one" + " Second: " + "two", res);
    }

}
