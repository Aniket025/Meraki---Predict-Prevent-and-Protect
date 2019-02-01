package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.Safespots_live;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/safespotsLive")
public class SafespotLiveController {

    @RequestMapping(method = RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE )
    public int saveSafespot ( @RequestBody Safespots_live temp ){
        return ( new Safespots_live()).saveSafespot(temp,null);
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Collection<Safespots_live> getSafespotsLiveInfo (@RequestBody int id ){
        return ( new Safespots_live()).getAllStatusByDisaster(id,null);
    }

    @RequestMapping(value = "/2", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Safespots_live getSafespotLiveInfo (@RequestBody int id ){
        return ( new Safespots_live()).getSafespotLiveInfo(id,null);
    }

    @RequestMapping(value = "/1", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public int updateSafespots (@RequestBody Safespots_live safespots_live ){
        return ( new Safespots_live()).updateStatus(safespots_live,null);
    }

}
