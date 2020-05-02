package rest;

import java.util.Base64;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nilfactor.activity3.utility.LogInterceptor;
import com.nilfactor.activity3.utility.ServiceService;

import data.UserEntityRepository;
import data.WeatherDataRepository;
import entity.UserEntity;
import entity.WeatherDataEntity;

@RequestScoped
@Path("/weather")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Interceptors(LogInterceptor.class)
public class WeatherService {
	private WeatherDataRepository weatherDataRepository = ServiceService.getWeatherDataRepository();
	private UserEntityRepository userEntityRepository = ServiceService.getUserEntityRepository();
	
	/*
	 * This is for those who need to create the base64 encoded header value for Authorization
	 */
	@Interceptors(LogInterceptor.class)
	private String generateB64AuthString(String username, String password) {
		String normalString = username + ":" + password;
		String encodedString = Base64.getEncoder().encodeToString(normalString.getBytes());
		return "Basic " + encodedString;
	}
	
	@Interceptors(LogInterceptor.class)
	private boolean isUserAuthenticated(String authString) {
		try {
			String decodedAuth = "";
	        System.out.println(" ");
	        String[] parts = authString.split(" ");
	        String authInfo = parts[1];
	        byte[] bytes = null;
	        
		    bytes = Base64.getDecoder().decode(authInfo);
		    decodedAuth = new String(bytes);
	        
	        String[] decodedParts = decodedAuth.split(":");
	        
	        if (decodedParts.length < 2) {
	        	return false;
	        }
	       
	        String username = decodedParts[0];
	        String password = decodedParts[1];
	        UserEntity user = userEntityRepository.findUserByUsername(username);
	        
	        if (user.getPassword().equals(password)) {
	        	return true;
	        }
		} catch (Exception e) {
        	System.out.println(e.getMessage());
        	return false;
        }
         
        return false;
    }
	
	@Interceptors(LogInterceptor.class)
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@HeaderParam("authorization") String authString) {
		if (!this.isUserAuthenticated(authString)) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		return Response.status(Response.Status.OK)
		        .entity(weatherDataRepository.getAll())
		        .build();
	}
	
	@Interceptors(LogInterceptor.class)
	@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getById(@HeaderParam("authorization") String authString, @PathParam("id") long id) {
		if (!this.isUserAuthenticated(authString)) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		WeatherDataEntity wde = weatherDataRepository.getById(id);
		if (wde == null) {
			return Response.status(Response.Status.NOT_FOUND)
			        .entity("Not Found")
			        .build();
		}
		
		return Response.status(Response.Status.OK)
		        .entity(wde)
		        .build();
	}
	
	@Interceptors(LogInterceptor.class)
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@HeaderParam("authorization") String authString, WeatherDataEntity wde) {
		if (!this.isUserAuthenticated(authString)) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}

		weatherDataRepository.save(wde);
		return Response.status(Response.Status.OK)
		        .entity(wde)
		        .build();
	}
	
	@Interceptors(LogInterceptor.class)
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@HeaderParam("authorization") String authString, @PathParam("id") long id) {
		if (!this.isUserAuthenticated(authString)) {
			return Response.status(Response.Status.UNAUTHORIZED)
		        .entity("{\"error\":\"User not authenticated\"}")
		        .build();
		}
		
		WeatherDataEntity wde = weatherDataRepository.getById(id);
		
		if (wde != null) {
			weatherDataRepository.deleteById(id);
			wde = weatherDataRepository.getById(id);
			
			if (wde != null) {
				return Response
			        .status(Response.Status.INTERNAL_SERVER_ERROR)
			        .entity("{\"message\": \"entry not deleted\"}")
			        .build();
			}
			
			return Response
			        .status(Response.Status.OK)
			        .entity("{\"message\": \"entry deleted\"}")
			        .build();
		}
		
		return Response
		        .status(Response.Status.NOT_FOUND)
		        .entity("Not Found")
		        .build();
	}
}
