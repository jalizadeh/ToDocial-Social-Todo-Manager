package com.jalizadeh.springboot.web.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jalizadeh.springboot.web.model.PasswordResetToken;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.model.VerificationToken;
import com.jalizadeh.springboot.web.repository.VerificationTokenRepository;
import com.jalizadeh.springboot.web.repository.PasswordResetTokenRepository;
import com.jalizadeh.springboot.web.repository.UserRepository;

@Service
public class TokenService {

	public static final String TOKEN_INVALID = "invalid";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    
    public static final String TOKEN_TYPE_VERIFICATION = "VT";
    public static final String TOKEN_TYPE_PASSWORD_RESET = "PRT";
    
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private PasswordResetTokenRepository prtRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	
	public void createVerificationToken(User user, String token) {
		final VerificationToken myToken = 
				new VerificationToken(token, user, new Date());
        tokenRepository.save(myToken);
	}


	
	public VerificationToken getVerificationToken(String token) {
		return tokenRepository.findByToken(token);
	}


	//Supports both Verification & Password Reset tokens
	public String validateVerificationToken(String type, String token) {
		if(type.equals(TOKEN_TYPE_VERIFICATION)) {
			VerificationToken vt = tokenRepository.findByToken(token);
			if(vt == null) {
				return TOKEN_INVALID;
			}
			
			User user = vt.getUser();
			user.setMp(user.getPassword());
			
			Calendar cal = Calendar.getInstance();
			if(vt.getExpiryDate().getTime() 
					- cal.getTime().getTime() <= 0 ) {
				tokenRepository.delete(vt); //expired token must be deleted
				return TOKEN_EXPIRED;
			}
			
			user.setEnabled(true);
			tokenRepository.delete(vt); //valid token must be deleted
			userRepository.save(user);
			return TOKEN_VALID;
		
		} else { //PRT
			PasswordResetToken prt = prtRepository.findByToken(token);
			if(prt == null) {
				return TOKEN_INVALID;
			}
			
			User user = prt.getUser();
			user.setMp(user.getPassword());
			
			Calendar cal = Calendar.getInstance();
			if(prt.getExpiryDate().getTime() 
					- cal.getTime().getTime() <= 0 ) {
				prtRepository.delete(prt); //expired token must be deleted
				return TOKEN_EXPIRED;
			}
			
			//Changing password is a two-step function
			//1. here I should check if token is valid
			//2. ok, first SecurityContext must be filled with the user, then
			//   the token is removed in controller
			return TOKEN_VALID;

		}
	}
}
