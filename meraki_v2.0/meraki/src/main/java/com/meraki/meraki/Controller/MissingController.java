package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.Missing;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/missing")
public class MissingController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int getMissingInfo(@RequestBody Missing missing) {
        return ( new Missing().saveMissingInfo(missing,null));
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Missing getMissingInfo (@RequestBody int id ){
        return ( new Missing()).getSingleInfo(id,null);
    }

    @RequestMapping( method = RequestMethod.GET )
    public Collection<Missing> getAllPeople_missing (){
        return ( new Missing().getAll(null));
    }

}
