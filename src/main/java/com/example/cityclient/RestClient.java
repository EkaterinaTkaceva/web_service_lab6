package com.example.cityclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;


public class RestClient {
    private static final String BASE_URL = "http://localhost:8080/cities";
    private final Client client;
    private final ObjectMapper objectMapper;

    public RestClient() {
        this.client = Client.create();
        this.objectMapper = new ObjectMapper();
    }

    public List<City> searchCities(String name, String country, String theme, Integer population, Integer foundedYear) {
        WebResource webResource = client.resource(BASE_URL + "/search");

        if (name != null && !name.isEmpty()) {
            webResource = webResource.queryParam("name", name);
        }
        if (country != null && !country.isEmpty()) {
            webResource = webResource.queryParam("country", country);
        }
        if (theme != null && !theme.isEmpty()) {
            webResource = webResource.queryParam("theme", theme);
        }
        if (population != null) {
            webResource = webResource.queryParam("population", population.toString());
        }
        if (foundedYear != null) {
            webResource = webResource.queryParam("foundedYear", foundedYear.toString());
        }

        try {
            ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            checkResponse(response);
            String jsonResponse = response.getEntity(String.class);
            return objectMapper.readValue(jsonResponse, new TypeReference<List<City>>() {});
        } catch (Exception e) {
            System.out.println("Ошибка при получении данных: " + e.getMessage());
            return null;
        }
    }

    public int createCity(City city) {
        WebResource webResource = client.resource(BASE_URL + "/create");

        try {
            ClientResponse response = webResource
                    .type(MediaType.APPLICATION_JSON)
                    .post(ClientResponse.class, objectMapper.writeValueAsString(city));
            checkResponse(response);
            JsonNode jsonNode = objectMapper.readTree(response.getEntity(String.class));
            return jsonNode.get("id").asInt();         } catch (Exception e) {
            System.out.println("Ошибка при создании города: " + e.getMessage());
            return -1;
        }
    }

    public String updateCity(int id, City city) {
        WebResource webResource = client.resource(BASE_URL + "/update/" + id);

        try {
            ClientResponse response = webResource
                    .type(MediaType.APPLICATION_JSON)
                    .put(ClientResponse.class, objectMapper.writeValueAsString(city));
            int status = response.getStatus();
            checkResponse(response);
            return "Обновление успешно. Статус: " + status + ". Ответ: " + response.getEntity(String.class);
        } catch (Exception e) {
            return "Ошибка обновления: " + e.getMessage();
        }
    }

    public String deleteCity(int id) {
        WebResource webResource = client.resource(BASE_URL + "/delete/" + id);

        try {
            ClientResponse response = webResource.delete(ClientResponse.class);
            int status = response.getStatus();
            checkResponse(response);
            return "Удаление успешно. Статус: " + status + ". Ответ: " + response.getEntity(String.class);
        } catch (Exception e) {
            return "Ошибка удаления: " + e.getMessage();
        }
    }

    private void checkResponse(ClientResponse response) {
        if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RuntimeException("Ошибка сервера: " + response.getStatus());
        }
    }
}
