package cn.delei.spring.boot.transaction.domain.user.repository;

import cn.delei.spring.boot.transaction.domain.user.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void add(UserEntity entity) {
        String sql = "INSERT INTO user_student(vc_name) VALUES (?)";
        jdbcTemplate.update(sql, entity.getName());
    }

    public void update(UserEntity entity) {
        String sql = "UPDATE user_student SET vc_name = ?";
        jdbcTemplate.update(sql, entity.getName());
    }

    public UserEntity selectByPrimaryKey(Long key) {
        String sql = "SELECT l_id , vc_name FROM user_student WHERE l_id = ?";
        UserEntity result = jdbcTemplate.queryForObject(sql, new Object[]{key}, new UserRowMapper());
        return result;
    }

    public void deleteByPrimaryKey(Long key) {
        String sql = "DELETE FROM user_student WHERE l_id = ?";
        jdbcTemplate.update(sql, key);
    }

    class UserRowMapper implements RowMapper<UserEntity> {
        @Override
        public UserEntity mapRow(ResultSet resultSet, int i) throws SQLException {
            UserEntity entity = new UserEntity();
            entity.setId(resultSet.getLong(0));
            entity.setName(resultSet.getString(1));
            return entity;
        }
    }
}
