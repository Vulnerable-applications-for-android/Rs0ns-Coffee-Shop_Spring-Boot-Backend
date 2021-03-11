package com.rson.rsoncoffeeshop.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rson.rsoncoffeeshop.models.FakeUser;
import com.rson.rsoncoffeeshop.models.GiftCard;
import com.rson.rsoncoffeeshop.services.FakeUserService;
import com.rson.rsoncoffeeshop.services.GiftCardService;

@RestController
public class ApiController {
	
	private final FakeUserService fakeUserService;
	private final GiftCardService giftCardService;
	
	public ApiController(FakeUserService fakeUserService, GiftCardService giftCardService) {
		this.fakeUserService = fakeUserService;
		this.giftCardService = giftCardService;
	}
	
	@PostMapping(value="/api/v2/register", consumes="application/json", produces="application/json")
	public String fakeUserRegister(@RequestBody FakeUser fakeUser) {
		String username = fakeUser.getUsername();
		String password = fakeUser.getPassword();
		if (username.length() < 6 || password.length() < 1) {
			return "FAILURE";
		}
		FakeUser newFakeUser = fakeUserService.findFakeUserByUsername(username);
		if (newFakeUser != null) {
			return "FAILURE";
		}
		fakeUserService.registerFakeUser(username, password);
		return "SUCCESS";
	}
	
	@PostMapping(value="/api/v3/login", consumes="application/json", produces="application/json")
	public String fakeUserLogin(@RequestBody FakeUser fakeUser) {
		String username = fakeUser.getUsername();
    	String password = fakeUser.getPassword();
    	if (username.equals("AdminKing6548") && password.equals("admin")) {
			return "AdminKing6548,359,[FLAG - 1]";
		}
    	if (username.equals("adm") && password.equals("basketball")) {
    		return "adm,753,[FLAG - 2]";
    	}
    	boolean authenticated = fakeUserService.authenticateFakeUser(username, password);
    	if (authenticated) {
    		FakeUser thisUser = fakeUserService.findFakeUserByUsername(fakeUser.getUsername());
    		String thisUsername = thisUser.getUsername();
    		Long thisUserId = thisUser.getId();
    		return thisUsername + "," + thisUserId.toString() + "," + "[FLAG - 3]";
    	} else {
    		return "INVALID,-1";
    	}
	}
	
	@RequestMapping(value="/api/v1/account/user")
	public String secretEndpoint() {
		return "{\"username\":\"AdminKing6548\",\"password\":\"21232f297a57a5a743894a0e4a801fc3\"}";
	}
	
	@PostMapping(value="/api/v3/account/user", consumes="application/json", produces="application/json")
	public FakeUser getLoggedUser(@RequestBody FakeUser fakeUser) {
		FakeUser loggedUser = fakeUserService.findFakeUserById(fakeUser.getId());
		if (loggedUser == null) {
			FakeUser newUser = new FakeUser();
			loggedUser = newUser;
		}
		if (fakeUser.getId() == 753) {
			loggedUser.setUsername("adm");
			loggedUser.setPassword("[FLAG 5]");
		} else if (fakeUser.getId() == 359) {
			loggedUser.setUsername("AdminKing6548");
			loggedUser.setPassword("Wouldn't you like to know...");
		} else {
			loggedUser.setPassword("Wouldn't you like to know...");
		}
		return loggedUser;
	}
	
	
	@PostMapping(value="/api/v3/logout", consumes="application/json", produces="application/json")
	public FakeUser fakeUserLogout(@RequestBody FakeUser fakeUser) {
		FakeUser thisFakeUser = fakeUserService.findFakeUserByUsername(fakeUser.getUsername());
		return fakeUserService.fakeUserLogout(thisFakeUser);
	}
	
	@PostMapping(value="/api/v3/giftcard/add", consumes="application/json", produces="application/json")
	public String addGiftCard(@RequestBody GiftCard giftCard) {
		if (giftCard.getCardNumber() == 8465 && giftCard.getSecurityCode() == 941) {
			System.out.print(giftCard.getUserId());
			Long giftCardOwnerId = giftCard.getUserId();
			FakeUser giftCardOwner = fakeUserService.findFakeUserById(giftCardOwnerId);
			giftCard.setOwner(giftCardOwner);
			if (giftCard.getValue() == null) {
				giftCard.setValue(5);
			}
			giftCardService.createGiftCard(giftCard);
			return "[FLAG - 7]";
		} else {
			return "FAILURE";
		}
	}
	
	@PostMapping(value="/api/v3/giftcards", consumes="application/json", produces="application/json")
	public List<GiftCard> getGiftCards(@RequestBody FakeUser fakeUser){
		if (fakeUser.getId() == 78) {
			GiftCard newGiftCard = new GiftCard();
			newGiftCard.setValue(9999);
			fakeUser.setUsername("JohnRipper");
			fakeUser.setPassword("[$2a$04$Ayc.Ej3xfKrZMVu.QEsNiOmH0nbI00wKvD/W8cai6eShSmBfSfQFa]");
			newGiftCard.setOwner(fakeUser);
			List<GiftCard> l = new ArrayList<GiftCard>();
			l.add(newGiftCard);
			return l;
		}
		Long userId = fakeUser.getId();
		FakeUser thisUser = fakeUserService.findFakeUserById(userId);
		if (thisUser == null) {
			return null;
		}
		List<GiftCard> userCards = thisUser.getGiftCards();
		for (GiftCard card : userCards) {
			FakeUser user = card.getOwner();
			user.setPassword("Wouldn't you like to know...");
		}
		return userCards;
	}
}
