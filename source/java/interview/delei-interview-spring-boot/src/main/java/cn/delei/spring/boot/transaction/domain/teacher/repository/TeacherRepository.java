package cn.delei.spring.boot.transaction.domain.teacher.repository;

import cn.delei.spring.boot.transaction.domain.teacher.entity.TeacherEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TeacherRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void add(TeacherEntity entity) {
        String sql = "INSERT INTO user_teacher(vc_name) VALUES (?)";
        jdbcTemplate.update(sql, entity.getName());
    }

    public void update(TeacherEntity entity) {
        String sql = "UPDATE user_teacher SET vc_name = ?";
        jdbcTemplate.update(sql, entity.getName());
    }

    public TeacherEntity selectByPrimaryKey(Long key) {
        String sql = "SELECT l_id , vc_name FROM user_teacher WHERE l_id = ?";
        TeacherEntity result = jdbcTemplate.queryForObject(sql, new Object[]{key}, new TeacherRowMapper());
        return result;
    }

    public void deleteByPrimaryKey(Long key) {
        String sql = "DELETE FROM user_teacher WHERE l_id = ?";
        jdbcTemplate.update(sql, key);
    }

    class TeacherRowMapper implements RowMapper<TeacherEntity> {
        @Override
        public TeacherEntity mapRow(ResultSet resultSet, int i) throws SQLException {
            TeacherEntity entity = new TeacherEntity();
            entity.setId(resultSet.getLong(0));
            entity.setName(resultSet.getString(1));
            return entity;
        }
    }
}
