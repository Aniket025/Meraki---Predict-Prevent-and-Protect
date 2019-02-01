package com.meraki.meraki.Controller;
import com.meraki.meraki.Model.FileUpload;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestBody FileUpload upload) {
        return ( new FileUpload().uploadPic(upload));
    }

}
