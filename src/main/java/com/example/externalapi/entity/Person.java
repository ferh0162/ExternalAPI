package com.example.externalapi.entity;

import com.example.externalapi.dtos.CountryDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Person {
  @Id
  private String name;
  String gender;
  int genderCount;
  double genderProbability;
  private int age;
  private int ageCount;
  private String countryName;
  private double countryProbability;

  public Person(String name, String gender, int genderCount, double genderProbability, int age, int ageCount, String countryName, double countryProbability) {
    this.name = name;
    this.gender = gender;
    this.genderCount = genderCount;
    this.genderProbability = genderProbability;
    this.age = age;
    this.ageCount = ageCount;
    this.countryName = countryName;
    this.countryProbability = countryProbability;
  }
}

