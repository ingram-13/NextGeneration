package personal.personalblogreturn.controller;


import com.github.pagehelper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import personal.personalblogreturn.pojo.Blog;
import personal.personalblogreturn.pojo.Tag;
import personal.personalblogreturn.service.BlogService;
import personal.personalblogreturn.service.TagService;
import personal.personalblogreturn.service.TypeService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Ljh
 */

@Controller
public class TagsController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;


    @RequestMapping("/tag")
    String index(Model model, HttpSession session){
        return queryTagPage(model,session ,1 ,5,-1 );
    }

    @RequestMapping("/tag/{pageNum}/{pageSize}/{id}")
    String queryTagPage(Model model,
                        HttpSession session,
                        @PathVariable Integer pageNum,
                        @PathVariable Integer pageSize,
                        @PathVariable Integer id){
        List<Tag> tags = tagService.queryTagByCountsBlog(99);
        model.addAttribute("tags",tags);
        if (id==-1){
            //获得总的条数
            model.addAttribute("size", blogService.queryBlogIndexPage().size());
            PageHelper.startPage(pageNum,pageSize );
            List<Blog> blogs = blogService.queryBlogIndexPage();
            PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogs,pageSize);
            model.addAttribute("pageInfo",pageInfo );

            //获得当前页
            model.addAttribute("pageNum", pageInfo.getPageNum());
            //获得一页显示的条数
            model.addAttribute("pageSize", pageInfo.getPageSize());
            //是否是第一页
            model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
            //获得总页数
            model.addAttribute("totalPages", pageInfo.getPages());
            //是否是最后一页
            model.addAttribute("isLastPage", pageInfo.isIsLastPage());
            //获得当前行数
            model.addAttribute("startRow", pageInfo.getStartRow());
            boolean LF = false;
            if(pageInfo.isIsFirstPage()&&pageInfo.isIsLastPage()){
                LF = true;
            }
            model.addAttribute("LF",LF );
            LF = false;
        }else {
            //获得总的条数
            model.addAttribute("size", blogService.queryBlogByTagId(id).size());

            PageHelper.startPage(pageNum,pageSize );
            List<Blog> blogs = blogService.queryBlogByTagId(id);
            Tag tag = tagService.queryTagByID(id);
            PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogs,pageSize);
            model.addAttribute("pageInfo",pageInfo );
            //获得当前页
            model.addAttribute("pageNum", pageInfo.getPageNum());
            //获得一页显示的条数
            model.addAttribute("pageSize", pageInfo.getPageSize());
            //是否是第一页
            model.addAttribute("isFirstPage", pageInfo.isIsFirstPage());
            //获得总页数
            model.addAttribute("totalPages", pageInfo.getPages());
            //是否是最后一页
            model.addAttribute("isLastPage", pageInfo.isIsLastPage());
            //获得当前行数
            model.addAttribute("startRow", pageInfo.getStartRow());
            //获得当前选中的tag名称
            model.addAttribute("tagName",tag.getName());
            boolean LF = false;
            if(pageInfo.isIsFirstPage()&&pageInfo.isIsLastPage()){
                LF = true;
            }
            model.addAttribute("LF",LF );
            session.removeAttribute("noTarget");
            LF = false;
        }

        return "public/tags";
    }
}
