package personal.personalblogreturn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.personalblogreturn.mapper.CommentMapper;
import personal.personalblogreturn.pojo.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ljh
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment queryCommentByBlogId(Integer id) {

        return commentMapper.queryCommentByBlogId(id);
    }

    @Override
    public Comment queryParentComment(Integer parentId) {
        return commentMapper.queryParentComment(parentId);
    }

    @Override
    public List<Comment> querySonComment(Integer id) {
        return commentMapper.querySonComment(id);
    }

    @Transactional
    @Override
    public Integer addComment(Comment comment) {
        comment.setCreateDate(new Date());
        return commentMapper.addComment(comment);
    }

    @Override
    public Comment queryCommentById(Integer id) {
        return commentMapper.queryCommentById(id);
    }

    @Transactional
    @Override
    public Integer delComment(Integer id) {
        if (queryCommentById(id).getParentComment()==null){
            delCommentPart(id);
        }
        return commentMapper.delComment(id);
    }

    @Transactional
    public Integer delCommentPart(Integer id) {
        return commentMapper.delCommentPart(id);
    }

}
