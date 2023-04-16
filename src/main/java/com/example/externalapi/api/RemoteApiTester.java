// This is a package name that helps the computer find the right files.
package com.example.externalapi.api;

// These are special tools that the program needs to use.
import com.example.externalapi.dtos.Gender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// This is a controller class that helps the program connect to the website.
@Controller
public class RemoteApiTester implements CommandLineRunner {

  // This is a method that the program will use to connect to the website and get information.
  private Mono<String> callSlowEndpoint(){
    // This creates a connection to the website using something called WebClient.
    Mono<String> slowResponse = WebClient.create()
        .get()
        .uri("http://localhost:8080/random-string-slow")
        // This gets the response from the website and puts it in a Mono.
        .retrieve()
        .bodyToMono(String.class)
        // This is what happens if there's a problem getting the response from the website.
        .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
    return slowResponse;
  }
  public void callSlowEndpointBlocking(){
    long start = System.currentTimeMillis();
    List<String> ramdomStrings = new ArrayList<>();

    Mono<String> slowResponse = callSlowEndpoint();
    ramdomStrings.add(slowResponse.block()); //Three seconds spent

    slowResponse = callSlowEndpoint();
    ramdomStrings.add(slowResponse.block());//Three seconds spent

    slowResponse = callSlowEndpoint();
    ramdomStrings.add(slowResponse.block());//Three seconds spent
    long end = System.currentTimeMillis();

    ramdomStrings. add(0,"Time spent BLOCKING (ms): "+(end-start));

    System.out.println(ramdomStrings.stream().collect(Collectors.joining(",")));
  }

  public void callSlowEndpointNonBlocking(){
    long start = System.currentTimeMillis();
    Mono<String> sr1 = callSlowEndpoint();
    Mono<String> sr2 = callSlowEndpoint();
    Mono<String> sr3 = callSlowEndpoint();

    var rs = Mono.zip(sr1,sr2,sr3).map(t-> {
      List<String> randomStrings = new ArrayList<>();
      randomStrings.add(t.getT1());
      randomStrings.add(t.getT2());
      randomStrings.add(t.getT3());
      long end = System.currentTimeMillis();
      randomStrings.add(0,"Time spent NON-BLOCKING (ms): "+(end-start));
      return randomStrings;
    });
    List<String> randoms = rs.block(); //We only block when all the three Mono's has fulfilled
    System.out.println(randoms.stream().collect(Collectors.joining(",")));
  }

  Mono<Gender> getGenderForName(String name) {
    WebClient client = WebClient.create();
    Mono<Gender> gender = client.get()
        .uri("https://api.genderize.io?name="+name)
        .retrieve()
        .bodyToMono(Gender.class);
    return gender;
  }

  List<String> names = Arrays.asList("lars", "peter", "sanne", "kim", "david", "maja");


  public void getGendersBlocking() {
    long start = System.currentTimeMillis();
    List<Gender> genders = names.stream().map(name -> getGenderForName(name).block()).toList();
    long end = System.currentTimeMillis();
    System.out.println("Time for six external requests, BLOCKING: "+ (end-start));
  }

  public void getGendersNonBlocking() {
    long start = System.currentTimeMillis();
    var genders = names.stream().map(name -> getGenderForName(name)).toList();
    Flux<Gender> flux = Flux.merge(Flux.concat(genders));
    List<Gender> res = flux.collectList().block();
    long end = System.currentTimeMillis();
    System.out.println("Time for six external requests, NON-BLOCKING: "+ (end-start));
  }


  // This is a method that the program has to implement because of the CommandLineRunner interface.
  @Override
  public void run(String... args) throws Exception {
    //System.out.println("1: "+callSlowEndpoint().toString());

    //String randomStr = callSlowEndpoint().block();
    //System.out.println("2: " + randomStr);

    //callSlowEndpointBlocking();
    //callSlowEndpointNonBlocking();
    //System.out.println(getGenderForName("Ferhat").block().getGender());

    //getGendersBlocking();
    //getGendersNonBlocking();


  }
}
