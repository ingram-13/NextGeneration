package personal.personalblogreturn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import personal.personalblogreturn.service.TagService;
import personal.personalblogreturn.service.TypeService;

/**
 * Created by Ljh
 */

@Controller
public class aboutMeController {

    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    @RequestMapping("/aboutMe")
    String aboutMe(Model model){
        model.addAttribute("tag", tagService.queryTagByCountsBlog(99));
        model.addAttribute("type",typeService.queryTypeByCountsBlog(99));
        return "public/aboutMe";
    }
}
