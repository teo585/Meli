#Meli 
# Proyecto de Ejemplo con Gradle y Java

Este proyecto utiliza Gradle 7.1 y Java. A continuación, se describe cómo enviar un JSON a una API específica.

## Requisitos

- Gradle 7.1
- Java (versión especificada en tu `build.gradle`)

## Enviar JSON a la API
 
Para interactuar con la API, debes enviar un JSON con tres atributos a la siguiente URL: http://localhost:8080/api/pedido/crear





### Formato del JSON

El JSON debe tener el siguiente formato:

json
{
  "correoElectronico": "usuario@example.com",
  "latitud": "6.90",
  "longitud": "-71.61"
}

### Ejemplo de Solicitud
### Puedes enviar la solicitud usando herramientas como curl o Postman. Aquí tienes un ejemplo de cómo hacerlo con curl:
curl -X POST http://localhost:8080/api/pedido/crear \
-H "Content-Type: application/json" \
-d '{
"correoElectronico": "usuario@example.com",
"latitud": "6.90",
"longitud": "-71.61"
}'


### Instrucciones Adicionales
### Configuración de Gradle: Asegúrate de tener Gradle 7.1 instalado y configurado correctamente en tu sistema.
###Construcción del Proyecto: Para construir el proyecto, navega a la raíz del proyecto y ejecuta:

gradlew build
###Ejecución del Proyecto: Para ejecutar el proyecto, usa:
gradlew run