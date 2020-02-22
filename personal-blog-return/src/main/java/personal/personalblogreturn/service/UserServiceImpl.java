package personal.personalblogreturn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import personal.personalblogreturn.mapper.UserMapper;
import personal.personalblogreturn.pojo.User;
import personal.personalblogreturn.utils.MD5util;

/**
 * Created by Ljh
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User checkUser(String username,String password) {
        return userMapper.checkUser(username, MD5util.code(password));
    }
}
