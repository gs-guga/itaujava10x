package dev.java10x.itaujava10x.Config;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "estatistica")
public record EstatisticaConfig(@NotNull
                                @Positive
                                Integer segundos) {

}
