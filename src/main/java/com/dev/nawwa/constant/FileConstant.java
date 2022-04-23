package com.dev.nawwa.constant;

public class FileConstant
{
	public static final String USER_IMAGE_PATH = "/user/image/";
	public static final String JPG_EXTENSION = "jpg";
	/** Syste.getProperty("user.home") will create folders at OS specific user's home location
	 * 	in Windows "c:/username/"
	 *  */
	public static final String USER_FOLDER = System.getProperty("user.home") + "/supportportal/user/";
	public static final String DIRECTORY_CREATED = "Created directory for: ";
	public static final String DEFAULT_USER_IMAGE_PATH = "/user/image/profile/";
	public static final String FILE_SAVED_IN_FILE_SYSTEM = "Saved file in file system by name: ";
	public static final String DOT = ".";
	public static final String FORWARD_SLASH = "/";
	public static final String TEMP_PROFILE_IMAGE_BASE_URL = "https://robohash.org/";
}
