package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.Arriving;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/arriving")
public class ArrivingController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Arriving> getArrivingInfo(@RequestBody int safespotId) {
        return ( new Arriving().peopleArriving(safespotId,null));
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public int saveArrival ( @RequestBody Arriving arrive ){
        return ( new Arriving()).saveArriving(arrive,null);

    }

    @RequestMapping(method = RequestMethod.GET)
    public String check (){
        return "hi";
    }

}
