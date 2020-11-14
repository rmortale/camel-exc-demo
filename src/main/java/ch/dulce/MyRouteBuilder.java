package ch.dulce;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.BindToRegistry;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.jms.connection.JmsTransactionManager;

/**
 * A Camel Java8 DSL Router
 * kamel run --dev --property-file src/main/resources/application.properties src/main/java/ch/dulce/MyRouteBuilder.java
 */
public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        bindToRegistry("jmsConnectionFactory", cf);

        JmsTransactionManager tm = new JmsTransactionManager();
        tm.setConnectionFactory(cf);

        bindToRegistry("jmsTransactionManager", tm);
        
        ActiveMQComponent jms = ComponentsBuilderFactory.activemq().acceptMessagesWhileStopping(true)
        .transacted(true).transactionManager(tm).connectionFactory(cf).build();

        bindToRegistry("jms", jms);

        from("jms:inqueue")
        //.transacted()
        .process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
                log.info(exchange.getIn().toString());
                throw new Exception("error test");
            }
            
        }).log("${headers}");
    }

    // @BindToRegistry
    // public JmsComponent jms() {
    //     ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();

    //     JmsTransactionManager tm = new JmsTransactionManager();
    //     tm.setConnectionFactory(cf);

    //     bindToRegistry("jmsTransactionManager", tm);
    //     return ComponentsBuilderFactory.activemq().acceptMessagesWhileStopping(true)
    //     .transacted(true).transactionManager(tm).connectionFactory(cf).build();
    // }

}
