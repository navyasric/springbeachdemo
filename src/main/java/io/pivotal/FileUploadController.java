package io.pivotal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by pivotal on 9/15/15.
 */

/** Class to show how to upload files
 *
 */
@Controller
public class FileUploadController {

    /**
     * Simple get method
     * @return string with explanation
     */
        @RequestMapping(value="/upload", method=RequestMethod.GET)
        public @ResponseBody String provideUploadInfo() {
            return "You can upload a file by posting to this same URL, or you can use the form at /uploadform.html";
        }

    /**
     * This is the part that does the work
     * @param name  Name of the file
     * @param file  Contents of file
     * @return Ugly confirmation page
     *
     * \TODO update confirmation page
     * \TODO store file in database instead
     */
        @RequestMapping(value="/upload", method=RequestMethod.POST)
        public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
                                                     @RequestParam("file") MultipartFile file){
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    name = "uploads/" + name;
                    BufferedOutputStream stream =
                            new BufferedOutputStream(new FileOutputStream(new File(name)));
                    stream.write(bytes);
                    stream.close();
                    return "You successfully uploaded " + name + "!";
                } catch (Exception e) {
                    return "You failed to upload " + name + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + name + " because the file was empty.";
            }
        }


}
