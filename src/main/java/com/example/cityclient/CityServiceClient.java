package com.example.cityclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CityServiceClient {
    public static void main(String[] args) throws IOException {
        RestClient client = new RestClient();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n=== Меню ===");
            System.out.println("1. Искать города");
            System.out.println("2. Добавить город");
            System.out.println("3. Обновить город");
            System.out.println("4. Удалить город");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");
            String choice = reader.readLine().trim();

            switch (choice) {
                case "1":
                    searchCities(client, reader);
                    break;
                case "2":
                    createCity(client, reader);
                    break;
                case "3":
                    updateCity(client, reader);
                    break;
                case "4":
                    deleteCity(client, reader);
                    break;
                case "5":
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Некорректный ввод, попробуйте снова.");
                    break;
            }
        }
    }

    private static void searchCities(RestClient client, BufferedReader reader) throws IOException {
        System.out.print("Введите название (или Enter): ");
        String name = reader.readLine().trim();
        System.out.print("Введите страну (или Enter): ");
        String country = reader.readLine().trim();
        System.out.print("Введите тематику (или Enter): ");
        String theme = reader.readLine().trim();
        System.out.print("Введите население (или Enter): ");
        String populationInput = reader.readLine().trim();
        Integer population = populationInput.isEmpty() ? null : Integer.parseInt(populationInput);
        System.out.print("Введите год основания (или Enter): ");
        String foundedYearInput = reader.readLine().trim();
        Integer foundedYear = foundedYearInput.isEmpty() ? null : Integer.parseInt(foundedYearInput);
        List<City> cities = client.searchCities(name, country,theme,population,foundedYear);
        if (cities == null || cities.isEmpty()) {
            System.out.println("Города не найдены.");
        } else {
            cities.forEach(System.out::println);
        }
    }

    private static void createCity(RestClient client, BufferedReader reader) throws IOException {
        System.out.print("Название: ");
        String name = reader.readLine();
        System.out.print("Страна: ");
        String country = reader.readLine();
        System.out.print("Тематика: ");
        String theme = reader.readLine();
        System.out.print("Население: ");
        int population = Integer.parseInt(reader.readLine());
        System.out.print("Год основания: ");
        int foundedYear = Integer.parseInt(reader.readLine());

        int cityId = client.createCity(new City(0, name, country, theme, population, foundedYear));
        if (cityId > 0) {
            System.out.println("Город создан с ID: " + cityId);
        } else {
            System.out.println("Ошибка при создании города.");
        }
    }

    private static void updateCity(RestClient client, BufferedReader reader) throws IOException {
        System.out.print("ID города: ");
        int id = Integer.parseInt(reader.readLine());
        System.out.print("Новое название: ");
        String name = reader.readLine();
        System.out.print("Новая страна: ");
        String country = reader.readLine();
        System.out.print("Новая тематика: ");
        String theme = reader.readLine();
        System.out.print("Новое население: ");
        int population = Integer.parseInt(reader.readLine());
        System.out.print("Новый год основания: ");
        int foundedYear = Integer.parseInt(reader.readLine());

        String response = client.updateCity(id, new City(id, name, country, theme, population, foundedYear));
        System.out.println(response);
    }

    private static void deleteCity(RestClient client, BufferedReader reader) throws IOException {
        System.out.print("ID города для удаления: ");
        int id = Integer.parseInt(reader.readLine());

        String response = client.deleteCity(id);
        System.out.println(response);
    }
}
