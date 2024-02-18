package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper
{
    @Insert("INSERT INTO USER (name, account_id, token, gmt_create, gmt_modified, avatar_url) VALUES (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("SELECT * FROM USER WHERE ID = #{id}")
    User findById(@Param("id") Integer id);

    @Select("SELECT * FROM USER WHERE account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("UPDATE USER SET name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} WHERE id = #{id}")
    void update(User user);
}
