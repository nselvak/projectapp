package vttp.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.project.model.User;
import static vttp.project.repository.Queries.*;

@Repository
public class UserRepo {

    @Autowired
    private JdbcTemplate template;

    public Integer checkUserPW(User user) {
        SqlRowSet rs = template.queryForRowSet( SQL_SELECT_USER_PW, user.getUsername(), user.getPassword());
        if (!rs.next()) {
            return 0;
            }
        return rs.getInt("count");
    }

    public Integer checkUser(User user) {
        SqlRowSet rs = template.queryForRowSet( SQL_SELECT_USER, user.getUsername());
        if (!rs.next()) {
            return 0;
            }
        return rs.getInt("count");
    }

    public boolean save(final User user){
        int added = template.update(SQL_INSERT_INTO_USER, user.getUsername(), user.getPassword());

        return added >0;
        
    }
    
}
