package com.bank.account.service;

import com.bank.account.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private ClientRepository clientRepository;
	ModelMapper modelMapper = new ModelMapper();
	public JwtUserDetailsService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	/**
	 * Cette fonction permet de charger l'utilisateur
	 * @param mail
	 * @return l'utilisateur
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		if (clientRepository.existsByMail(mail)) {
			return new User(mail, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("Mail not found with Mail: " + mail);
		}
	}

}