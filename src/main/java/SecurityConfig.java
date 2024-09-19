import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login", "/admin/home").permitAll() // Allow access to login and home page without authentication
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Require ADMIN role for admin routes
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login") // Specify the custom login page
                        .defaultSuccessUrl("/admin/upload", true) // Redirect to upload page after successful login
                        .permitAll() // Allow everyone to access the login page
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Logout URL
                        .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                        .permitAll() // Allow everyone to log out
                )
                // Keep CSRF disabled (consider enabling it in production)
                .csrf(csrf -> csrf.disable()); // Disable CSRF for file uploads (can be re-enabled if needed)

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Create an in-memory user with encoded password for security
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("adminpassword")) // Using encoded password
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder using BCrypt hashing
    }
}
