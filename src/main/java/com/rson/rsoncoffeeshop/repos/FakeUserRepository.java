package com.rson.rsoncoffeeshop.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rson.rsoncoffeeshop.models.FakeUser;

@Repository
public interface FakeUserRepository extends CrudRepository<FakeUser, Long>{
	FakeUser findByUsername(String username);
}
