package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.Safespots_req;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/safespotsReq")
public class SafespotReqController {

    @RequestMapping(method = RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE )
    public int saveSafespot ( @RequestBody Safespots_req temp ){
        return ( new Safespots_req()).saveRequest(temp,null);
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Collection<Safespots_req> getSafespotsInfo ( @RequestBody int id ){
        return ( new Safespots_req()).getAllRequestsByDisaster(id,null);
    }

    @RequestMapping(value = "/1", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public int updateSafespots (@RequestBody Safespots_req temp ){
        return ( new Safespots_req()).updateStatus(temp,null);
    }

}
