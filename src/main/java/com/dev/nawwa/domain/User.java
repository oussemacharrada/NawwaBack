package com.dev.nawwa.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity()
@Table(name="sp_user_info")
public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sp_user_seq")
	@SequenceGenerator(name="sp_user_seq", sequenceName = "sp_user_seq", allocationSize=1)
	private Long id;

	private String userId;

	private String userName;
	private String password;
	private String email;
	private String profileImageUrl;
	private Date lastLoginDate;
	private Date lastLoginDateDisplay;
	private Date joinDate;
	private String role;
	private String[] authorities;
	private boolean active;
	private boolean isNotLocked;
	@JsonManagedReference
	@OneToOne(cascade = CascadeType.REMOVE,
			mappedBy = "account", fetch = FetchType.LAZY)
	private UserProfile userProfile;
	@JsonManagedReference
	@OneToOne(cascade = CascadeType.REMOVE,
			mappedBy = "account", fetch = FetchType.LAZY)
	private ServiceProviderProfile serviceProviderProfile;


	public User()
	{
	}

	

	public User(Long id, String userId, String userName, String password,
	      String email, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate,
	      String role, String[] authorities, boolean active, boolean isNotLocked)
	{
		super();
		this.id = id;
		this.userId = userId;

		this.userName = userName;
		this.password = password;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.lastLoginDate = lastLoginDate;
		this.lastLoginDateDisplay = lastLoginDateDisplay;
		this.joinDate = joinDate;
		this.role = role;
		this.authorities = authorities;
		this.active = active;
		this.isNotLocked = isNotLocked;
	}



	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}



	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getProfileImageUrl()
	{
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl)
	{
		this.profileImageUrl = profileImageUrl;
	}

	public Date getLastLoginDate()
	{
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastLoginDateDisplay()
	{
		return lastLoginDateDisplay;
	}

	public void setLastLoginDateDisplay(Date lastLoginDateDisplay)
	{
		this.lastLoginDateDisplay = lastLoginDateDisplay;
	}

	public Date getJoinDate()
	{
		return joinDate;
	}

	public void setJoinDate(Date joinDate)
	{
		this.joinDate = joinDate;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String[] getAuthorities()
	{
		return authorities;
	}

	public void setAuthorities(String[] authorities)
	{
		this.authorities = authorities;
	}

	public boolean active()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public boolean isNotLocked()
	{
		return isNotLocked;
	}

	public void setNotLocked(boolean isNotLocked)
	{
		this.isNotLocked = isNotLocked;
	}

}
