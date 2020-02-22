package personal.personalblogreturn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import personal.personalblogreturn.pojo.Blog;
import personal.personalblogreturn.service.BlogService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ljh
 */

@Controller
public class fileController {

    @Autowired
    private BlogService blogService;


    @RequestMapping("/file")
    String index(Model model){
        int size = blogService.queryBlogPage().size();
        List<Blog> nineteen = blogService.queryBlogByYear(2019);
        List<Blog> twenty = blogService.queryBlogByYear(2020);
        Map<List<Blog>,Integer> file = new LinkedHashMap<>();
        file.put(nineteen,2019 );
        file.put(twenty,2020 );
        model.addAttribute("file",file );
        model.addAttribute("size",size );
        return "public/file";
    }
}
