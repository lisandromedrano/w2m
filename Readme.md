
## Prueba técnica Spring Boot

Desarrollar, utilizando Spring Boot 2 y Java 11, una API que permita hacer un mantenimiento de súper
héroes.

### Este mantenimiento permite:

- Consultar todos los súper héroes. :heavy_check_mark:
- Consultar un único súper héroe por id. :heavy_check_mark:
- Consultar todos los súper héroes que contienen, en su nombre, el valor de un parámetro
enviado en la petición. Por ejemplo, si enviamos “man” devolverá “Spiderman”, “Superman”,
“Manolito el fuerte”, etc. :heavy_check_mark:
- Modificar un súper héroe. :heavy_check_mark:
- Eliminar un súper héroe. :heavy_check_mark:
- Test unitarios de algún servicio. :heavy_check_mark:

### Puntos a tener en cuenta:

- Los súper héroes se deben guardar en una base de datos H2 en memoria.:heavy_check_mark:
- La prueba se debe presentar en un repositorio de Git. No hace falta que esté publicado. Se
puede pasar comprimido en un único archivo.:heavy_check_mark:

### Puntos opcionales de mejora:

- Utilizar alguna librería que facilite el mantenimiento de los scripts DDL de base de datos. El proyecto contiene scripts de migracion de Flyway :heavy_check_mark:
- Implementar una anotación personalizada que sirva para medir cuánto tarda en ejecutarse.
una petición. Se podría anotar alguno o todos los métodos de la API con esa anotación.
Funcionaría de forma similar al @Timed de Spring, pero imprimiendo la duración en un log.:heavy_check_mark:
- Gestión centralizada de excepciones.:heavy_check_mark:
- Test de integración.:heavy_check_mark:
- Presentar la aplicación dockerizada. :heavy_check_mark:
- Poder cachear peticiones. :heavy_check_mark:
- Documentación de la API. :heavy_check_mark:
- Seguridad del API. ❌


## Swagger Api docs

http://localhost:8080/swagger-ui.html

## Build and Start Services


``` 
.gradlew clean
``` 
``` 
.gradlew build
``` 

``` 
docker build -t w2m-app .
``` 


``` 
docker run -dp 8080:8080 w2m-app
``` 

## Postman Collection

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/6337e87c05811a3c0a7e)
