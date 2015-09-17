package io.pivotal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  \class Class that serves /helloworld.. endpoints
 *  \TODO add a path parameter for fun  , as in /helloworld/dirk
 *
 */
@Controller
public class HelloController {

    /**  Serves 'helloworld' with one optional parameter
     *
     * @param name  Name to insert into template
     * @param model  Autoinjected
     * @return  Thymeleaf template
     */
    @RequestMapping("/helloworld")
    public String helloworld(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "hellotemplate";
    }

    /**
     * Serves 'helloworld2'
     * @return hardcoded string
     */
    @RequestMapping("/helloworld2")
    public @ResponseBody String helloworld2() {
        return "<html><h1>Hello world</h1><p>Hardcoded without a template</p></html>";
    }
}

/*
this will give you a really unhelpful error or a circular reference problem UNLESS
you include thymeleaf on the classpath:
- There was an unexpected error (type=Not Found, status=404).
- javax.servlet.ServletException: Circular view path

to add it to classpath, modify build.gradle to have this dependency:
compile("org.springframework.boot:spring-boot-starter-thymeleaf")

If you want to test this and actually return a raw string as a html page, you have to
add @ResponseBody , like so public @ResponseBody String helloworld(@RequestParam(...
this will disable the automatic looking for a view/template engine.

In the original GreetingController, we used @RestController, which makes all methods of
this class have @RespoonseBody answers by default.  Then additional magic converts any
object that is not a string into a JSON representation of that object.

-------------
All of this is explained in this SO post
http://stackoverflow.com/questions/25330084/spring-simply-rendering-an-html-page

Spring Boot will automatically use and configure Thymeleaf as the view rendering engine,
as long as it's on the classpath. To put it on the classpath use

compile("org.springframework.boot:spring-boot-starter-thymeleaf")

in the gradle build file (using the relevant maven dependency is straightforward).

[..]
For a complete example, check out this guide.  ()
 */