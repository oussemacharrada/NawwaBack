package com.dev.nawwa.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService
{
	public static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
	public static final int INCREMENT_ATTEMPT = 1;
	public LoadingCache<String, Integer> loginAttemptCache;

	/**
	 * This returns the Cache which holds user login attempts. like. 
	 * -----------Cache-------------------------
	 * 		User 			LoginAttempt
	 * ----------------------------------------- 
	 * 		user 1 				1 
	 * 		user 2 				3
	 * -----------------------------------------
	 */
	public LoginAttemptService()
	{
		super();
		// removes user cache data after 15 minutes and handle 100 user entries at a time
		loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).maximumSize(100)
		      .build(new CacheLoader<String, Integer>()
		      {
			      @Override
			      public Integer load(String key) throws Exception
			      {
				      return 0;
			      }
		      });
	}
	
	/** remove user attempts data from cache */
	public void evictUserFromLoginAttemptCache(String username) {
		loginAttemptCache.invalidate(username);
	}
	
	/** add/increment user attempts in cache */
	public void addUserToLoginAttemptCache(String username)  {
		int attempts = 0;
		try
		{
		// gets the cache attempt number and adds with attempts value.
			attempts = INCREMENT_ATTEMPT + loginAttemptCache.get(username);
		}
		catch(ExecutionException e)
		{
			e.printStackTrace();
		}
		// update the attempt count in loginAttemptCache
		loginAttemptCache.put(username, attempts);
	}
	
	/** validates user attempts if not exceeding set value i.e 5 */
	public boolean hasExceededMaxAttempts(String username)  {
		try
		{
			return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
		}
		catch(ExecutionException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
}
