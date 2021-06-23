package com.backend.configuration.security;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.backend.entities.Admin;
import com.backend.entities.Agence;
import com.backend.entities.Agent;
import com.backend.entities.Client;
import com.backend.exceptions.NotFoundException;
import com.backend.services.AdminService;
import com.backend.services.AgenceService;
import com.backend.services.AgentService;
import com.backend.services.ClientService;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	AgentService agentService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	AgenceService agenceService;
	
	UserPrincipalDetailsService service;
	
	@Autowired
	public AppSecurityConfig(UserPrincipalDetailsService service) {

		this.service = service;
	}
	
	@PostConstruct
	public void init() {
		
		
//		   Agence agence = new Agence();
//	       agence.setNom("massira");
//	       agence.setCreationAdmin(admin);
//	       agenceService.addAgence(agence);
//
//       Agent agent = new Agent();
//       agent.setUsername("agent");
//       agent.setPassword("agent");
//       agent.setAgence(agence);
//       agentService.addAgent(agent);
//
//
//       Client c = new Client();
//        c.setUsername("client");
//        c.setPassword("client");
//        c.setAgence(agence);
//        c.setCreationAgent(agent);
//        
//        clientService.addClient(c);
        
		List<Admin>  currentAdminList= new ArrayList<Admin>();
		try {
		currentAdminList = adminService.getAdmins(null);
		} catch (NotFoundException e) {
		
	        
			Admin    admin    = new Admin();
	        admin.setUsername("admin");
	        admin.setPassword("admin");
	        admin.setEmail("ensa.backend@gmail.com");
	        admin.setRole("Admin");
	        adminService.addAdmin(admin);
	     
	        
		}

	    
	}

	@Bean
	public DaoAuthenticationProvider autProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(service);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		
		return provider;
	}
	
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(ImmutableList.of("*"));
		configuration.setAllowedMethods(ImmutableList.of("HEAD",
				"GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.setAllowedHeaders(ImmutableList.of("accept",
				"accept-encoding",
				"authorization",
				"content-type",
				"dnt",
				"origin",
				"user-agent",
				"x-csrftoken",
				"x-requested-with"));
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.cors()
			.and()
			.authorizeRequests()
			//ADMIN
			.antMatchers(HttpMethod.GET,"/admins").permitAll()		//afficher les admins
			.antMatchers(HttpMethod.GET,"/admin/username/{username}").hasRole("Admin")		//admin par username
			.antMatchers(HttpMethod.POST,"/admins").hasRole("Admin")		//creer les admins
			.antMatchers(HttpMethod.PUT,"/admin/{id}").hasRole("Admin")	//modifier un admin
			.antMatchers(HttpMethod.DELETE,"/admin/{id}").hasRole("Admin")	//supprimer un admin
			
			//AGENT
			.antMatchers(HttpMethod.POST,"/agents").hasRole("Admin")		//creer agent
			.antMatchers(HttpMethod.PUT,"/agent/{id}").hasRole("Admin")	//modifier agent
			.antMatchers(HttpMethod.DELETE,"/agent/{id}").hasRole("Admin")	//supprimer agent
			.antMatchers(HttpMethod.GET,"/agence/{id}/agents").hasRole("Admin")	//afficher agents
			.antMatchers(HttpMethod.GET,"/agent/username/{username}").hasAnyRole("Admin","Agent")		//agent par username
			.antMatchers(HttpMethod.GET,"/agents").hasRole("Admin")	//afficher agent
			.antMatchers(HttpMethod.GET,"/agent/{id}/appointments").hasRole("Agent")	//afficher appointment agent

			//AGENCE
			.antMatchers(HttpMethod.GET,"/agences").hasAnyRole("Admin","Agent")		//afficher agences
			.antMatchers(HttpMethod.POST,"/agences").hasRole("Admin")		//creer agences
			.antMatchers(HttpMethod.PUT,"/agence/{id}").hasRole("Admin")	//modifier agence
			.antMatchers(HttpMethod.DELETE,"/agence/{id}").hasRole("Admin")	//supprimer agence
		
			//DEVISE
			.antMatchers(HttpMethod.GET,"/devises").hasAnyRole("Admin","Agent","Client")		//afficher devises
			.antMatchers(HttpMethod.POST,"/devises").hasRole("Admin")		//creer devises
			.antMatchers(HttpMethod.PUT,"/devise/{code}").hasRole("Admin")	//modifier devise
			.antMatchers(HttpMethod.DELETE,"/devise/{code}").hasRole("Admin")	//supprimer devise

			//CLIENT
			.antMatchers(HttpMethod.POST,"/clients").hasRole("Agent")	//creer client
			.antMatchers(HttpMethod.GET,"/clients").hasRole("Agent")	//afficher client
			.antMatchers(HttpMethod.GET,"/client/username/{username}").hasAnyRole("Agent","Client")		//client par username
			.antMatchers(HttpMethod.GET,"/agence/{id}/clients").hasRole("Agent")	//afficher clients
			.antMatchers(HttpMethod.PUT,"/client/{id}").hasRole("Agent")	//modifier client
			.antMatchers(HttpMethod.DELETE,"/client/{id}").hasRole("Agent")	//supprimer client
			
			.antMatchers(HttpMethod.GET,"/client/{id}/appointments").hasRole("Client")		//afficher client
			
			//BENEF
			.antMatchers(HttpMethod.GET,"/client/{id}/benef").hasAnyRole("Agent","Client")	// afficher les benef du client id
			.antMatchers(HttpMethod.POST,"/beneficiaire").hasRole("Client")	
			.antMatchers(HttpMethod.GET,"/beneficiaire/{id}").hasRole("Client")	
			.antMatchers(HttpMethod.DELETE,"/beneficiaire/{id}").hasRole("Client")	
			//COMPTE
			.antMatchers(HttpMethod.GET,"/client/{id}/comptes").hasAnyRole("Agent","Client")	//afficher comptes
			.antMatchers(HttpMethod.GET,"/comptes/all").hasAnyRole("Agent","Client")	//afficher compte
			.antMatchers(HttpMethod.GET,"/comptes").hasAnyRole("Agent","Client")	//afficher compte
			.antMatchers(HttpMethod.GET,"/compte/prop/{prop}").hasAnyRole("Agent","Client")
			.antMatchers(HttpMethod.GET,"/compte/agent/{agent}").hasAnyRole("Agent","Client")

			.antMatchers(HttpMethod.POST,"/comptes").hasRole("Agent")	//creer compte
			.antMatchers(HttpMethod.PUT,"/compte/{id}").hasRole("Agent")	//modifier compte
			.antMatchers(HttpMethod.DELETE,"/compte/{id}").hasRole("Agent")//supprimer compte
			.antMatchers(HttpMethod.GET,"/compte/{numero}").hasRole("Client")	//afficher compte
			
			//VIREMENT ET RECHARGE
			.antMatchers(HttpMethod.GET,"/compte/{id}/virements").hasRole("Client")	//afficher virements
			.antMatchers(HttpMethod.GET,"/compte/{id}/virementsEnvoyes").hasRole("Client")//afficher virement envoyes
			.antMatchers(HttpMethod.GET,"/compte/{id}/virementsRecus").hasRole("Client")//afficher virement recus
			.antMatchers(HttpMethod.GET,"/compte/{id}/recharges").hasRole("Client")	//afficher recharges
			.antMatchers(HttpMethod.GET,"/virements").hasRole("Client")	//afficher virement
			.antMatchers(HttpMethod.POST,"/virements").hasRole("Client")//creer virement

			//OPERATIONS (RETRAIT ET VERSEMENT)
			.antMatchers(HttpMethod.GET,"/operations").hasAnyRole("Agent","Client")	//afficher operation
			.antMatchers(HttpMethod.GET,"/compte/{id}/operations").hasAnyRole("Agent","Client")	//afficher operations
			.antMatchers(HttpMethod.POST,"/operations").hasRole("Agent")	//creer operation

			//APPOINTMENTS
		//	.antMatchers(HttpMethod.GET,"/appointments").hasAnyRole("Agent","Client")	//afficher appointment
			.antMatchers(HttpMethod.POST,"/addAppointment").hasRole("Client")	//creer appointment
			.antMatchers(HttpMethod.PUT,"/appointment/{id}").hasRole("Agent")	//modifier appointment
			.antMatchers(HttpMethod.DELETE,"/appointment/{id}").hasRole("Agent")	//supprimer appointment

			

			//VIREMENT MULTIPLE
			.antMatchers(HttpMethod.GET,"/virement/multiple/client/{id}").hasRole("Client")	//afficher virement par client
			.antMatchers(HttpMethod.GET,"/virement/multiple").hasRole("Client")	//afficher virements
			.antMatchers(HttpMethod.GET,"/virement/multiple/{id}").hasRole("Client") //afficher virement specifique
			.antMatchers(HttpMethod.POST,"/virement/multiple").hasRole("Client")	//poster virements
			
			
			.and()
			.httpBasic()
			.and()
			.csrf().disable()
			;
			
		
		
		super.configure(http);
	}
	
	
	

}
