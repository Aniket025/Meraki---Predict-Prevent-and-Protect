package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.Stores_live;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/storesLive")
public class StoresLiveController {

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Collection<Stores_live> getStoresInfo (@RequestBody int id ){
        return ( new Stores_live()).getAllStatusByDisaster(id,null);
    }

    @RequestMapping(value = "/1", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public int updateStatuse (@RequestBody Stores_live store ){
        return ( new Stores_live()).updateStatus(store,null);
    }

}
