package com.rson.rsoncoffeeshop.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rson.rsoncoffeeshop.models.GiftCard;

@Repository
public interface GiftCardRepository extends CrudRepository<GiftCard, Long>{
	Iterable<GiftCard> findAll();
	GiftCard findByCardNumber(Integer cardNumber);
}
