package personal.personalblogreturn.mapper;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import personal.personalblogreturn.pojo.Tag;
import personal.personalblogreturn.pojo.Type;


import java.util.List;

@Mapper
@Repository
public interface TagMapper {

    @Insert("INSERT INTO t_tag(id, name) VALUES (#{id},#{name})")
    Integer addTag(Tag tag);

    @Delete("Delete from t_tag where id=#{id}")
    Integer delTag(@Param("id") Integer id);

    @Delete("Delete from t_blog_tags where tags_id = #{id}")
    void delTagPart(Integer id);

    @Update("UPDATE t_tag SET name = #{name} where id =#{id}")
    Integer updateTag(Tag tag);

    @Select("Select * from t_tag where id = #{id}")
    Tag queryTagByID(@Param("id") Integer id);

    @Select("Select * from t_tag where name = #{name}")
    Tag queryTagByName(@Param("name") String name);

    @Select("SELECT * FROM t_tag WHERE name LIKE concat('%',#{name},'%')")
    List<Tag> queryTagByNameLike(@Param("name") String name);

    @Select("Select * from t_tag")
    List<Tag> queryTagPage();

    @Select("SELECT t_tag.name FROM t_blog_tags,t_tag where t_tag.id = t_blog_tags.tags_id " +
            "GROUP BY t_blog_tags.tags_id ORDER BY COUNT(t_blog_tags.blog_id) DESC Limit #{limit}")
    List<String> queryTagByCountsBlogN(Integer limit);

    @Select("SELECT COUNT(blog_id) AS times FROM t_blog_tags GROUP BY tags_id ORDER BY times DESC Limit #{limit}")
    List<Integer> queryTagByCountsBlogC(Integer limit);
    @Select("SELECT  DISTINCT  t_tag.* FROM t_tag,t_blog_tags where id = tags_id Limit #{limit}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "blog",column = "id",javaType = List.class,
                    many = @Many(select = "personal.personalblogreturn.mapper.BlogMapper.queryBlogByTagId"))
    })
    List<Tag> queryTagByCountsBlog(Integer limit);

    @Select("SELECT * FROM t_tag where t_tag.id IN (SELECT t_blog_tags.tags_id from t_blog_tags where t_blog_tags.blog_id = #{id}) ")
    List<Tag> queryTagByBlogId(Integer id);
}
