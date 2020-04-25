package br.com.thiago.test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.thiago.servicos.Servicos;
import br.com.thiago.test.entidate.LoginRequest;
import br.com.thiago.test.entidate.LoginResponse;
import br.com.thiago.test.entidate.PessoaRequest;
import br.com.thiago.test.entidate.PessoaResponse;
import br.com.thiago.test.entidate.PessoaUpdateResponse;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class ReqResExample {

	@Before
	public void configuraApi() {
		baseURI = 	"https://reqres.in/";
	}
	@Test
	public void methodGet() {
//		PessoaResponse as =
				given()
		.when()
			.get(Servicos.getUsers_ID.getValor(), 2)
		.then().contentType(ContentType.JSON)
			.statusCode(HttpStatus.SC_OK)
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/thiagoExample.json"))
			.log().all();
//			.and().extract().response().as(PessoaResponse.class);
		
		
	}
	@Test
	public void methodPost() {
		PessoaRequest pessoaRequest = new PessoaRequest("thiago","QA");
		
		basePath= "/api/users";		
		PessoaResponse as = given()
			.contentType("application/json")
		.body(pessoaRequest)
		.when()
			.post("/")
		.then()
			.statusCode(HttpStatus.SC_CREATED)
			.extract().response().as(PessoaResponse.class);
		
		Assert.assertNotNull(as);
		Assert.assertNotNull(as.getId());
		Assert.assertEquals(pessoaRequest.getNome(), as.getNome());
		Assert.assertEquals(pessoaRequest.getJob(), as.getJob());
	}
	@Test
	public void methodPost2() {
		PessoaRequest pessoaRequest = new PessoaRequest("thiago","QA");
		
		basePath= "/api/users";		
		String as =
				given()
			.contentType("application/json")
		.body(pessoaRequest)
		.when()
			.post("/")
		.then()
			.statusCode(HttpStatus.SC_CREATED).log().all()
			.and().extract().response().path("nome");
		
		System.out.println(as);
		Assert.assertNotNull(as);
	}
	
	@Test
	public void methodPut() {
		PessoaRequest pessoaRequest = new PessoaRequest("thiago","QA");

		PessoaResponse pessoaResponse = 
			given()
				.contentType("application/json")
			.body(pessoaRequest)
			.when()
				.put(Servicos.getUsers_ID.getValor(),2)
			.then()
				.statusCode(HttpStatus.SC_OK).log().all()
				.and().extract().response().as(PessoaResponse.class);
			
		assertNotNull(pessoaResponse);
		assertEquals(pessoaRequest.getNome(), pessoaResponse.getNome());
		assertEquals(pessoaRequest.getJob(), pessoaResponse.getJob());
	}
	
	@Test
	public void methodPatch() {
		PessoaRequest pessoaRequest = new PessoaRequest("thiago","QA");

		PessoaUpdateResponse pessoaResponse = 
			given()
				.contentType("application/json")
			.body(pessoaRequest)
			.when()
				.put(Servicos.getUsers_ID.getValor(),2)
			.then()
				.statusCode(HttpStatus.SC_OK).log().all()
				.and().extract().response().as(PessoaUpdateResponse.class);
			
		assertNotNull(pessoaResponse);
		assertEquals(pessoaRequest.getNome(), pessoaResponse.getNome());
		assertEquals(pessoaRequest.getJob(), pessoaResponse.getJob());
	}
	
	@Test
	public void methodPostRegister() {
		basePath= "/api/register";		
		
		LoginRequest loginRequest = new LoginRequest("eve.holt@reqres.in","pistol");
		LoginResponse loginResponse = 
			given()
				.contentType("application/json")
			.body(loginRequest)
			.when()
				.post("/")
			.then()
				.statusCode(HttpStatus.SC_OK).log().all()
				.and().extract().response().as(LoginResponse.class);
			
		assertNotNull(loginResponse);
		assertNotNull(loginResponse.getToken());
	}
	
	@Test
	public void methodPostLoginUnsuccessful() {
		basePath= "/api/register";		
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("peter@klaven");

		given()
			.contentType("application/json")
			.body(loginRequest)
		.when()
			.post("/")
		.then()
			.statusCode(HttpStatus.SC_BAD_REQUEST).log().all();
	}
	
	@Test
	public void methodDelete() {
		
		int statusCodeResponse = given()
		.when()
			.delete(Servicos.getUsers_ID.getValor(),2)
		.then()
			.statusCode(HttpStatus.SC_NO_CONTENT)
			.log().all()
		.and()
			.extract().statusCode();
		
		assertEquals(204, statusCodeResponse);
	}
	
	@Test
	public void methodGetListUsers() {
		int statusCode = given()
			.when()
				.get(Servicos.getUsers_PAGE.getValor(), 2)
			.then().contentType(ContentType.JSON)
				.statusCode(HttpStatus.SC_OK)
				.and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/listUserPage.json"))
				.log().all()
			.and().extract().response().getStatusCode();
		
		assertEquals(200, statusCode);	
	}
	
	@Test
	public void methodGetListUserValidateEmail() {

		String email =given()
			.when()
				.get(Servicos.getUsers_PAGE.getValor(), 2)
			.then()
				.contentType(ContentType.JSON)
				.statusCode(HttpStatus.SC_OK)
				.and()
				.log().all()
			.and().extract().response().jsonPath().getString("data.email[0]");
		System.out.println(email);
		
		assertNotNull(email);	
	}
	
	@Test
	public void methodGetuserNotFound() {
		int statusCode = given()
			.when()
				.get(Servicos.getUsers_ID.getValor(), 23)
			.then().contentType(ContentType.JSON)
				.statusCode(HttpStatus.SC_NOT_FOUND)
				.log().all()
			.and().extract().response().getStatusCode();
		
		assertEquals(404, statusCode);	
	}
	
}
