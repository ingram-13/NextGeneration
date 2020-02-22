package personal.personalblogreturn.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.personalblogreturn.mapper.TypeMapper;
import personal.personalblogreturn.pojo.Type;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ljh
 */
@Service
public class TypeServiceImpl implements TypeService {


    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public Integer addType(Type type) {
        return typeMapper.addType(type);
    }

    @Transactional
    @Override
    public Integer delType(Integer id) {
        delTypePart(id);
        return typeMapper.delType(id);
    }

    @Transactional
    public void delTypePart(Integer id){
        typeMapper.delTypePart(id);
    }

    @Transactional
    @Override
    public Integer updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Transactional
    @Override
    public Type queryTypeByID(Integer id) {
        return typeMapper.queryTypeByID(id);
    }
    @Transactional
    @Override
    public Type queryTypeByName(Object name) {
        return typeMapper.queryTypeByName(name);
    }

    @Transactional
    @Override
    public List<Type> queryTypeByNameLike(String name) {
        return typeMapper.queryTypeByNameLike(name);
    }


    @Transactional
    @Override
    public List<Type> queryTypePage() {
        return typeMapper.queryTypePage();
    }

    @Override
    public List<Type> queryTypeByCountsBlog(Integer limit) {
        List<Type> types = typeMapper.queryTypeByCountsBlog(limit);
        types.sort(new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
            return o2.getBlog().size()-o1.getBlog().size();
            }
        });
        return types;
    }


}
