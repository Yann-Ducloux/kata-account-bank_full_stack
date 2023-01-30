package com.bank.account.controller;

import com.bank.account.config.JwtTokenUtil;
import com.bank.account.dto.ConnectionRequestDTO;
import com.bank.account.entity.InfoUtilisateurRequest;
import com.bank.account.entity.jwtUtilisateur;
import com.bank.account.service.AuthentificationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

	/**
	 * fonction qui récupére le jwt de l'utlisateur qui posséde un compte
	 * @param infoUtilisateur les info de l'utilisateur
	 * @return JWT le jwt de l'utilisateur
	 * @throws Exception
	 */
	@PostMapping("/authentification")
	@ApiOperation(value = "post a user", notes = "authentification a user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 404, message = "Not found - The user was not found")
	})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody InfoUtilisateurRequest infoUtilisateur)
			throws Exception {

		authenticate(infoUtilisateur.getMail(), infoUtilisateur.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(infoUtilisateur.getMail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new jwtUtilisateur(token));
	}

	/**
	 * fonction qui vérifie si l'utilisateur peut se connecter
	 * @param mail le mail
	 * @param password mot de passe
	 * @throws Exception
	 */

	private void authenticate(String mail, String password) throws Exception {
		Objects.requireNonNull(mail);
		Objects.requireNonNull(password);

		try {
			ConnectionRequestDTO connectionRequestDTO = new ConnectionRequestDTO();
			connectionRequestDTO.setMail(mail);
			connectionRequestDTO.setPassword(password);
			if(this.authentificationService.connection(connectionRequestDTO)){
				//TODO: pourquoi password fonctionne ?
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mail, "password"));
			}
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
