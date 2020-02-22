package personal.personalblogreturn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import personal.personalblogreturn.mapper.TagMapper;
import personal.personalblogreturn.mapper.TypeMapper;
import personal.personalblogreturn.pojo.Tag;
import personal.personalblogreturn.pojo.Type;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ljh
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public Integer addTag(Tag tag) {
        return tagMapper.addTag(tag);
    }

    @Transactional
    @Override
    public Integer delTag(Integer id) {
        delTagPart(id);
        return tagMapper.delTag(id);
    }

    @Transactional
    public void delTagPart(Integer id){
        tagMapper.delTagPart(id);
    }

    @Transactional
    @Override
    public Integer updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Override
    public Tag queryTagByID(Integer id) {
        return tagMapper.queryTagByID(id);
    }

    @Override
    public Tag queryTagByName(String name) {
        return tagMapper.queryTagByName(name);
    }

    @Override
    public List<Tag> queryTagByNameLike(String name) {
        return tagMapper.queryTagByNameLike(name);
    }

    @Override
    public List<Tag> queryTagPage() {
        return tagMapper.queryTagPage();
    }

    @Override
    public List<Tag> queryTagByCountsBlog(Integer limit) {
        List<Tag> tags = tagMapper.queryTagByCountsBlog(limit);
        tags.sort(new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                return o2.getBlog().size() - o1.getBlog().size();
            }
        });
        return tags;
    }
}
