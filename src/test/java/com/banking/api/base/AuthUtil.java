package com.banking.api.base;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthUtil {
    // static variable — token generated once, reused across all tests
	private static String authToken;
	//private static final String USERNAME="admin";
	public static String getAuthToken() {
		 // Only generate token if not already generated
        // avoids calling /auth before every single test
		if (authToken==null) {
			authToken = generateToken();
		}
		return authToken;
	
	}
	private static String generateToken() {
		// Request body — credentials as raw JSON
		String credentials="{\"username\":\"admin\",\"password\":\"password123\"}";
		
		Response response=
				 given()
	                .baseUri("https://restful-booker.herokuapp.com")
	                .header("Content-Type", "application/json")
	                .body(credentials)
	                .when()
	                .post("/auth")
	                .then()
	                .statusCode(200)
	                .body("token",notNullValue())
	                .extract().response();
		String token = response.jsonPath().getString("token");
        System.out.println("Generated Auth Token: " + token);
        return token;
	}
	
	
	

}
