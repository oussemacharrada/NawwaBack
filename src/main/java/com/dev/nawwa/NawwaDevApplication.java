package com.dev.nawwa;

import com.dev.nawwa.constant.FileConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class}) /*Removes default whitelabel  spring error page*/
public class NawwaDevApplication {

	public static void main(String[] args) {
		SpringApplication.run(NawwaDevApplication.class, args);
		System.out.println(FileConstant.USER_FOLDER);
		new File(FileConstant.USER_FOLDER).mkdirs();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
