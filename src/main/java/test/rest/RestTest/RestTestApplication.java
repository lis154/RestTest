package test.rest.RestTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import test.rest.RestTest.Model.User;

import java.util.List;

@SpringBootApplication
public class RestTestApplication {

	static final String URL_EMPLOYEES = "http://91.241.64.178:7081/api/users";

	public static void main(String[] args) {


		SpringApplication.run(RestTestApplication.class, args);
		String sessionId = getAllUSer();
		addUser(sessionId);
		changeUser(sessionId);
		deleteUser(sessionId);

	}

	public static String getAllUSer(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<User>> rateResponse =
				restTemplate.exchange(URL_EMPLOYEES,
						HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
						});
		HttpHeaders head = rateResponse.getHeaders();
		String headers = head.getFirst("Set-Cookie");
		return headers;
	}

	public static String addUser(String gSessionId){
		RestTemplate restTemplate = new RestTemplate();
		User user = new User (Long.valueOf(3), "James", "Brown", (byte) 29);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cookie", gSessionId);

		HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
		ResponseEntity<String> result
				= restTemplate.postForEntity(URL_EMPLOYEES, requestBody, String.class);
		HttpHeaders head = result.getHeaders();
		System.out.println(result.getBody());
		return head.getFirst("Set-Cookie");
	}
	public static void changeUser(String gSessionId) {
		RestTemplate restTemplate = new RestTemplate();
		User user = new User (Long.valueOf(3), "Thomas", "Shelby", (byte) 29);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cookie", gSessionId);
		HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
		ResponseEntity<String> result
				= restTemplate.exchange(URL_EMPLOYEES, HttpMethod.PUT , requestBody, String.class);
		System.out.println(result.getBody());
	}

	public static void deleteUser(String gSessionId){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cookie", gSessionId);
		String url = URL_EMPLOYEES + "/3";
		HttpEntity<User> requestBody = new HttpEntity<>(null, headers);
		ResponseEntity<String> result
				= restTemplate.exchange(url, HttpMethod.DELETE , requestBody, String.class);
		System.out.println(result.getBody());
	}

}
