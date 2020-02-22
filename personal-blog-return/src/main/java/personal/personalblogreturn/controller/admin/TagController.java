package personal.personalblogreturn.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.personalblogreturn.pojo.Tag;
import personal.personalblogreturn.service.TagService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Ljh
 */

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 添加标签
     *
     * @param tag
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/addTag")
    String addTag(Tag tag, RedirectAttributes redirectAttributes){
        String name = tag.getName();
        if (name.isEmpty()){
            redirectAttributes.addFlashAttribute("tagEmpty","添加失败:名称不能为空" );
            return "redirect:tagsRelease.html";
        }else if (tagService.queryTagByName(name)!=null){
            redirectAttributes.addFlashAttribute("tagRepeat", "添加失败:该标签已存在");
            return "redirect:tagsRelease.html";
        }else if (name.length()>20){
            redirectAttributes.addFlashAttribute("tagOutOfBounds", "添加失败:名称长度不能超过10个中文或20个英文");
            return "redirect:tagsRelease.html";
        }else{
            redirectAttributes.addFlashAttribute("addTagSuccess","添加成功！" );
            tagService.addTag(tag);
            return "redirect:/admin/queryTagPage";
        }
    }


    /**
     * 删除标签
     *
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/delTag/{id}")
    String delTag(@PathVariable Integer id,Model model,HttpSession session){
        model.addAttribute("delInfo","删除成功" );
        tagService.delTag(id);
        return queryTagPage(model,1 ,10 , session);
    }

    /**
     * 跳转至修改标签页面
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/goUpdateTag/{id}")
    String updateTag(Model model,@PathVariable Integer id){
        Tag tag = tagService.queryTagByID(id);
        model.addAttribute("tag",tag);
        return "admin/tagsUpdate.html";
    }


    /**
     * 修改标签
     *
     * @param tag
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/updateTag")
    String updateTag(Tag tag, RedirectAttributes redirectAttributes){
        String name = tag.getName();
        if (name.isEmpty()){
            redirectAttributes.addFlashAttribute("tagEmpty","修改失败:名称不能为空" );
            return "redirect:/admin/goUpdateTag/"+tag.getId();
        }else if (tagService.queryTagByName(name)!=null){
            redirectAttributes.addFlashAttribute("tagRepeat", "修改失败:该标签已存在");
            return "redirect:/admin/goUpdateTag/"+tag.getId();
        }else if (name.length()>20){
            redirectAttributes.addFlashAttribute("tagOutOfBounds", "修改失败:名称长度不能超过10个中文或20个英文");
            return "redirect:/admin/goUpdateTag/"+tag.getId();
        }else{
            redirectAttributes.addFlashAttribute("addTagSuccess","修改成功！" );
            tagService.updateTag(tag);
            return "redirect:/admin/queryTagPage";
        }

    }

    /**
     * 根据名称模糊查询标签
     *
     * @param session
     * @param name
     * @param model
     * @return
     */
    @RequestMapping("/queryTagLike")
    String queryTagLike(HttpSession session,
                         @RequestParam("name") String name,
                         Model model){
        model.addAttribute("query",1);
        if(tagService.queryTagByNameLike(name).isEmpty()){
            model.addAttribute("queryError", "无结果");
            return queryTagPage(model,1 ,10 ,session );
        }else {
            session.setAttribute("queryTagLike", name);
            return queryTagPage(model,1 ,10 ,session );
        }
    }

    @RequestMapping("/queryTagPage")
    String index(Model model,
                 HttpSession session){
        Integer pageNum=1;
        Integer pageSize=10;
        return queryTagPage(model,pageNum ,pageSize ,session );
    }

    /**
     * 分页查询展示
     *
     * @param model
     * @param pageNum
     * @param pageSize
     * @return 展示分页查询或搜查结果
     */
    @RequestMapping("/queryTagPage/{pageNum}/{pageSize}")
    String queryTagPage(Model model,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("pageSize")Integer pageSize,
                         HttpSession session){
        PageHelper.startPage(pageNum,pageSize );
        if (session.getAttribute("queryTagLike")==null){
            List<Tag> tags = tagService.queryTagPage();
            PageInfo<Tag> pageInfo = new PageInfo<Tag>(tags,pageSize);

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
            return "admin/tagsManage";
        }else{
            String name = (String) session.getAttribute("queryTagLike");
            List<Tag> tags = tagService.queryTagByNameLike(name);
            PageInfo<Tag> pageInfo = new PageInfo<Tag>(tags,pageSize);
            //获取搜查结果条数
            model.addAttribute("tagResult","共"+tags.size()+"条记录");

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
            //当前页面前后都无可选
            boolean LF = false;
            if(pageInfo.isIsFirstPage()&&pageInfo.isIsLastPage()){
                LF = true;
            }
            model.addAttribute("LF",LF );
            LF = false;
            session.removeAttribute("queryTagLike");
            return "admin/tagsManage";
        }

    }

}
