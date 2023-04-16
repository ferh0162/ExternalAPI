package com.example.externalapi.api;

import com.example.externalapi.dtos.Age;
import com.example.externalapi.dtos.Country;
import com.example.externalapi.dtos.Gender;
import com.example.externalapi.dtos.CombinedResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RemoteAPI {

  public Mono<Gender> genderEndPoint(String name){
    // This creates a connection to the website using something called WebClient.
    Mono<Gender> gender = WebClient.create()
        .get()
        .uri("https://api.genderize.io/?name="+name)
        // This gets the response from the website and puts it in a Mono.
        .retrieve()
        .bodyToMono(Gender.class)
        // This is what happens if there's a problem getting the response from the website.
        .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
    return gender;
  }

  public Mono<Age> ageEndPoint(String name){
    // This creates a connection to the website using something called WebClient.
    Mono<Age> age = WebClient.create()
        .get()
        .uri("https://api.agify.io/?name="+name)
        // This gets the response from the website and puts it in a Mono.
        .retrieve()
        .bodyToMono(Age.class)
        // This is what happens if there's a problem getting the response from the website.
        .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
    return age;
  }
  public Mono<Country> countryEndPoint(String name){
    // This creates a connection to the website using something called WebClient.
    Mono<Country> country = WebClient.create()
        .get()
        .uri("https://api.nationalize.io/?name="+name)
        // This gets the response from the website and puts it in a Mono.
        .retrieve()
        .bodyToMono(Country.class)
        // This is what happens if there's a problem getting the response from the website.
        .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
    return country;
  }

  public Mono<CombinedResponse> callSlowEndpointNonBlocking(String name){
    Mono<Gender> sr1 = genderEndPoint(name);
    Mono<Age> sr2 = ageEndPoint(name);
    Mono<Country> sr3 = countryEndPoint(name);

    return Mono.zip(sr1,sr2,sr3).map(t-> new CombinedResponse(t.getT1(),t.getT2(),t.getT3()));

  }
}
