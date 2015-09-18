package io.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


// The @EnableAsync annotation switches on Spring’s ability to run @Async methods in a background thread pool.
// as discussed in LookupService, things will work without this.

@SpringBootApplication
@EnableAsync
public class SpringbeachdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbeachdemoApplication.class, args);
    }


}
