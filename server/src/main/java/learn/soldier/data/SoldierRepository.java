package learn.soldier.data;

import learn.soldier.models.Soldier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SoldierRepository {
    List<Soldier> findAll();

    Soldier findById(int soldierId);

    Soldier add(Soldier soldier);

    boolean update(Soldier soldier);

    @Transactional
    boolean deleteById(int soldierId);
}
