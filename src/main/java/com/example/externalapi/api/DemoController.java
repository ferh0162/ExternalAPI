package com.example.externalapi.api;

import com.example.externalapi.dtos.Age;
import com.example.externalapi.dtos.CombinedResponse;
import com.example.externalapi.dtos.Country;
import com.example.externalapi.dtos.Gender;
import com.example.externalapi.entity.Person;
import com.example.externalapi.repository.PersonRepository;
import com.example.externalapi.service.PersonService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class DemoController {
  private final int SLEEP_TIME = 1000*3;

  @GetMapping(value = "/random-string-slow")
  public String slowEndpoint() throws InterruptedException {
    Thread.sleep(SLEEP_TIME);
    return RandomStringUtils.randomAlphanumeric(10);
  }
  private RemoteAPI remoteAPI = new RemoteAPI();
  private PersonService personService;

  private PersonRepository personRepository;

  public DemoController(PersonService personService, PersonRepository personRepository) {
    this.personService = personService;
    this.personRepository = personRepository;
  }

  @GetMapping("/name-info")
  public CombinedResponse getNameInfo(@RequestParam("name") String name) {

    CombinedResponse res;

    //Caching
    if (personRepository.existsByName(name)) {
      Optional<Person> person = personRepository.findPersonByName(name);
      res = new CombinedResponse(person.get());
      System.out.println(name + " exists already");
    } else {
      //Calling non-blocking function
      res = remoteAPI.callSlowEndpointNonBlocking(name).block();
      personService.savePersonFromCombinedResponse(res);

      System.out.println(name + " does not exist");
    }

    return res;
  }
}
