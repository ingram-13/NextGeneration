package personal.personalblogreturn.service;


import org.springframework.stereotype.Service;
import personal.personalblogreturn.pojo.Tag;
import personal.personalblogreturn.pojo.Type;

import java.util.List;
import java.util.Map;

@Service
public interface TagService {

    Integer addTag(Tag tag);

    Integer delTag(Integer id);

    Integer updateTag(Tag tag);

    Tag queryTagByID(Integer id);

    Tag queryTagByName(String name);

    List<Tag> queryTagByNameLike(String name);

    List<Tag> queryTagPage();

    List<Tag> queryTagByCountsBlog(Integer limit);
}
