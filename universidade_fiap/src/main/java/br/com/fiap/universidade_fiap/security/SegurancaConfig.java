package br.com.fiap.universidade_fiap.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SegurancaConfig {

	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(request -> request
				// Rotas públicas
				.requestMatchers("/login", "/usuario/novo", "/insere_usuario", "/css/**", "/js/**", "/img/**", "/error/**")
				.permitAll()
				// API de detecção - permitir acesso para usuários autenticados
				.requestMatchers("/api/detections/**").hasAnyRole("ADMIN", "GERENTE", "OPERADOR", "USER")
				// Rotas específicas por perfil
				.requestMatchers("/usuario/lista", "/usuario/excluir/**").hasRole("ADMIN")
				.requestMatchers("/dashboard", "/relatorios/**").hasAnyRole("ADMIN", "GERENTE")
				.requestMatchers("/motos/**", "/operacoes/**").hasAnyRole("ADMIN", "GERENTE", "OPERADOR")
				.requestMatchers("/acesso_negado").permitAll()
				// Todas as outras rotas requerem autenticação
				.anyRequest().authenticated())
				.formLogin(
						login -> login.loginPage("/login").usernameParameter("username").passwordParameter("password")
								.defaultSuccessUrl("/", true).failureUrl("/login?falha=true").permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll())
				.exceptionHandling(exception -> exception.accessDeniedHandler((request, response, ex) -> {
					response.sendRedirect("/acesso_negado");
				}))
				.csrf(csrf -> csrf.ignoringRequestMatchers("/insere_usuario", "/motos/salvar", "/motos/atualizar", "/motos/excluir/**", "/motos/status/salvar", "/motos/status/atualizar", "/usuario/excluir/**", "/logout", "/api/detections/**"));

		return http.build();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
