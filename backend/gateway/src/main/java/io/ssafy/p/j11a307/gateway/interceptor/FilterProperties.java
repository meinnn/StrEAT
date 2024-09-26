package io.ssafy.p.j11a307.gateway.interceptor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "filter")
public class FilterProperties {

    private List<String> includePaths;
    private List<String> excludePaths;
}
