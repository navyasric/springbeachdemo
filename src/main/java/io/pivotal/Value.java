package io.pivotal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;

//@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {

    private Long id;
    private String quote;

    public String getQuote() {
        return quote;
    }

}
