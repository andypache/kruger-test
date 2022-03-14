package ec.kruger.vaccination.config;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author andres.pacheco
 *
 * Properties for all application
 */
@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String resourcesId;
    private String admin;
    private String employee;
    private List<String> vaccines;

    @Tolerate ApplicationProperties(){
    }
}
