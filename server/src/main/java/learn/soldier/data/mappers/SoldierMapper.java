package learn.soldier.data.mappers;

import learn.soldier.models.Soldier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SoldierMapper implements RowMapper<Soldier> {

    @Override
    public Soldier mapRow(ResultSet resultSet, int i) throws SQLException {
        Soldier soldier = new Soldier();
        soldier.setSoldierId(resultSet.getInt("soldier_id"));
        soldier.setFirstName(resultSet.getString("first_name"));
        soldier.setMiddleName(resultSet.getString("middle_name"));
        soldier.setLastName(resultSet.getString("last_name"));
        if (resultSet.getDate("dob") != null) {
            soldier.setDob(resultSet.getDate("dob").toLocalDate());
        }
        soldier.setHeightInInches(resultSet.getInt("height_in_inches"));
        return soldier;
    }
}
