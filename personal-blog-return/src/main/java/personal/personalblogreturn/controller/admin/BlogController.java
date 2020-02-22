package personal.personalblogreturn.controller.admin;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.personalblogreturn.pojo.Blog;
import personal.personalblogreturn.pojo.Tag;
import personal.personalblogreturn.pojo.Type;
import personal.personalblogreturn.service.BlogService;
import personal.personalblogreturn.service.TagService;
import personal.personalblogreturn.service.TypeService;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Ljh
 */

@Controller
@RequestMapping("/admin")
public class BlogController {

    private String content = null;
    private String flag = null;
    private String type = null;
    private String tag = null;
    private String firstPic = null;
    private String description = null;
    private boolean appreciateSwitch = true;
    private boolean commentSwitch = true;
    private boolean copyrightSwitch = true;
    private boolean recommend = true;

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @RequestMapping("/goAddBlog")
    String goAddBlog(Model model,HttpSession session){
        if (session.getAttribute("judge")==null || (boolean)session.getAttribute("judge")){
            session.setAttribute("judge",true );
        }else {
            session.setAttribute("judge",false );
        }
        List<Type> types = typeService.queryTypePage();
        List<Tag> tags = tagService.queryTagPage();
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        return "admin/blogRelease";
    }

    /**
     * 添加博客
     *
     * @param blog
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/addBlog")
    String addBlog(Blog blog,Model model,HttpSession session){
        if (blogService.queryBlogByTitle(blog.getTitle())==null){
            blogService.addBlog(blog);
        if (blog.isStatus()){
            model.addAttribute("info","发布成功" );
        }else {
            model.addAttribute("info","保存成功" );
        }
        return queryBlogPage(model, 1,5 ,session );
        }else {
            content = blog.getContent();
            flag = blog.getFlag();
            description = blog.getDescription();
            type = blog.getTypeId().toString();
            tag = Arrays.toString(blog.getTagsId().toArray()).replace("[","" ).replace("]","" ).replace(" ","" );
            firstPic = blog.getFirstPic();
            appreciateSwitch = blog.isAppreciateSwitch();
            commentSwitch = blog.isCommentSwitch();
            copyrightSwitch = blog.isCopyrightSwitch();
            recommend = blog.isRecommend();
            model.addAttribute("titleRepeat", "标题重复！请重新命名标题");

        }
        System.out.println("+++++++++>"+tag);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("content",content );map.put("flag",flag );map.put("copyrightSwitch", copyrightSwitch);
        map.put("commentSwitch", commentSwitch);map.put("appreciateSwitch", appreciateSwitch);map.put("description",description );
        map.put("recommend", recommend);map.put("type",type );map.put("tag",tag );map.put("firstPic",firstPic );
        model.addAllAttributes(map);
        return goAddBlog(model,session);
    }

    @RequestMapping("/delBlog/{id}")
    String delBlog(@PathVariable Integer id,Model model,HttpSession session){
        blogService.delBlog(id);
        model.addAttribute("info","删除成功" );
        return queryBlogPage(model, 1,5 ,session );
    }

    /**
     * 跳转至修改博客页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/goUpdateBlog/{id}")
    String gouUpdateBlog(@PathVariable Integer id,Model model,HttpSession session){
        session.setAttribute("judge",false);
        model.addAttribute("id",id);
        Blog blog = blogService.queryBlogById(id);
        List<Integer> tagsId = blogService.queryBlogTagsId(id);
        content = blog.getContent();
        flag = blog.getFlag();
        description = blog.getDescription();
        String title = blog.getTitle();
        type = blog.getType().getId().toString();
        tag = Arrays.toString(tagsId.toArray()).replace("[","" ).replace("]","" ).replace(" ","" );
        firstPic = blog.getFirstPic();
        appreciateSwitch = blog.isAppreciateSwitch();
        commentSwitch = blog.isCommentSwitch();
        copyrightSwitch = blog.isCopyrightSwitch();
        recommend = blog.isRecommend();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("content",content );map.put("flag",flag );map.put("copyrightSwitch", copyrightSwitch);map.put("description",description );
        map.put("commentSwitch", commentSwitch);map.put("appreciateSwitch", appreciateSwitch);map.put("title",title );
        map.put("recommend", recommend);map.put("type",type );map.put("tag",tag);map.put("firstPic",firstPic );
        model.addAllAttributes(map);
        return goAddBlog(model,session);
    }

    @RequestMapping("/updateBlog")
    String updateBlog(Blog blog,Model model,HttpSession session){
        blogService.updateBlog(blog);
        return queryBlogPage(model, 1, 5, session);
    }
    /**
     * 根据标题、类型、是否推荐进行动态查询
     *
     * @param blog
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/queryBlogDynamic")
    String queryBlogDynamic(Blog blog, HttpSession session,Model model){
        if (blog.getTypeId()!=null){blog.setType(typeService.queryTypeByID(blog.getTypeId()));}
        System.out.println(blogService.queryBlogDynamic(blog));
        if (blogService.queryBlogDynamic(blog).size()==0){
            model.addAttribute("queryError","无目标对象"+" "+blog.getTitle());
            return queryBlogPage(model,1 ,5 ,session);
        }else {
            List<Blog> blogs = blogService.queryBlogDynamic(blog);
            session.setAttribute("queryBlog",blogs );
            return queryBlogPage(model,1 ,5 ,session);
        }
    }

    @RequestMapping("/queryBlogPage")
    String index(Model model,HttpSession session){
        Integer pageNum = 1;
        Integer pageSize = 5;
        return queryBlogPage(model, pageNum, pageSize,session);
    }

    /**
     * 分页查询博客展示
     *
     * @param model
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping("/queryBlogPage/{pageNum}/{pageSize}")
    String queryBlogPage(Model model,
                         @PathVariable Integer pageNum,
                         @PathVariable Integer pageSize,
                         HttpSession session){
        if(session.getAttribute("queryBlog")==null){
            List<Type> types = typeService.queryTypePage();
            model.addAttribute("types",types );
            PageHelper.startPage(pageNum,pageSize);
            List<Blog> blogs = blogService.queryBlogPage();
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
            return "admin/blogManage";
        } else {
            PageHelper.startPage(pageNum,pageSize);
            List<Blog> blogs = (List<Blog>) session.getAttribute("queryBlog");
            PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogs,99);
            model.addAttribute("pageInfo",pageInfo );
            //获取搜查结果条数
            model.addAttribute("blogResult","共"+blogs.size()+"条记录");
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
            session.removeAttribute("queryBlog");
            return "admin/blogManage";
        }

    }



}
