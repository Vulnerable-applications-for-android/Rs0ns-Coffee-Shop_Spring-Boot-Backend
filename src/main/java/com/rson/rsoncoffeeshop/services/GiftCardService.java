package com.rson.rsoncoffeeshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rson.rsoncoffeeshop.models.GiftCard;
import com.rson.rsoncoffeeshop.repos.GiftCardRepository;

@Service
public class GiftCardService {
	@Autowired
	private GiftCardRepository giftCardRepository;
	
	public List<GiftCard> allGiftCards(){
		return (List<GiftCard>) giftCardRepository.findAll();
	}

	public GiftCard createGiftCard(GiftCard giftCard){
		return giftCardRepository.save(giftCard);
	}

	public GiftCard findGiftCard(Long id){
		Optional<GiftCard> optionalGiftCard = giftCardRepository.findById(id);
		if(optionalGiftCard.isPresent()) {
			return optionalGiftCard.get();
		} else {
			return null;
		}
	}

}
