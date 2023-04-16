package com.example.externalapi.service;

import com.example.externalapi.dtos.CombinedResponse;
import com.example.externalapi.entity.Person;
import com.example.externalapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
  private  PersonRepository personRepository;

  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Person savePersonFromCombinedResponse(CombinedResponse combinedResponse) {
    Person person = new Person(
        combinedResponse.getName(),
        combinedResponse.getGender(),
        combinedResponse.getGenderCount(),
        combinedResponse.getGenderProbability(),
        combinedResponse.getAge(),
        combinedResponse.getAgeCount(),
        combinedResponse.getCountryName(),
        combinedResponse.getCountryProbability()
    );
    return personRepository.save(person);
  }

  public Person savePerson(String name, String gender, int genderCount, double genderProbability, int age, int ageCount, String countryName, double countryProbability) {
    Person person = new Person(name, gender, genderCount, genderProbability, age, ageCount, countryName, countryProbability);
    return personRepository.save(person);
  }

  public Optional<Person> findInCache(String name) {
    return personRepository.findPersonByName(name);
  }
}
