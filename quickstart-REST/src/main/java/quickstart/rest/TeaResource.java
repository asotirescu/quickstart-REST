package quickstart.rest;

import java.net.URI;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.JSONP;

import com.wordnik.swagger.annotations.*;

/**
 * Root resource class hosted at the URI path "/v1/teastore"
 */
@Path("/v1/teastore")
@Api(value = "/v1/teastore", description = "Operations on Tea Inventory")
public class TeaResource {
	@Context
	private ServletContext context;
	@Context
	private UriInfo uriInfo;
	
	/**
	 * getTeas 
	 * <p>
	 * Example request: <br>
	 * 			GET /jersey2x/api/v1/teastore/teas HTTP/1.1 <br>
	 * 			Host: localhost:8090 <br>
				Content-Type: application/json 
				<p>
	 * Example response: <br>
	 * 			HTTP/1.1 200 OK <br>
				Content-Type: application/xml <br>
				<p>
				
				{@code
				<?xml version="1.0" ?>
				<tea>
				  <name>Default TeaName</name>
				  <origin>Default Tea Origin</origin>
				  <country>Default Tea Country</country>
				  <year>0</year>
				</tea>
				}
				
	 * 
	 * @return Map - all the tea items in the tea database (in JSON format).<br>
	 * 					On error returns HTTP Status Code 404.
	 */

	@GET
	@Path("teas")
	@Produces(MediaType.APPLICATION_JSON)
	
	@ApiOperation(value = "Returns the entire tea database", notes = "Tea objects with their Ids", response=Map.class)
	@ApiResponses(value = {
	  @ApiResponse(code = 404, message = MessageConstants.NO_TEAS_IN_DB) 
	})
	public Map getTeas() {
		Map<Integer, Tea> teaDB = getTeaDB().getAll();
		if (teaDB == null)
			throw new NotFoundException(MessageConstants.NO_TEAS_IN_DB); 
		return teaDB;
	}

	/**
	 * createTea 
	 * <p>
	 *  @param Tea object in JSON format
	 *  
	 *  @return Location header with the URL of the newly created resource + Body
	 *         with newly created resource
	 *         <p>
	 * Example request: <br>
	 * 			POST /jersey2x/api/v1/teastore/teas/ HTTP/1.1 <br>
	 * 			Content-type: application/json <br>
	 * 			Host: localhost:8090 <p>
	 * 
	 * 			{"name":"Maocha","origin":"Nannuo","country":"China","year":2008}
	 * 
	 * <p>
	 * Example response: <br>	
	 * 			HTTP/1.1 201 Created <br>
				Location: http://localhost:8090/jersey2x/api/v1/teastore/teas/2 <br>
				Content-Type: application/json<p>
							
				{"name":"Maocha","origin":"Nannuo","country":"China","year":2008}

	 * 		
	 * 
	 */
	@POST
	@Path("teas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTea(Tea tea) {
		TeaDB teaDB = getTeaDB();
		int newId = 0;
		if (tea != null) {
			newId = teaDB.createTea(tea);
		}
		return Response
				.created(
						URI.create(uriInfo.getAbsolutePath().toString() + newId))
				.entity(tea).build();
	}

	/**
	 * getTea() <p>
	 * 
	 * 
	 * @param id   - tea item id
	 * 
	 * @return Tea - a content negotiated version of a Tea item: XML (default)
	 *         or JSON. 
	 *         On error returns HTTP Status Code 404.
	 *<p>                 
	 * Example request:<br>
	 * 		GET /jersey2x/api/v1/teastore/teas/1 HTTP/1.1
			Host: localhost:8090
			<p>
	   Example response:<br>
	   		HTTP/1.1 200 OK
			Content-Type: application/xml
			
			<p>
			{@code
			<?xml version="1.0" ?>
			<tea>
			  <name>Default TeaName</name>
			  <origin>Default Tea Origin</origin>
			  <country>Default Tea Country</country>
			  <year>0</year>
			</tea>
			}
		<p>
		To get back JSON, specify it in an Accept header:<br>
		Example request:<br>
			GET //jersey2x/api/v1/teastore/teas/1/ HTTP/1.1
			Accept: application/json
			Host: localhost:8090
			<p>
		Example response:<br>
			HTTP/1.1 200 OK
			Content-Type: application/json
			{@code
			{
			  "name": "Default TeaName",
			  "origin": "Default Tea Origin",
			  "country": "Default Tea Country",
			  "year": 0
			}
			}

	 */
	
	@GET
	@Path("teas/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Tea getTea(@PathParam("id") int id) {
		Map teaDB = getTeaDB().getAll();
		Tea tea = (Tea) teaDB.get(id);
		if (tea == null)
			throw new NotFoundException(); 

		return tea;
	}

	/**
	 * getTeaJsonP() <p>
	 * 
	 * 
	 * @param callback   - the JavaScript function callback that wraps the JSON object
	 * 
	 * @param id   - tea item id
	 * 
	 * @return Tea - a JSONP version of the Tea object, ie the JSON object wrapped in the function callback 
	 * 			specified by the "callback" query string parameter
	 *         On error returns HTTP Status Code 404.
	 *<p>                 
	 * Example request:<br>
	 * 		GET //jersey2x/api/v1/teastore/teas/1/jsonp?callback=foo HTTP/1.1
			Host: localhost:8090
			<p>
	   Example response:<br>
	   		HTTP/1.1 200 OK
			Content-Type: application/javascript
			
			<p>
			{@code
			foo({"name":"Default TeaName","origin":"Default Tea Origin","country":"Default Tea Country","year":0})
			}

	 */

	@GET
	@Path("teas/{id}/jsonp")
	@JSONP(queryParam="callback")
	@Produces({"application/javascript"})
	public Tea getTeaJsonP(@QueryParam("callback") String callback, @PathParam("id") int id) {
		return getTea(id);
	}
	
	/**
	 * deleteTea() <p>
	 * 
	 * 
	 * @param id   - tea item id
	 * 
	 * @return 204 No Content
	 *<p>                 
	 * Example request:<br>
	 * 		DELETE /jersey2x/api/v1/teastore/teas/1 HTTP/1.1 <br>
			Host: localhost:8090
			<p>
	   Example response:<br>
	   		HTTP/1.1 204 No Content

	 */
	@DELETE
	@Path("teas/{id}")
	public void deleteTea(@PathParam("id") int id) {
		TeaDB teaDB = getTeaDB();
		if (teaDB == null)
			throw new NotFoundException("No tea database found"); 

		teaDB.deleteTea(id);
	}

	private TeaDB getTeaDB() {
		TeaDB teaDB = null;
		try {
			teaDB = (TeaDB) context.getAttribute("TeaDB");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teaDB;
	}
}
