package com.meraki.meraki.Controller;

import com.meraki.meraki.Model.SmsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/smsService")
public class SmsController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int subscribe(@RequestBody SmsService service) {
        return ( new SmsService().saveService(service,null));
    }

    @RequestMapping(value = "/0", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE )
    public int unsubscribe ( @RequestBody SmsService service ){
        return ( new SmsService()).DeleteService(service,null);

    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<SmsService> getAllService (){
        return (new SmsService()).getAll(null);
    }

}
