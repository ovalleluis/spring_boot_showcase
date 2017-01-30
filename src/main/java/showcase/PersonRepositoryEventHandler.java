package showcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static showcase.WebSocketConfiguration.MESSAGE_PREFIX;

/**
 * Created by luis.ovalle on 26/01/2017.
 */
@Component
@RepositoryEventHandler(Person.class)
public class PersonRepositoryEventHandler {

    private final RabbitTemplate rabbitTemplate;

    private final SimpMessagingTemplate websocket;

    private final EntityLinks entityLinks;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PersonRepositoryEventHandler(SimpMessagingTemplate websocket,
                                        EntityLinks entityLinks, RabbitTemplate rabbitTemplate) {
        this.websocket = websocket;
        this.entityLinks = entityLinks;
        this.rabbitTemplate = rabbitTemplate;
    }

    @HandleAfterCreate
    public void newPerson(Person person) {
        log.info( "Created Person:" + person);
        this.websocket.convertAndSend(
                MESSAGE_PREFIX + "/users", getPath(person));
        rabbitTemplate.convertAndSend("users", person.getId());
    }



    /**
     * Take an {@link Person} and get the URI using
     * Spring Data REST's {@link EntityLinks}.
     *
     * @param  person
     */
    private String getPath(Person person) {
        return this.entityLinks.linkForSingleResource(person.getClass(),
                person.getId()).toUri().getPath();
    }
}
