package com.example.externalapi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Gender {
  public int count;
  public String gender;
  public String name;
  public double probability;
}


