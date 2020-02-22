package personal.personalblogreturn.service;



import org.springframework.stereotype.Service;
import personal.personalblogreturn.pojo.Type;

import java.util.List;
import java.util.Map;

@Service
public interface TypeService {

    Integer addType(Type type);

    Integer delType(Integer id);

    Integer updateType(Type type);

    Type queryTypeByID(Integer id);

    Type queryTypeByName(Object name);

    List<Type> queryTypeByNameLike(String name);

    List<Type> queryTypePage();

    List<Type> queryTypeByCountsBlog(Integer limit);
}
