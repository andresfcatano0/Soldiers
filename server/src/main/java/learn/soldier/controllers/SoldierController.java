package learn.soldier.controllers;

import learn.soldier.domain.SoldierService;
import learn.soldier.domain.Result;
import learn.soldier.models.Soldier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@CrossOrigin
@RequestMapping("/api/soldier")
public class SoldierController {

    private final SoldierService service;

    public SoldierController(SoldierService service) {
        this.service = service;
    }

    @GetMapping
    public List<Soldier> findAll() {
        return service.findAll();
    }

//    @GetMapping("/{soldierId}")
//    public Soldier findById(@PathVariable int soldierId) {
//        return service.findById(soldierId);
//    }

    @GetMapping("/{soldierId}")
    public ResponseEntity<Soldier> findById(@PathVariable int soldierId) {
        Soldier soldier = service.findById(soldierId);
        if (soldier != null) {
            return new ResponseEntity<>(soldier, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Soldier soldier) {
        Result<Soldier> result = service.add(soldier);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{soldierId}")
    public ResponseEntity<Object> update(@PathVariable int soldierId, @RequestBody Soldier soldier) {
        if (soldierId != soldier.getSoldierId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Soldier> result = service.update(soldier);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{soldierId}")
    public ResponseEntity<Void> deleteById(@PathVariable int soldierId) {
        if (service.deleteById(soldierId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
