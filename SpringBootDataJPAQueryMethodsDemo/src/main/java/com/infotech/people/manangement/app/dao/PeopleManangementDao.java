package com.infotech.people.manangement.app.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.infotech.people.manangement.app.entities.Person;

public interface PeopleManangementDao extends Repository<Person, Integer>{
	List<Person> findByLastName(String lastName);
	List<Person> findByFirstNameAndEmail(String firstName,String email);
}
