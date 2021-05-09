package com.w2m.lisandro.challenge.controller;

import com.w2m.lisandro.challenge.model.SuperHero;
import com.w2m.lisandro.challenge.service.SuperHeroService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;

public class SuperHeroControllerTest extends BaseApiTest implements SuperHeroControllerTestCases {
    @MockBean
    private SuperHeroService superHeroService;

    @Override
    @Test
    public void testFindAll(){
        SuperHero superman = new SuperHero(1, "Superman");
        SuperHero batman = new SuperHero(2, "Batman");
        Mockito.when(superHeroService.findAll()).thenReturn(Arrays.asList(superman, batman));

        List<SuperHero> response = when()
                .get("/superheroes")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", SuperHero.class);

        assertThat(response).hasSize(2);
    }

    @Override
    @Test
    public void testFindByName(){
        SuperHero superman = new SuperHero(1, "Superman");
        SuperHero batman = new SuperHero(2, "Batman");
        Mockito.when(superHeroService.findByName(any())).thenReturn(Arrays.asList(superman, batman));

        List<SuperHero> response = given().param("name", "man")
                .when()
                .get("/superheroes")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", SuperHero.class);

        assertThat(response).hasSize(2);
    }

    @Override
    @Test
    public void testGetById_200_OK(){
        SuperHero superman = new SuperHero(1, "Superman");

        Mockito.when(superHeroService.findById(eq(1))).thenReturn(superman);

        when()
                .get("/superheroes/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }


    @Override
    @Test
    public void testGetById_404_Not_Found(){
        doThrow(NoSuchElementException.class).when(superHeroService).findById(anyInt());
        when()
                .get("/superheroes/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Override
    @Test
    public void testPost_200_OK(){
        SuperHero superman = new SuperHero(1, "Superman");
        Mockito.when(superHeroService.save(any(SuperHero.class))).thenReturn(superman);

        String body = "{\"name\": \"superman\"}";
        SuperHero saved = given().contentType("application/json")
                .body(body)
                .when().post("/superheroes")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(SuperHero.class);

        assertThat(saved.getId()).isEqualTo(1);

    }

    @Override
    @Test
    public void testPut_200_OK(){
        SuperHero superman = new SuperHero(1, "Superman");
        Mockito.when(superHeroService.update(anyInt(), any(SuperHero.class))).thenReturn(superman);

        String body = "{\"name\": \"superman\"}";
        SuperHero saved = given()
                .contentType("application/json")
                .pathParams("id",1)
                .body(body)
                .when().put("/superheroes/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(SuperHero.class);

        assertThat(saved.getId()).isEqualTo(1);

    }

    @Override
    @Test
    public void testPut_404_Not_Found(){
        doThrow(NoSuchElementException.class).when(superHeroService).update(anyInt(), any());

        String body = "{\"name\": \"superman\"}";
        given()
                .contentType("application/json")
                .pathParams("id",1)
                .body(body)
                .when().put("/superheroes/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Override
    @Test
    public void testDelete_204_No_Content(){

        given()
                .contentType("application/json")
                .pathParams("id",1)
                .when().delete("/superheroes/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Override
    @Test
    public void testDelete_404_Not_Found(){
        doThrow(EmptyResultDataAccessException.class).when(superHeroService).delete(anyInt());

        given()
                .contentType("application/json")
                .pathParams("id",1)
                .when().delete("/superheroes/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
