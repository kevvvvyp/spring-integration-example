package com.kevvvvyp.springintegrationexample.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

//import lombok.NoArgsConstructor;
//import javax.validation.Valid;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;

@Data
@Validated
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

//    private static final String MISSING_MSG = "Missing property";
//    private static final String OUT_OF_BOUNDS = "Property was out of bounds";
//
//    //TODO add any configurable app properties
//
//    @Valid
//    private final ApplicationProperties.Example example = new Example();
//
//    @Data
//    @NoArgsConstructor
//    public static class Example {
//
//        @NotNull(message = MISSING_MSG)
//        private Object someObject;
//
//        @Min(0)
//        @Max(value = Integer.MAX_VALUE, message = OUT_OF_BOUNDS)
//        private int someValue;
//    }
}



