package personal.personalblogreturn.controller;

import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ljh
 */

@Controller
public class test {
//
//    @GetMapping("/{id}/{name}")
//    public String getTest(@PathVariable Integer id,@PathVariable String name){
////        int i= 9/0;
//
//        System.out.println("---getTest---");
//        return "public/index";
//    }

    @RequestMapping("/index")
    public String testG(){
        System.out.println("==============jkkk+++++++++++=");
        return "public/index";
    }
}
