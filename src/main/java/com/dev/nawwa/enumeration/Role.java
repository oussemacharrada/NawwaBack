package com.dev.nawwa.enumeration;

import com.dev.nawwa.constant.Authority;

public enum Role
{
	ROLE_USER(Authority.USER_AUTHORITES),
	ROLE_HR(Authority.HR_AUTHORITIES),
	ROLE_MANAGER(Authority.MANAGER_AUTHORITIES),
	ROLE_ADMIN(Authority.ADMIN_AUTHORITIES),
	ROLE_SUPER_ADMIN(Authority.SUPER_ADMIN_AUTHORITIES);
	
	private String[] authorities;
	
	Role(String...authorities){
		this.authorities = authorities;
	}
	
	public String[] getAuthorities() {
		return authorities;
	}
	
}
