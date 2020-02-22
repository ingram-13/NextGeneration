package personal.personalblogreturn.mapper;


import org.apache.ibatis.annotations.*;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;
import personal.personalblogreturn.pojo.Type;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TypeMapper {

    @Insert("INSERT INTO t_type(id, name) VALUES (#{id},#{name})")
    Integer addType(Type type);

    @Delete("Delete from t_type where id=#{id}")
    Integer delType(@Param("id") Integer id);

    @Update("UPDATE t_blog SET type_id = NULL WHERE type_id = #{id}")
    void delTypePart(Integer id);

    @Update("UPDATE t_type SET name = #{name} where id =#{id}")
    Integer updateType(Type type);

    @Select("Select * from t_type where id = #{id}")
    Type queryTypeByID(@Param("id") Integer id);

    @Select("Select * from t_type where name = #{name}")
    Type queryTypeByName(@Param("name") Object name);

    @Select("SELECT * FROM t_type WHERE name LIKE concat('%',#{name},'%')")
    List<Type> queryTypeByNameLike(@Param("name") String name);

    @Select("Select * from t_type")
    List<Type> queryTypePage();

    /**
     * 查询所有的类型，根据每个类型对应的博客数量进行降序排序
     *
     * @param limit
     * @return
     */

    @Select("SELECT  DISTINCT  t_type.* FROM t_type,t_blog where t_type.id = type_id Limit #{limit}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "blog",column = "id",javaType = List.class,
            many = @Many(select = "personal.personalblogreturn.mapper.BlogMapper.queryBlogByTypeId"))
    })
    List<Type> queryTypeByCountsBlog(Integer limit);
}
