package ch.dulce;

import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java8 DSL Router
 * kamel run --dev --property-file src/main/resources/application.properties src/main/java/ch/dulce/ActivemqTransacted.java
 */
public class ActivemqTransacted extends RouteBuilder {

    @Override
    public void configure() {

        from("activemq:inqueue").throwException(IllegalArgumentException.class, "bad message");
                
    }

}
