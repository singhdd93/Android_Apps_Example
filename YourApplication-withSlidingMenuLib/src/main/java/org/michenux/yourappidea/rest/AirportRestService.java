package org.michenux.yourappidea.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.michenux.android.rest.TimestampDeserializer;
import org.michenux.yourappidea.model.AirportRestResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;

public class AirportRestService {

	private static final AirportRestService instance = new AirportRestService();

	public static final String IN_MODE = "in";

	private HttpHeaders requestHeaders ;

	private RestTemplate restTemplate ;
	
	public static AirportRestService getInstance() {
		return instance;
	}

	private AirportRestService() {
		requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);
		
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setReadTimeout(20000);
		requestFactory.setConnectTimeout(10000);
		
		restTemplate = new RestTemplate(requestFactory);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampDeserializer());
		GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter(gsonBuilder.create());
		restTemplate.getMessageConverters().add(gsonConverter);
	}

	public AirportRestResponse getAirportInfo(String airport, String mode, String url) {

		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
	
		ResponseEntity<AirportRestResponse> responseEntity = restTemplate.exchange(url,
				HttpMethod.GET, requestEntity, AirportRestResponse.class, airport, mode);

		return responseEntity.getBody();
	}
}
