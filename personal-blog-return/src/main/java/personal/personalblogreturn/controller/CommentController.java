package personal.personalblogreturn.controller;

import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.personalblogreturn.pojo.Blog;
import personal.personalblogreturn.pojo.Comment;
import personal.personalblogreturn.service.BlogService;
import personal.personalblogreturn.service.CommentService;

import javax.servlet.http.HttpSession;

/**
 * Created by Ljh
 */


@Controller
public class CommentController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/comment/{id}")
    String index(@PathVariable Integer id, Model model,HttpSession session){
        Blog blog = blogService.queryBlogById(id);
        model.addAttribute("comments",blog.getComments() );
        return "public/blog :: commentList";
    }

    @RequestMapping("/postComment")
    String post(Comment comment, Model model, HttpSession session){
        if ((comment.getContent()==null || "".equals(comment.getContent())) ||
            (comment.getNickName()==null || "".equals(comment.getNickName())) ||
            (comment.getEmail()==null || "".equals(comment.getEmail()))){
            return index(comment.getBlog().getId(),model,session);
        }
        if (session.getAttribute("userInfo")!=null){
            comment.setAdmin(true);
        }
        commentService.addComment(comment);
        return index(comment.getBlog().getId(),model,session);
    }

}
