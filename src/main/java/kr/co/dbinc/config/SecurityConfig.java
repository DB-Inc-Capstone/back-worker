package kr.co.dbinc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
					.cors(Customizer.withDefaults())
					.csrf().disable()
					.authorizeHttpRequests()
					//.antMatchers("/**").permitAll()
					.anyRequest().permitAll();
					
		return httpSecurity.build();
	}
}
