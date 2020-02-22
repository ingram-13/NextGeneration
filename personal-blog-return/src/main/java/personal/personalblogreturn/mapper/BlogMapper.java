package personal.personalblogreturn.mapper;


import org.apache.ibatis.annotations.*;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;
import personal.personalblogreturn.pojo.Blog;

import java.util.List;

/**
 * Created by Ljh
 */
@Repository
@Mapper
public interface BlogMapper{

    class BlogProvider {
        public String queryDynamic(Blog blog){
            String sql = "Select * from t_blog where recommend = #{recommend}";
            if (blog.getTitle()!=null && !blog.getTitle().isEmpty()){ sql+=" and title Like  concat('%',#{title},'%')"; }
            if (blog.getType()!=null){ sql+=" and type_id = #{typeId}";}
            return sql;
        }
    }

    /**
     * 新增博客并获取到自增id的值
     *
     * @param blog
     * @return
     */
    @Insert("INSERT INTO t_blog(id, appreciate_switch, comment_switch, content, copyright_switch, create_time,description,first_pic, flag, recommend, status, title, update_time, view_counts, type_id, user_id)" +
            "VALUES (#{id},#{appreciateSwitch},#{commentSwitch},#{content},#{copyrightSwitch},#{createTime},#{description},#{firstPic},#{flag},#{recommend},#{status},#{title},#{updateTime},#{viewCounts},#{typeId},1);")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer addBlog(Blog blog);

    /**
     * 新增博客的子操作，对从表进行新增
     *
     * @param blog_id
     * @param tags_id
     */
    @Insert("INSERT INTO t_blog_tags VALUES (#{blog_id},#{tags_id});")
    void addBlogTags(@Param("blog_id") Integer blog_id,@Param("tags_id") Integer tags_id);

    /**
     * 删除博客
     *
     * @param id
     * @return
     */
    @Delete("Delete from t_blog where id = #{id}")
    Integer delBlog(Integer id);

    /**
     * 删除博客的子操作，对从表删除
     *
     *
     * @param id
     */
    @Delete("Delete from t_blog_tags where blog_id=#{id}")
    void delBlogTags(Integer id);

    /**
     * 更新博客的子操作，对从表冗余的数据进行删除
     *
     * @param bid
     * @param tid
     */
    @Delete("Delete from t_blog_tags where blog_id=#{bid} and tags_id =#{tid} limit 1")
    void update_delBlogTags(@Param("bid") Integer bid,@Param("tid") Integer tid);

    /**
     * 更新博客
     *
     * @param blog
     * @return
     */
    @Update("Update t_blog set appreciate_switch=#{appreciateSwitch}, comment_switch=#{commentSwitch}, content=#{content}," +
            "                    copyright_switch=#{copyrightSwitch}, description=#{description}, first_pic=#{firstPic}, flag=#{flag}," +
            "                    recommend=#{recommend}, status=#{status}, title=#{title}," +
            "                    type_id=#{typeId} where id = #{id};")
    Integer updateBlog(Blog blog);

    /**
     * 更新博客的子操作，对从表添加新数据
     *
     * @param bid
     * @param tid
     */
    @Update("Insert into t_blog_tags values(#{bid},#{tid})")
    void update_addBlogTags(@Param("bid") Integer bid,@Param("tid") Integer tid);

    /**
     * 更新博客的点击数
     *
     * @param id
     * @return
     */
    @Update("update t_blog set view_counts = view_counts + 1 where id = #{id} ")
    Integer updateBlogViewCountS(Integer id);

    /**
     * 对标题，类型进行动态联表查询
     *
     * @param blog
     * @return
     */
    @SelectProvider(type = BlogProvider.class ,method ="queryDynamic" )
    @Results({
            @Result(property = "type", column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
    })
    List<Blog> queryBlogDynamic(Blog blog);

    /**
     * 查询所有博客，根据更新时间降序排序，后续用于分页查询
     *
     * @return
     */
    @Select("Select * from t_blog order by update_time desc")
    @Results({
            @Result(property = "typeId",column = "type_id"),
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "type", column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
            @Result(property = "tags",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.TagMapper.queryTagByBlogId"))
    })
    List<Blog> queryBlogPage();

    /**
     * 查询所有已发布博客，根据更新时间降序排序，后续用于分页查询
     *
     * @return
     */
    @Select("Select * from t_blog where status = true order by update_time desc")
    @Results({
            @Result(property = "typeId",column = "type_id"),
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "type", column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
            @Result(property = "tags",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.TagMapper.queryTagByBlogId"))
    })
    List<Blog> queryBlogIndexPage();

    @Select("select * from t_blog where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
            @Result(property = "tags",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.TagMapper.queryTagByBlogId")),
            @Result(property = "comments",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.CommentMapper.queryCommentByBlogId"))
    })
    Blog queryBlogById(Integer id);

    @Select("select tags_id from t_blog_tags where blog_id = #{id}")
    List<Integer> queryBlogTagsId(Integer id);

    @Select("select * from t_blog where title = #{title}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
            @Result(property = "tags",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.TagMapper.queryTagByBlogId"))
    })
    Blog queryBlogByTitle(String title);

    /**
     * 模糊查询
     *
     * @param title
     * @return
     */
    @Select("select * from t_blog where title Like concat('%',#{title},'%') and status = true")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
            @Result(property = "tags",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.TagMapper.queryTagByBlogId"))
    })
    List<Blog> queryBlogByTitleUnclear(String title);

    /**
     * 根据类型ID查询对应的博客，一对多查询的子操作
     *
     * @param type_id
     * @return
     */
    @Select("select * from t_blog where type_id = #{type_id} and status = true")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "type", column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
            @Result(property = "tags",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.TagMapper.queryTagByBlogId"))
    })
    List<Blog> queryBlogByTypeId(Integer type_id);

    /**
     * 根据标签ID查询对应的博客，多对多查询的子操作
     *
     * @param tags_id
     * @return
     */
    @Select("SELECT * FROM t_blog WHERE status = true and t_blog.`id` IN (SELECT blog_id FROM t_blog_tags WHERE tags_id = #{tags_id}) ")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "type", column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
            @Result(property = "tags",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.TagMapper.queryTagByBlogId"))
    })
    List<Blog> queryBlogByTagId(Integer tags_id);

    @Select("select * from t_blog where recommend = #{isRecommend} ORDER BY update_time Limit #{limit}")
    List<Blog> queryTitleByRecommend(@Param("isRecommend") Boolean isRecommend,@Param("limit") Integer limit);

    /**
     * 根据年份查询博客
     *
     * @param year
     * @return
     */
    @Select("SELECT * FROM t_blog WHERE YEAR(create_time) = #{year} and status = true ORDER BY create_time ")
    @Results({
            @Result(property = "typeId",column = "type_id"),
            @Result(property = "type",column = "type_id",
                    one = @One(select = "personal.personalblogreturn.mapper.TypeMapper.queryTypeByID")),
    })
    List<Blog> queryBlogByYear(Integer year);


}
