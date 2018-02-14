package gov.samhsa.ocp.ocpuiapi.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class SecurityConfig {

    private static final String RESOURCE_ID = "ocpUiApi";

    @Bean
    public ResourceServerConfigurer resourceServer(SecurityProperties securityProperties) {
        return new ResourceServerConfigurerAdapter() {
            @Override
            public void configure(ResourceServerSecurityConfigurer resources) {
                resources.resourceId(RESOURCE_ID);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                if (securityProperties.isRequireSsl()) {
                    http.requiresChannel().anyRequest().requiresSecure();
                }
                http.authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/ocp-fis/**").access("#oauth2.hasScope('ocpUiApi.read')")
                        .antMatchers(HttpMethod.PUT, "/ocp-fis/**").access("#oauth2.hasScope('ocpUiApi.write')")
                        .antMatchers(HttpMethod.POST, "/ocp-fis/**").access("#oauth2.hasScope('ocpUiApi.write')")
                        .antMatchers(HttpMethod.DELETE, "/ocp-fis/**").access("#oauth2.hasScope('ocpUiApi.write')")
                        .anyRequest().denyAll();
            }
        };
    }
}
