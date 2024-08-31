package learn.soldier.data;

import learn.soldier.data.mappers.SoldierMapper;
import learn.soldier.models.Soldier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SoldierJdbcTemplateRepository implements SoldierRepository {

    private final JdbcTemplate jdbcTemplate;

    public SoldierJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Soldier> findAll() {
        final String sql = "select soldier_id, first_name, middle_name, last_name, dob, height_in_inches "
                + "from soldier limit 1000;";
        return jdbcTemplate.query(sql, new SoldierMapper());
    }

    @Override
    @Transactional
    public Soldier findById(int soldierId) {

        final String sql = "select soldier_id, first_name, middle_name, last_name, dob, height_in_inches "
                + "from soldier "
                + "where soldier_id = ?;";

        Soldier soldier = jdbcTemplate.query(sql, new SoldierMapper(), soldierId).stream()
                .findAny().orElse(null);

        return soldier;
    }

    @Override
    public Soldier add(Soldier soldier) {

        final String sql = "insert into soldier (first_name, middle_name, last_name, dob, height_in_inches) "
                + " values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, soldier.getFirstName());
            ps.setString(2, soldier.getMiddleName());
            ps.setString(3, soldier.getLastName());
            ps.setDate(4, soldier.getDob() == null ? null : Date.valueOf(soldier.getDob()));
            ps.setInt(5, soldier.getHeightInInches());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        soldier.setSoldierId(keyHolder.getKey().intValue());
        return soldier;
    }

    @Override
    public boolean update(Soldier soldier) {

        final String sql = "update soldier set "
                + "first_name = ?, "
                + "middle_name = ?, "
                + "last_name = ?, "
                + "dob = ?, "
                + "height_in_inches = ? "
                + "where soldier_id = ?;";

        return jdbcTemplate.update(sql,
                soldier.getFirstName(),
                soldier.getMiddleName(),
                soldier.getLastName(),
                soldier.getDob(),
                soldier.getHeightInInches(),
                soldier.getSoldierId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int soldierId) {
        return jdbcTemplate.update("delete from soldier where soldier_id = ?;", soldierId) > 0;
    }

}
