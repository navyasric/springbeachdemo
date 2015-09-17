package io.pivotal;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;


/**
 * various attempts to render stuff simple classes and do validation, taken from
 *  http://spring.io/guides/gs/validating-form-input/
 *
 */
@Controller
public class PersonController extends WebMvcConfigurerAdapter {

    /**
     * Here we show how to use a template page without actually coding a controller.
     * This is mainly an exercise in futility but it is nice to know.
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/personresult").setViewName("personresulttemplate");
    }


    /**
     * Show an empty form to enter the data into. Clearly spring creates a new Person for us behind
     * the scenes, as these forms fields are empty but linked to Person in the model.
     * @param person
     * @return A template
     */
    @RequestMapping(value="/personform", method=RequestMethod.GET)
    public String showForm(Person person) {
        return "personform";
    }


    /**
     * Catch the post to the form.  If there are any errors, it will show you the form again with the errors
     * clearly marked (thanks to thymeleaf magic, see '${#fields.hasErrors}' ).
     * If successful, redirect to a confirmation page.
     *
     * @param person
     * @param bindingResult
     * @return A redirect
     */
    @RequestMapping(value="/personform", method=RequestMethod.POST)
    public String checkPersonInfo(@Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.print("Form submitted but it has errors\n");
            return "personform";
        }
        System.out.print("Form submitted, no errors found\n");
        // originally we redirected to a non template html file:
        //   return "redirect:/personresult-static.html"
        // now, we return to a template instead
        return "redirect:/personresult";
        // the point of using a redirect is that the URL on the browser changes.  We could have also just
        // included the template, but then the url would stay at  /personform, which is confusing.
    }

}