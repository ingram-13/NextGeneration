package personal.personalblogreturn.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import personal.personalblogreturn.pojo.Comment;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {

    @Select("SELECT * FROM t_comment WHERE blog_id = #{id} and parent_comment_id is null ORDER BY create_date DESC;")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "parentComment",column = "parent_comment_id",
                    one = @One(select = "personal.personalblogreturn.mapper.CommentMapper.queryParentComment")),
            @Result(property = "sonComment",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.CommentMapper.querySonComment"))
    })
    Comment queryCommentByBlogId(Integer id);

    @Select("SELECT * FROM t_comment WHERE id = #{id};")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "parentComment",column = "parent_comment_id",
                    one = @One(select = "personal.personalblogreturn.mapper.CommentMapper.queryParentComment")),
            @Result(property = "sonComment",column = "id",
                    many = @Many(select = "personal.personalblogreturn.mapper.CommentMapper.querySonComment")),
            @Result(property = "blog",column = "blog_id",
                    one = @One(select = "personal.personalblogreturn.mapper.BlogMapper.queryBlogById"))
    })
    Comment queryCommentById(Integer id);

    @Select("Select * from t_comment where id = #{parentId}")
    Comment queryParentComment(Integer parentId);

    @Select("Select * from t_comment where parent_comment_id = #{id}")
    List<Comment> querySonComment(Integer id);

    @Insert("Insert into t_comment(content,create_date,email,nick_name,blog_id,parent_comment_id,r_object,admin)" +
            "values(#{content},#{createDate},#{email},#{nickName},#{blog.id},#{parentComment.id},#{rObject},#{admin})")
    Integer addComment(Comment comment);

    @Delete("DELETE  FROM t_comment WHERE id = #{id};")
    Integer delComment(Integer id);

    @Delete("DELETE  FROM t_comment WHERE parent_comment_id = #{id};")
    Integer delCommentPart(Integer id);
}
