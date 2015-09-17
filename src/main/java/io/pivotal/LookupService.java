package io.pivotal;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class LookupService {

    RestTemplate restTemplate = new RestTemplate();

    /**
     * This function contacts the webserver for a quote, and pretends that it took 1 second to do so.
     * The quote is then inserted in a greeting object.
     *
     * @param id  The id of the greeting
     * @param name   Name of the greeting
     * @return Future<Greeting> asynchronous result
     * @throws InterruptedException
     *
     *      * \TODO we need a mocker for the lookupservice
     */
    @Async
    public Future<Greeting> find(Long id, String name) throws InterruptedException {
        System.out.println("Looking up " + name);
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        Greeting greeting = new Greeting(id, name, false);
        greeting.setQuote(quote.getValue().getQuote());
        Thread.sleep(1000L);
        return new AsyncResult<Greeting>(greeting);
    }

}