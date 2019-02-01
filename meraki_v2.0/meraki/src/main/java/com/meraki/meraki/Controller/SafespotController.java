package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.SafeSpots;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/safespots")
public class SafespotController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int saveSafeSpotsInfo(@RequestBody SafeSpots safeSpots) {
        return ( new SafeSpots().saveSafespots(safeSpots,null));
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public SafeSpots getSafeSpotsInfo ( @RequestBody int id ){
        return ( new SafeSpots()).getSingleInfo(id,null);
    }

    @RequestMapping(value = "/1", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Collection<SafeSpots> getAllSafeSpotsByState ( @RequestBody int state_id ){
        return ( new SafeSpots()).getSafespotsByState(state_id,null);
    }

    @RequestMapping( method = RequestMethod.GET )
    public Collection<SafeSpots> getAllSafeSpots (){
        return ( new SafeSpots().getAll(null));
    }

}
