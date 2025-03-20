package com.example.cityclient;

public class City {
    private int id;
    private String name;
    private String country;
    private String theme;
    private int population;
    private int foundedYear;

    public City() {}

    public City(int id, String name, String country, String theme, int population, int foundedYear) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.theme = theme;
        this.population = population;
        this.foundedYear = foundedYear;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }

    public int getFoundedYear() { return foundedYear; }
    public void setFoundedYear(int foundedYear) { this.foundedYear = foundedYear; }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", theme='" + theme + '\'' +
                ", population=" + population +
                ", foundedYear=" + foundedYear +
                '}';
    }
}
