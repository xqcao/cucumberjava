package com.clinecode.onecline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinecode.onecline.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}