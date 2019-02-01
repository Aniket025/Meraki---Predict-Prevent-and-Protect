package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.Disaster;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/disaster")
public class DisasterController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int getDisasterInfo(@RequestBody Disaster disaster) {
        return ( new Disaster().saveDisaster(disaster,null));
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Disaster getDisaster ( @RequestBody int id ){
        return ( new Disaster()).getDisasterInfo(id,null);
    }

    @RequestMapping(value = "/1", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Collection<Disaster> getDisastersByState ( @RequestBody int stateId ){
        return ( new Disaster()).getDisastersByState(stateId,null);
    }

    @RequestMapping( method = RequestMethod.GET )
    public Collection<Disaster> getAllDisasters (){
        return ( new Disaster()).getAllDisasters(null);
    }

}
