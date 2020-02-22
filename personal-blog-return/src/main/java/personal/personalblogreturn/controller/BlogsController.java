package personal.personalblogreturn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import personal.personalblogreturn.pojo.Blog;
import personal.personalblogreturn.pojo.Comment;
import personal.personalblogreturn.service.BlogService;
import personal.personalblogreturn.service.CommentService;
import personal.personalblogreturn.utils.MarkDownUtil;

import javax.servlet.http.HttpSession;

/**
 * Created by Ljh
 */


@Controller
public class BlogsController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/blog/{id}")
    String queryBlog(Model model,
                     @PathVariable  Integer id){
        Blog blog = blogService.queryBlogById(id);
        blogService.updateBlogViewCountS(id);
        String contentb = blog.getContent();
        String contenta = MarkDownUtil.markdownToHtmlExtensions(contentb);
        blog.setContent(contenta);
        model.addAttribute("blog",blog);
        return "public/blog";
    }

    @RequestMapping("/delComment/{id}")
    String del(@PathVariable  Integer id,
               Model model ,HttpSession session){
        Comment comment = commentService.queryCommentById(id);
        Integer bid = comment.getBlog().getId() ;
        commentService.delComment(id);
        return queryBlog(model,bid);
    }
}
