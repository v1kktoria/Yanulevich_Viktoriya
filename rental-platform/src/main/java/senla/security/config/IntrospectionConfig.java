package senla.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class IntrospectionConfig {

    @Bean
    public OpaqueTokenIntrospector introspector(
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}") String introspectionUri,
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}") String clientId,
            @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}") String clientSecret
    ) {
        OpaqueTokenIntrospector delegate = new NimbusOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret);

        return token -> {
            var principal = delegate.introspect(token);

            var roles = Optional.ofNullable((Map<?, ?>) principal.getAttribute("realm_access"))
                    .map(m -> (Collection<String>) m.get("roles"))
                    .orElse(Set.of());

            Collection<GrantedAuthority> authorities = roles.stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                    .collect(Collectors.toList());

            return new DefaultOAuth2AuthenticatedPrincipal(principal.getName(), principal.getAttributes(), authorities);
        };
    }

}
