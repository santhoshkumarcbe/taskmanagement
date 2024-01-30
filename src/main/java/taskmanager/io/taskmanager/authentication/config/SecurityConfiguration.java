package taskmanager.io.taskmanager.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import taskmanager.io.taskmanager.authentication.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf
                                .disable())
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("user/register",
                                                "user/getbyid/**",
                                                "user/getall",
                                                "user/getallbyrole/manager",
                                                "user/getallbyrole/admin",
                                                "user/getbymobilenumber/**",
                                                "user/delete/**"
                                                )
                                                .hasAnyAuthority("admin")

                                                .requestMatchers("user/getallbyrole/contributor",
                                                "user/getbyusername/**",
                                                "user/getbyemail/**",
                                                "task/post",
                                                "task/getall",
                                                "task/findbytitle/**",
                                                "task/update/**",
                                                "task/delete/**",
                                                "assignment/publish",
                                                "assignment/getall",
                                                "assignment/getallbytaskid/**",
                                                "asssignment/delete/**"
                                                )
                                                .hasAnyAuthority("manager","admin")

                                                .requestMatchers(
                                                "assignment/getbyid/**",
                                                "assignment/update-status",
                                                "chat/post",
                                                "chat/getallchats",
                                                "chat/delete/**"
                                                )
                                                .hasAnyAuthority("contributor","manager","admin")
                                                .anyRequest().authenticated()
                                                )

                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
