package personal.personalblogreturn.service;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import personal.personalblogreturn.pojo.Comment;

import java.util.List;

@Service
public interface CommentService {

    Comment queryCommentByBlogId(Integer id);

    Comment queryParentComment(Integer parentId);

    List<Comment> querySonComment(Integer id);

    Integer addComment(Comment comment);

    Comment queryCommentById(Integer id);

    Integer delComment(Integer id);
}
