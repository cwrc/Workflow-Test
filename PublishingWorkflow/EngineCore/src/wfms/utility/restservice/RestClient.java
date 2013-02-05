package wfms.utility.restservice;

import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;


// This is a utility class for working with REST services, specifically calling REST services and getting the responses.
public class RestClient {
	private static WebResource createResource(String url){
		return Client.create(new DefaultClientConfig()).resource(url);
	}
	
	private static Form createForm(Set<Parameter> params){
		Form form = new Form();
		for (Parameter param : params) {
			form.add(param.getKey(), param.getValue());
		}
		return form;
	}
	
	private static MultivaluedMap<String, String> createQuery(Set<Parameter> params){
		MultivaluedMap<String, String> map= new MultivaluedMapImpl();
		for (Parameter param : params) {
			map.add(param.getKey(), param.getValue());
		}
		return map;
	}
	
	public static Response POST(String url, Set<Parameter> params){
		ClientResponse clientResponse = createResource(url).post(ClientResponse.class,createForm(params));
		return new Response(clientResponse.getStatus(), clientResponse.getEntity(String.class));
	}
	
	public static Response GET(String url, Set<Parameter> params){
		ClientResponse clientResponse = createResource(url).queryParams(createQuery(params)).get(ClientResponse.class);
		return new Response(clientResponse.getStatus(), clientResponse.getEntity(String.class));
	}
}
