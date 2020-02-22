package personal.personalblogreturn.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.personalblogreturn.pojo.Type;
import personal.personalblogreturn.service.TypeService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Ljh
 */

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 增加类型
     *
     * @param type
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/addType")
    String addType(Type type, RedirectAttributes redirectAttributes){
        String name = type.getName();
        if (name.isEmpty()){
            redirectAttributes.addFlashAttribute("typeEmpty","添加失败:名称不能为空" );
            return "redirect:typesRelease.html";
        }else if (typeService.queryTypeByName(name)!=null){
            redirectAttributes.addFlashAttribute("typeRepeat", "添加失败:该类型已存在");
            return "redirect:typesRelease.html";
        }else if (name.length()>20){
            redirectAttributes.addFlashAttribute("typeOutOfBounds", "添加失败:名称长度不能超过10个中文或20个英文");
            return "redirect:typesRelease.html";
        }else{
            redirectAttributes.addFlashAttribute("addTypeSuccess","添加成功！" );
            typeService.addType(type);
            return "redirect:/admin/queryTypePage";
        }
    }


    /**
     * 删除类型
     *
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/delType/{id}")
    String delType(@PathVariable Integer id,Model model,HttpSession session){
        model.addAttribute("delInfo","删除成功" );
        typeService.delType(id);
//        Integer page = (id-1) % 3 + 1;
        return queryTypePage(model,1 ,10 , session);
    }

    /**
     * 跳转至更新类型页面
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/goUpdateType/{id}")
    String updateType(Model model,@PathVariable Integer id){
        Type type = typeService.queryTypeByID(id);
        model.addAttribute("type",type);
        return "admin/typesUpdate.html";
    }


    /**
     * 更新类型
     *
     * @param type
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/updateType")
    String updateType(Type type, RedirectAttributes redirectAttributes){
        String name = type.getName();
        if (name.isEmpty()){
            redirectAttributes.addFlashAttribute("typeEmpty","修改失败:名称不能为空" );
            return "redirect:/admin/goUpdateType/"+type.getId();
        }else if (typeService.queryTypeByName(name)!=null){
            redirectAttributes.addFlashAttribute("typeRepeat", "修改失败:该类型已存在");
            return "redirect:/admin/goUpdateType/"+type.getId();
        }else if (name.length()>20){
            redirectAttributes.addFlashAttribute("typeOutOfBounds", "修改失败:名称长度不能超过10个中文或20个英文");
            return "redirect:/admin/goUpdateType/"+type.getId();
        }else{
            redirectAttributes.addFlashAttribute("addTypeSuccess","修改成功！" );
            typeService.updateType(type);
            return "redirect:/admin/queryTypePage";
        }

    }

    /**
     * 根据名称模糊查询
     *
     * @param session
     * @param name
     * @param model
     * @return
     */
    @RequestMapping("/queryTypeLike")
    String queryTypeLike(HttpSession session,
                         @RequestParam("name") String name,
                         Model model){
        model.addAttribute("query",1 );
        if(typeService.queryTypeByNameLike(name).isEmpty()){
            model.addAttribute("queryError", "无结果");
            return queryTypePage(model,1 ,10 ,session );
        }else {
            session.setAttribute("queryTypeLike", name);
            return queryTypePage(model,1 ,10 ,session );
        }
    }

    @RequestMapping("/queryTypePage")
    String index(Model model,
                 HttpSession session){
        Integer pageNum=1;
        Integer pageSize=10;
        return queryTypePage(model,pageNum ,pageSize ,session );
    }

    /**
     * 分页查询展示
     *
     * @param model
     * @param pageNum
     * @param pageSize
     * @return 展示分页查询或搜查结果
     */
    @RequestMapping("/queryTypePage/{pageNum}/{pageSize}")
    String queryTypePage(Model model,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("pageSize")Integer pageSize,
                         HttpSession session){
        PageHelper.startPage(pageNum,pageSize );
        if (session.getAttribute("queryTypeLike")==null){
            List<Type> types = typeService.queryTypePage();
            PageInfo<Type> pageInfo = new PageInfo<Type>(types,pageSize);

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
            return "admin/typesManage";
        }else{
            String name = (String) session.getAttribute("queryTypeLike");
            List<Type> types = typeService.queryTypeByNameLike(name);
            PageInfo<Type> pageInfo = new PageInfo<Type>(types,pageSize);
            //获取搜查结果条数
            model.addAttribute("typeResult","共"+types.size()+"条记录");

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
            session.removeAttribute("queryTypeLike");
            return "admin/typesManage";
        }

    }

}
