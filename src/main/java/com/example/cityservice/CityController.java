package com.example.cityservice;

import com.example.cityservice.City;
import com.example.cityservice.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<City>> searchCities(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country) {
        return ResponseEntity.ok(cityService.searchCities(name, country));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        City savedCity = cityService.createCity(city);
        return ResponseEntity.ok(savedCity);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCity(@PathVariable int id, @RequestBody City city) {
        cityService.updateCity(id, city);
        return ResponseEntity.ok("Город обновлен.");
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable int id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok("Город удален");
    }
}
