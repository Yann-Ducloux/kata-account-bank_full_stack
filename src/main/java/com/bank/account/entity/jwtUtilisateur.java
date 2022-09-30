package com.bank.account.entity;

import java.io.Serializable;

public class jwtUtilisateur implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public jwtUtilisateur(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}