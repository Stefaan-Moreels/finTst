package be.albatroz.javacase.infrastructure.util;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component("dateTimeProvider")
public class InstantProvider implements DateTimeProvider {
    @Override
    @NonNull
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(Instant.now());
    }
}