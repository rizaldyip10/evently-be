package com.pwdk.minpro_be;

import com.pwdk.minpro_be.auth.config.RsaKeyConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties (RsaKeyConfigProperties.class)
@Log
public class MinproBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinproBeApplication.class, args);
	}

}
