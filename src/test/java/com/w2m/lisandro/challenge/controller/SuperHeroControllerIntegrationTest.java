package com.w2m.lisandro.challenge.controller;

import com.w2m.lisandro.challenge.model.SuperHero;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Java6Assertions.assertThat;

@Sql("/db/scripts/insert_super_heroes.sql")
public class SuperHeroControllerIntegrationTest extends BaseApiTest implements SuperHeroControllerTestCases {

    @Override
    @Test
    public void testFindAll() {
        List<SuperHero> response = when()
                .get("/superheroes")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", SuperHero.class);

        assertThat(response).hasSize(11);
    }

    @Override
    @Test
    public void testFindByName() {
        List<SuperHero> response = when()
                .get("/superheroes?name=mAn")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", SuperHero.class);

        assertThat(response).hasSize(5);
        List<String> expectedList = List.of("Acquaman", "Superman", "Birdman", "Batman", "Ironman");

        assertThat(response.stream().map(superHero -> superHero.getName()).collect(Collectors.toList())).isEqualTo(expectedList);

    }

    @Override
    @Test
    public void testGetById_200_OK() {

        SuperHero superHero = when()
                .get("/superheroes/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(SuperHero.class);

        assertThat(superHero.getName()).isEqualTo("Acquaman");
    }

    @Override
    @Test
    public void testGetById_404_Not_Found() {
        when()
                .get("/superheroes/100")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Override
    @Test
    public void testPost_200_OK() {
        String body = "{\"name\": \"He-Man\"}";
        SuperHero saved = given().contentType("application/json")
                .body(body)
                .when().post("/superheroes")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(SuperHero.class);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("He-Man");
    }

    @Override
    @Test
    public void testPut_200_OK() {
        String body = "{\"name\": \"Batman Reloaded\"}";
        SuperHero saved = given()
                .contentType("application/json")
                .pathParams("id", 5)
                .body(body)
                .when().put("/superheroes/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(SuperHero.class);

        assertThat(saved.getId()).isEqualTo(5);
        assertThat(saved.getName()).isEqualTo("Batman Reloaded");

    }

    @Override
    @Test
    public void testPut_404_Not_Found() {
        String body = "{\"name\": \"Batman Reloaded\"}";

        given()
                .contentType("application/json")
                .pathParams("id", 100)
                .body(body)
                .when().put("/superheroes/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Override
    @Test
    public void testDelete_204_No_Content() {

        given()
                .contentType("application/json")
                .pathParams("id", 1)
                .when().delete("/superheroes/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Override
    @Test
    public void testDelete_404_Not_Found() {

        given()
                .contentType("application/json")
                .pathParams("id", 100)
                .when().delete("/superheroes/{id}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
