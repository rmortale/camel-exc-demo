package ch.dulce;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java8 DSL Router
 * kamel run --dev --property-file src/main/resources/application.properties src/main/java/ch/dulce/JmsComponentProps.java
 */
public class JmsComponentProps extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("jms:inqueue").throwException(IllegalArgumentException.class, "bad message");

    }


    
}
