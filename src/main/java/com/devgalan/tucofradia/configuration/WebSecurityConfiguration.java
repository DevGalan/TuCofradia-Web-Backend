package com.devgalan.tucofradia.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final DataSource dataSource;
    
    public WebSecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
            .csrf(csrf -> csrf.disable())
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/api/**").permitAll() // auth/** TODO: Cambiar
				.anyRequest().authenticated()
			)
			.formLogin(Customizer.withDefaults())
			.logout((logout) -> logout.permitAll());

		return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user =
            User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

            return new InMemoryUserDetailsManager(user);
        }

        // @Bean
        // public UserDetailsService userDetailsService() {
        //     var userDetailsService = new JdbcUserDetailsManager();
        //     userDetailsService.setDataSource(dataSource);
        //     UserDetails user1 = User.builder()
        //             .username("user")
        //             .password(passwordEncoder().encode("user"))
        //             .authorities("read")
        //             .build();
        //     UserDetails user2 = User.builder()
        //             .username("admin")
        //             .password(passwordEncoder().encode("admin"))
        //             .authorities("read")
        //             .build();

        //     userDetailsService.createUser(user1);
        //     userDetailsService.createUser(user2);
        //     return userDetailsService;
        // }

    }
