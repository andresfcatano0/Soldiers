package learn.soldier.domain;

import learn.soldier.data.SoldierRepository;
import learn.soldier.models.Soldier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SoldierService {

    private final SoldierRepository repository;

    public SoldierService(SoldierRepository repository) {
        this.repository = repository;
    }

    public List<Soldier> findAll() {
        return repository.findAll();
    }

    public Soldier findById(int soldierId) {
        return repository.findById(soldierId);
    }

    public Result<Soldier> add(Soldier soldier) {
        Result<Soldier> result = validate(soldier);
        if (!result.isSuccess()) {
            return result;
        }

        if (soldier.getSoldierId() != 0) {
            result.addMessage("soldierId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        soldier = repository.add(soldier);
        result.setPayload(soldier);
        return result;
    }

    public Result<Soldier> update(Soldier soldier) {
        Result<Soldier> result = validate(soldier);
        if (!result.isSuccess()) {
            return result;
        }

        if (soldier.getSoldierId() <= 0) {
            result.addMessage("soldierId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(soldier)) {
            String msg = String.format("soldierId: %s, not found", soldier.getSoldierId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int soldierId) {
        return repository.deleteById(soldierId);
    }

    private Result<Soldier> validate(Soldier soldier) {
        Result<Soldier> result = new Result<>();
        if (soldier == null) {
            result.addMessage("soldier cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(soldier.getFirstName())) {
            result.addMessage("firstName is required", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(soldier.getLastName())) {
            result.addMessage("lastName is required", ResultType.INVALID);
        }

        if (soldier.getDob() != null && soldier.getDob().isAfter(LocalDate.now().minusYears(12))) {
            result.addMessage("soldier younger than 12 are not allowed", ResultType.INVALID);
        }

        if (soldier.getHeightInInches() < 36 || soldier.getHeightInInches() > 96) {
            result.addMessage("height must be between 36 and 96 inches", ResultType.INVALID);
        }

        return result;
    }
}
