package ir.samatco.iepg.repo;

import ir.samatco.iepg.entity.Nominee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rameri on 2/27/17.
 */
public interface NomineeRepository extends PagingAndSortingRepository<Nominee, Integer>, JpaSpecificationExecutor<Nominee> {
    List<Nominee> findAll();
    Nominee findByName(String name);
}
