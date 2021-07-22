package br.com.orange.proposta.proposta.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/proposta").hasAuthority("USER")
				.antMatchers(HttpMethod.GET, "/api/proposta/**").hasAuthority("USER")
				.antMatchers(HttpMethod.GET, "/actuator/health").hasAuthority("INFRA")
				.antMatchers(HttpMethod.POST, "/api/biometria/**").hasAuthority("USER")
				.antMatchers(HttpMethod.POST, "/api/cartao/**").hasAuthority("USER")
				.anyRequest().authenticated()
				.and().oauth2ResourceServer().jwt()
				.jwtAuthenticationConverter(getJwtAuthenticationConverter());
	}

	//configuração do claimName definido no keycloak e removendo o prefix default do JWT
	private JwtAuthenticationConverter getJwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
		converter.setAuthoritiesClaimName("authorities");
		converter.setAuthorityPrefix("");
		JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
		authenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
		return authenticationConverter;
	}

}
