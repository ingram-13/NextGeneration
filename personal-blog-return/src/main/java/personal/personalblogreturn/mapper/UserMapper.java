package personal.personalblogreturn.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import personal.personalblogreturn.pojo.User;

/**
 * Created by Ljh
 */
@Repository
@Mapper
public interface UserMapper {
        @Select("select * from t_user where username = #{username} and password = #{password}")
        User checkUser(@Param("username") String username, @Param("password") String password);

}
