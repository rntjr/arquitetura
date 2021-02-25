package com.eterna.server.core.security.configuration;

import com.eterna.server.core.security.implementations.UserDetailsServiceImpl;
import com.eterna.server.core.security.jwt.JwtAuthenticationFilter;
import com.eterna.server.core.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("userDetailsService")
  private UserDetailsServiceImpl userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    AuthenticationManager authManager = authenticationManager();

    http
      .cors().and()
      .csrf().disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, "/api/auth/acessar").permitAll()
      .antMatchers(HttpMethod.POST, "/api/auth/cadastrar").permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(new JwtAuthenticationFilter(authManager))
      .addFilter(new JwtAuthorizationFilter(authManager, userDetailsService))
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .httpBasic();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    auth.userDetailsService(userDetailsService).passwordEncoder(bcrypt);
    //auth.inMemoryAuthentication().withUser("user").password(bcrypt.encode("user")).roles("user");
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
