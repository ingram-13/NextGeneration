package personal.personalblogreturn.service;

import org.springframework.stereotype.Service;
import personal.personalblogreturn.pojo.Blog;

import java.util.List;

/**
 * Created by Ljh
 */
@Service
public interface BlogService {

    Integer addBlog(Blog blog);

    Integer delBlog(Integer id);

    Integer updateBlog(Blog blog);

    Integer updateBlogViewCountS(Integer id);

    List<Blog> queryBlogDynamic(Blog blog);

    List<Blog> queryBlogPage();

    List<Blog> queryBlogIndexPage();

    Blog queryBlogById(Integer id);

    List<Integer> queryBlogTagsId(Integer id);

    Blog queryBlogByTitle(String title);

    List<Blog> queryBlogByTypeId(Integer type_id);

    List<Blog> queryBlogByTagId(Integer tags_id);

    List<Blog> queryBlogByTitleUnclear(String title);

    List<Blog> queryTitleByRecommend( Boolean isRecommend,Integer limit);

    List<Blog> queryBlogByYear(Integer year);
}
