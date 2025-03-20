package com.example.cityservice;

import com.example.cityservice.City;
import com.example.cityservice.CityRepository;
import com.example.cityservice.CityNotFoundException;
import com.example.cityservice.InvalidCityDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> searchCities(String name, String country) {
        if (name != null && !name.isEmpty()) {
            return cityRepository.findByNameContainingIgnoreCase(name);
        } else if (country != null && !country.isEmpty()) {
            return cityRepository.findByCountryContainingIgnoreCase(country);
        } else {
            return cityRepository.findAll();
        }
    }

    public City createCity(City city) {
        if (city.getName() == null || city.getName().trim().isEmpty()) {
            throw new InvalidCityDataException("Название города не может быть пустым.");
        }
        if (city.getCountry() == null || city.getCountry().trim().isEmpty()) {
            throw new InvalidCityDataException("Страна не может быть пустой.");
        }
        if (city.getPopulation() != null && city.getPopulation() < 0) {
            throw new InvalidCityDataException("Население не может быть отрицательным.");
        }
        if (city.getFoundedYear() != null && city.getFoundedYear() < 0) {
            throw new InvalidCityDataException("Год основания не может быть отрицательным.");
        }
        return cityRepository.save(city);
    }

    public City updateCity(int id, City cityDetails) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (!cityOptional.isPresent()) {
            throw new RuntimeException("Город с ID " + id + " не найден");
        }
        City city = cityOptional.get();
        city.setName(cityDetails.getName());
        city.setCountry(cityDetails.getCountry());
        city.setTheme(cityDetails.getTheme());
        city.setPopulation(cityDetails.getPopulation());
        city.setFoundedYear(cityDetails.getFoundedYear());

        return cityRepository.save(city);
    }

    public void deleteCity(int id) {
        if (!cityRepository.existsById(id)) {
            throw new CityNotFoundException("Город с ID " + id + " не найден.");
        }
        cityRepository.deleteById(id);
    }
}
