package ir.samatco.iepg.repo;

import ir.samatco.iepg.entity.Nominee;
import ir.samatco.iepg.entity.NomineeHistory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by rameri on 2/27/17.
 */
public interface NomineeHistoryRepository extends PagingAndSortingRepository<NomineeHistory, Integer>, JpaSpecificationExecutor<NomineeHistory> {
}
