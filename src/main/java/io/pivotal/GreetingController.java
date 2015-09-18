package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/*

NOTE NOTE NOTE
with the inclusion of angular/bootstrap, these endpoints have stopped working.

*/




/**
 * Our main testing class, servers all kinds of greetings
 */
@RestController
public class GreetingController {

    private static final String template = "Hi from Dirk and Navya, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    LookupService lookupService;

    /**
     * Simplest possible case: return an empty greeting object (filled with default values, see Greeting)
     * @param name not used
     * @return Greeting object which is converted to JSON
     * \TODO remove spurious arguments
     *
     */
    @RequestMapping("/simplegreeting")
    public Greeting simplegreeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting();
    }

    /**
     * Original Greeting page, with a quote lookup (see Greeting).  When constructing the Greeting, the
     * LookupService will be contacted and this will go to a website.
     * @param name
     * @return Greeting object which is converted to JSON
     */
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name),
                true);
    }

    /**
     * Look up a quote from the internet (this will take a while) and return its inner value object
     *  @return Value object which is converted to JSON
     */
    @RequestMapping("/quote")
    public Value quote() {
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
//        log.info(quote.toString());
        return quote.getValue();
    }

    /**
     * Look up a quote from the internet using the asynchronous service.  It will create a Future object and
     * then poll this object for the async service to complete.  A real life example would first render (part of) the
     * page of course.
     *
     * @return a Greeting
     * @throws Exception
     *
     * \TODO should we write a jquery polling solution for this, which contacts the server to see whether it
     * completed?
     *
     */
    @RequestMapping("/slowgreeting")
    public Greeting slow() throws Exception {
        Integer asyncloopcounter = 0;
        Future<Greeting> greeting = lookupService.find(counter.incrementAndGet(), "slow");
        while(!greeting.isDone()){
            asyncloopcounter++;
            Thread.sleep(100);
            System.out.println("waiting");  // make sure this gets printed, if not your spring is not truly async
            // in more detail, if @enableasync is not given to the main method of Application, this will still work
            // but it will not be asynchronous (good fallback there).
        }
        if (asyncloopcounter==0) {
            System.out.println("Async does not seem to be working, or service is too fast");
        }
        return greeting.get();
    }
}
