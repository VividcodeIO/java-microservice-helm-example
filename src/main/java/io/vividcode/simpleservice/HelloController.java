package io.vividcode.simpleservice;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Value("${app.prefix:Hello}")
    private String prefix;

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", prefix + " World");
    }
}
