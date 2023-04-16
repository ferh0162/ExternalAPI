package com.example.externalapi.dtos;

import com.example.externalapi.entity.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CombinedResponse {

 private String name;
 String gender;
 int genderCount;
 double genderProbability;
 private int age;
 private int ageCount;
 private String countryName;
 private double countryProbability;

  public CombinedResponse(Gender t1, Age t2, Country t3) {
   name = t1.getName();
  gender = t1.getGender();
  genderProbability = t1.getProbability();
  genderCount = t1.getCount();
  age = t2.getAge();
  ageCount = t2.getCount();
  countryName = t3.getCountry().get(0).getCountry_id();
  countryProbability = t3.getCountry().get(0).getProbability();
  }
 public CombinedResponse(Person person) {
  this.name = person.getName();
  this.gender = person.getGender();
  this.genderCount = person.getGenderCount();
  this.genderProbability = person.getGenderProbability();
  this.age = person.getAge();
  this.ageCount = person.getAgeCount();
  this.countryName = person.getCountryName();
  this.countryProbability = person.getCountryProbability();
 }
}
