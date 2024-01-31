package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper
{
    @Insert("INSERT INTO USER (name, account_id, token, gmt_create, gmt_modified) VALUES (#{name}, #{account_id}, #{token}, #{gmt_create}, #{gmt_modified})")
    void insert(User user);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User findByToken(@Param("token") String token);
}