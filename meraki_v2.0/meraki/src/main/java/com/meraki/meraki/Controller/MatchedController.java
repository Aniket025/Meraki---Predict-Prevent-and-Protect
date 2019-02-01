package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.Matched;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/matched")
public class MatchedController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int saveMatched(@RequestBody int matched) {
        return ( new Matched().saveMatched(matched,null));
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public int updateNewMatched ( @RequestBody Matched matched ){
        return ( new Matched()).newMatched(matched,null);
    }

    @RequestMapping(value = "/1", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public int relativeIsContacted ( @RequestBody int id ){
        return ( new Matched()).relativeContacted(id,null);
    }

    @RequestMapping(value = "/2", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Matched status ( @RequestBody int id ){
        return ( new Matched()).statusId(id,null);
    }

    @RequestMapping( method = RequestMethod.GET )
    public Collection<Matched> getAllPeople_matched (){
        return ( new Matched()).getAll(null);
    }

}
