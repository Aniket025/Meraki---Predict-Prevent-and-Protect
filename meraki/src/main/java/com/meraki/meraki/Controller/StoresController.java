package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.Stores;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/stores")
public class StoresController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int saveStoresInfo(@RequestBody Stores stores) {
        return ( new Stores().saveStores(stores,null));
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Stores getStoresInfo ( @RequestBody int id ){
        return ( new Stores()).getSingleInfo(id,null);
    }

    @RequestMapping(value = "/1", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Collection<Stores> getAllStores ( @RequestBody int type ){
        return ( new Stores()).getAll(type,null);
    }

    @RequestMapping(value = "/2", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public Collection<Stores> getAllStoresByState ( @RequestBody int state_id ){
        return ( new Stores()).getStoresByState(state_id,null);
    }

    @RequestMapping( method = RequestMethod.GET )
    public Collection<Stores> getAllPeople_stores (){
        return ( new Stores().getAll(0,null));
    }

}
