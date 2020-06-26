package be.albatroz.javacase.infrastructure.util;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAware implements org.springframework.data.domain.AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }

    public String getCurrentUser() {
        return getCurrentAuditor().orElse("UnAuthorized");
    }

}