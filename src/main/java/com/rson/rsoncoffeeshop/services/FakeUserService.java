package com.rson.rsoncoffeeshop.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rson.rsoncoffeeshop.models.FakeUser;
import com.rson.rsoncoffeeshop.repos.FakeUserRepository;

@Service
public class FakeUserService {
	@Autowired
	private FakeUserRepository fakeUserRepository;
	
    public FakeUser registerFakeUser(String username, String password) {;
    	FakeUser user = new FakeUser();
    	user.setUsername(username);
    	user.setPassword(password);
    	String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
		user.setLoggedIn(true);
        return fakeUserRepository.save(user);
    }
	
    public boolean authenticateFakeUser(String username, String password) {
    	FakeUser user = fakeUserRepository.findByUsername(username);
    	if(user == null) {
    		return false;
    	} else {
    		if(BCrypt.checkpw(password, user.getPassword())) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    }
    
	public FakeUser findFakeUserById(Long id){
		Optional<FakeUser> optionalFakeUser = fakeUserRepository.findById(id);
		if(optionalFakeUser.isPresent()) {
			return optionalFakeUser.get();
		} else {
			return null;
		}
	}
	
	public FakeUser findFakeUserByUsername(String username) {
		return fakeUserRepository.findByUsername(username);
	}
	
	public FakeUser fakeUserLogout(FakeUser fakeUser) {
		fakeUser.setLoggedIn(false);
		return fakeUserRepository.save(fakeUser);
	}
	
}
