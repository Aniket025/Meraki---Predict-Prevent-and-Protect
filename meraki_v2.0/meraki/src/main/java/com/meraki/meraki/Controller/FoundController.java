package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.People_found;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/found")
public class FoundController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int getPeople_foundInfo(@RequestBody People_found found) {
        return ( new People_found().saveFoundInfo(found,null));
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public People_found getPeople_found (@RequestBody int id ){
        return ( new People_found()).getSingleInfo(id,null);
    }

    @RequestMapping( method = RequestMethod.GET )
    public Collection<People_found> getAllPeople_found (){
        return ( new People_found()).getAll(null);
    }

}
