package ir.samatco.iepg.repo;

import ir.samatco.iepg.entity.Nominee;
import ir.samatco.iepg.entity.UserVote;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by rameri on 2/27/17.
 */
public interface UserVoteRepository extends PagingAndSortingRepository<UserVote, Long>, JpaSpecificationExecutor<UserVote> {
    @Query(value = "select u from UserVote u where u.voter.id=:voterId")
    List<UserVote> getUserVotes(@Param("voterId")Long voterId);
}
