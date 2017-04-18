package ir.samatco.iepg.repo;

import ir.samatco.iepg.entity.Nominee;
import ir.samatco.iepg.entity.Voter;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by rameri on 2/27/17.
 */
public interface VoterRepository extends PagingAndSortingRepository<Voter, Long>, JpaSpecificationExecutor<Long> {
    List<Voter> findAll();
}
