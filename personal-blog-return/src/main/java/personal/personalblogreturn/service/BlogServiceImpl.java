package personal.personalblogreturn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.personalblogreturn.mapper.BlogMapper;
import personal.personalblogreturn.mapper.TagMapper;
import personal.personalblogreturn.mapper.TypeMapper;
import personal.personalblogreturn.pojo.Blog;
import personal.personalblogreturn.pojo.Comment;
import personal.personalblogreturn.pojo.Tag;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ljh
 */
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public Integer addBlog(Blog blog) {
        addBlogPart(blog);
        for (Integer tagsId : blog.getTagsId()) {
            blogMapper.addBlogTags(blog.getId(),tagsId);
        }
        return null;
    }

    @Transactional
    public Integer addBlogPart(Blog blog) {
        List<Tag> list = new ArrayList<>();
        for (Integer tagsId : blog.getTagsId()) {
            list.add(tagMapper.queryTagByID(tagsId));
        }
        blog.setTags(list);
        blog.setType(typeMapper.queryTypeByID(blog.getTypeId()));
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViewCounts(0);
        return blogMapper.addBlog(blog);
    }



    @Transactional
    @Override
    public Integer delBlog(Integer id) {
        delBlogPart(id);
        return blogMapper.delBlog(id);
    }

    @Transactional
    public void delBlogPart(Integer id){
        blogMapper.delBlogTags(id);
    }

    @Transactional
    @Override
    public Integer updateBlog(Blog blog) {
        updateBlogPart(blog);
        //blog.setUpdateTime(new Date());
        return blogMapper.updateBlog(blog);
    }

    @Override
    public Integer updateBlogViewCountS(Integer id) {
        return blogMapper.updateBlogViewCountS(id);
    }

    @Transactional
    public void updateBlogPart(Blog blog){
        Integer bid = blog.getId();
        List<Integer> after = blog.getTagsId();
        List<Integer> before = blogMapper.queryBlogTagsId(bid);
        System.out.println("after:"+after);
        System.out.println("before:"+before);
        for (Integer b : before) {
            if (!after.contains(b)){
                blogMapper.update_delBlogTags(bid,b);
            }
        }
        for (Integer a : after) {
            if (!before.contains(a)){
                blogMapper.update_addBlogTags(bid,a);
            }
        }
    }

    @Transactional
    @Override
    public List<Blog> queryBlogDynamic(Blog blog) {
        return blogMapper.queryBlogDynamic(blog);
    }

    @Override
    public List<Blog> queryBlogPage() {
        return blogMapper.queryBlogPage();
    }

    @Override
    public List<Blog> queryBlogIndexPage() {
        return blogMapper.queryBlogIndexPage();
    }

    @Override
    public Blog queryBlogById(Integer id) {
        return blogMapper.queryBlogById(id);
    }

    @Override
    public List<Integer> queryBlogTagsId(Integer id) {
        return blogMapper.queryBlogTagsId(id);
    }

    @Override
    public Blog queryBlogByTitle(String title) {
        return blogMapper.queryBlogByTitle(title);
    }

    @Override
    public List<Blog> queryBlogByTypeId(Integer type_id) {
        return blogMapper.queryBlogByTypeId(type_id);
    }

    @Override
    public List<Blog> queryBlogByTagId(Integer tags_id) {
        return blogMapper.queryBlogByTagId(tags_id);
    }

    @Override
    public List<Blog> queryBlogByTitleUnclear(String title) {
        return blogMapper.queryBlogByTitleUnclear(title);
    }

    @Override
    public List<Blog> queryTitleByRecommend(Boolean isRecommend, Integer limit) {
        return blogMapper.queryTitleByRecommend(isRecommend,limit );
    }

    @Override
    public List<Blog> queryBlogByYear(Integer year) {
        return blogMapper.queryBlogByYear(year);
    }

}
