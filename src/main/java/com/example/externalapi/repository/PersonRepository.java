package com.example.externalapi.repository;

import com.example.externalapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  Optional<Person> findPersonByName(String name);
  boolean existsByName(String name);

}
