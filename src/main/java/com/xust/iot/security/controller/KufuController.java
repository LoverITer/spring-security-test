package com.xust.iot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: HuangXin
 * @Date: Created in 15:14 2019/9/15  2019
 * @Description:
 */
@Controller
public class KufuController {

    private static final String PREFIX="pages";


    @RequestMapping(value = "/")
    public String index() {
        return "welcome";
    }

    @RequestMapping(value = "/userlogin")
    public String login(){
        return PREFIX+"/login";
    }

    @RequestMapping(value = "/level1/{page}")
    public String toLevel11(@PathVariable("page")int page) {
        return PREFIX+"/Level1/"+page;
    }


    @RequestMapping(value = "/level2/{page}")
    public String toLevel24(@PathVariable("page")int page) {
        return PREFIX+"/Level2/"+page;
    }


    @RequestMapping(value = "/level3/{page}")
    public String toLevel37(@PathVariable("page")int page) {
        return PREFIX+"/Level3/"+page;
    }

}
