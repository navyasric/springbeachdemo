package io.pivotal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;

//@Data

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String type;
    private Value value;

   public String getType() {
        return type;
    }

    public Value getValue() {
        return value;
    }


}
