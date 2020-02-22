package personal.personalblogreturn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import personal.personalblogreturn.mapper.UserMapper;
import personal.personalblogreturn.pojo.User;

/**
 * Created by Ljh
 */

@Service
public interface UserService {

    User checkUser(String username,String password);

}
