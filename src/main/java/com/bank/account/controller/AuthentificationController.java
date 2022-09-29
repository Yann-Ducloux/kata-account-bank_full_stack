package com.bank.account.controller;

import com.bank.account.config.JwtTokenUtil;
import com.bank.account.dto.ConnectionDTO;
import com.bank.account.entity.JwtRequest;
import com.bank.account.entity.JwtResponse;
import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.exception.MailIsInvalidEception;
import com.bank.account.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
public class AuthentificationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;


	private AuthentificationService authentificationService;
	public AuthentificationController(AuthentificationService authentificationService) {
		this.authentificationService = authentificationService;
	}

	@PostMapping("/authentification")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getMail(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getMail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String mail, String password) throws Exception {
		Objects.requireNonNull(mail);
		Objects.requireNonNull(password);

		try {
			ConnectionDTO connectionDTO = new ConnectionDTO();
			connectionDTO.setMail(mail);
			connectionDTO.setPassword(password);
			if(this.authentificationService.connection(connectionDTO)){
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mail, password));
			}
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (MailIsInvalidEception e) {
			throw new Exception("INVALID_Mail", e);
		} catch (ClientPasswordFalseException e) {
			throw new Exception("INVALID_Password", e);
		}
	}
}
