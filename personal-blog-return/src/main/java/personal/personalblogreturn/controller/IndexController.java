package personal.personalblogreturn.controller;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import personal.personalblogreturn.pojo.Blog;
import personal.personalblogreturn.pojo.Tag;
import personal.personalblogreturn.pojo.Type;
import personal.personalblogreturn.service.BlogService;
import personal.personalblogreturn.service.TagService;
import personal.personalblogreturn.service.TypeService;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Ljh
 */

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;




    @RequestMapping("/queryBlog")
    String queryBlog(String title,HttpSession session,Model model){
        List<Blog> blogs = blogService.queryBlogByTitleUnclear(title);
        if (blogs ==null){
            model.addAttribute("negativeInfo","查询结果为空，请检查标题是否拼写正确" );
            return  index(session,model );
        }
        session.setAttribute("blogs",blogs);
        model.addAttribute("counts",blogs.size() );
        model.addAttribute("successInfo","查询成功：共"+blogs.size()+"条记录" );
        return  index(session ,model );
    }

    @RequestMapping("/index/{pageNum}/{pageSize}")
    String queryIndexPage(@PathVariable Integer pageNum,
                         @PathVariable Integer pageSize,
                         HttpSession session,
                         Model model){
        List<Type> types = typeService.queryTypeByCountsBlog(6);
        List<Tag> tags = tagService.queryTagByCountsBlog(9);
        List<Blog> recommend = blogService.queryTitleByRecommend(true, 8);
        model.addAttribute("recommend", recommend);
        model.addAttribute("tags",tags );
        model.addAttribute("types",types );
        if (session.getAttribute("blogs")==null){
            //获得总的条数
            model.addAttribute("size", blogService.queryBlogIndexPage().size());

           PageHelper.startPage(pageNum,pageSize);
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
           return "public/index";
       }else {
           PageHelper.startPage(pageNum,pageSize);
           List<Blog> blogs = (List<Blog>) session.getAttribute("blogs");
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
           session.removeAttribute("blogs");
           return "public/index";
       }
    }

    @RequestMapping("/index")
    String index(HttpSession session,Model model){
        Integer pageNum = 1;
        Integer pageSize = 10;
        return queryIndexPage(pageNum,pageSize,session ,model);
    }

    @GetMapping("/footer/recommendList")
    public String recommend(Model model) {
        model.addAttribute("recommendList", blogService.queryTitleByRecommend(true,3));
        return "commons/commons :: recommendList";
    }
}
