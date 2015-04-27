package quickstart.rest;

import java.io.StringWriter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class MyClient {
	
	private static final String host = "http://localhost";
	private static final String port = "8090";
	private static final String baseUrl = host+":"+port;
	private static final String resourceUrl = baseUrl + "/jersey2x/api/test-resource";
	
	public static void main  (String[] args) {
		Client client = ClientBuilder.newClient();
		
		Response getResponse = client.target(resourceUrl).request().get();
		System.out.println("GET");
		System.out.println(getResponse);
		System.out.println(getResponse.readEntity(String.class));
		
		Response postResponse = client.target(resourceUrl).request().post(Entity.xml(""));
		System.out.println("POST");
		System.out.println(postResponse);
		System.out.println(postResponse.readEntity(String.class));

	}


}
