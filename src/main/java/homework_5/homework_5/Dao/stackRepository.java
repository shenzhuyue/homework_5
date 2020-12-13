package homework_5.homework_5.Dao;
import homework_5.homework_5.stack;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface stackRepository extends CrudRepository<stack,Integer> {
    @Override
    List<stack> findAll();
}
