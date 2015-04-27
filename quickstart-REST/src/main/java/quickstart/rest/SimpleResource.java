package quickstart.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Root resource class hosted at the URI path "test-resource"
 */
@Path("/test-resource")
public class SimpleResource {

	/**	
	 * Simple method processing HTTP GET requests, producing "text/plain" media type.
	 * 
	 * @return String with message
	 * <p>
	 * Example request:
	 * 		GET /jersey2x/api/test-resource HTTP/1.1
			Host: localhost:8090
			<p>
	 * Example response:
	 * 		HTTP/1.1 200 OK
			Content-Type: text/plain
			<p>
			Hello from GET
	 * 
	 */
	@GET
	@Produces("text/plain")
	public String getMessage() {
		return MessageConstants.GET_MESSAGE;
	}

	/**
	 * Simple method processing HTTP POST requests, producing "text/html" media type.
	 * 
	 * @return String with message
	 * <p>
	 * Example request:<br>
	 * 		POST /jersey2x/api/test-resource HTTP/1.1
			Content-Type: text/plain; charset=UTF-8
			Host: localhost:8090
			<p>
	 * Example response:<br>
	  	HTTP/1.1 200 OK
		Content-Type: text/html
		<p>
		Hello from POST
	 * 
	 */
	@POST
	@Produces("text/html")
	public String getPost() {
		return MessageConstants.POST_MESSAGE;
	}
	

	/**
	 * Method processing HTTP GET requests with parameter parsing, producing
	 * "text/plain" MIME media type.
	 * 
	 * @return String that will be send back as a response of type "text/plain".
	 *  <p>
	 * Example request: <br>
	 * 		GET /jersey2x/api/test-resource/111-222 HTTP/1.1 <br>
	 * 		Host: localhost:8090
	 * <p>
	 * Example response: <br>
	 * 		HTTP/1.1 200 OK <br>
			Content-Type: text/plain <br>
		<p>

			First: 111 Second: 222
	 */
	@GET
	@Path("{first}-{second}")
	@Produces("text/plain")
	public String getFirstSecond(	@PathParam("first") String first,
									@PathParam("second") String second) {
		return "First: " + first + " Second: " + second;
	}



}
