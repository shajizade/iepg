package ir.samatco.iepg.service;

import ir.samatco.iepg.entity.Nominee;
import ir.samatco.iepg.entity.UserVote;
import ir.samatco.iepg.entity.Voter;
import ir.samatco.iepg.repo.NomineeRepository;
import ir.samatco.iepg.repo.UserVoteRepository;
import ir.samatco.iepg.repo.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Saeed
 *         Date: 4/17/2017
 */
@Service
public class FullService {
    public static final int STARTING_POINTS = 10000;
    @Autowired
    private NomineeRepository nomineeRepository;
    @Autowired
    private VoterRepository voterRepository;
    @Autowired
    private UserVoteRepository userVoteRepository;

    //public static List<Nominee> nomineeList;

    public List<Nominee> getAllNominees(){
        List<Nominee> nominees = nomineeRepository.findAll();
        Double bottom = 0D;
/*
        for (Nominee nominee : nominees) {
            bottom += Math.pow(Math.E, nominee.getNumber());
        }
        for (Nominee nominee : nominees) {
            nominee.setPrice(new Double(Math.pow(Math.E, nominee.getNumber())*1000 / bottom).intValue());
        }
*/
        for (Nominee nominee : nominees) {
            bottom +=  nominee.getEffectiveNumber();
        }
        for (Nominee nominee : nominees) {
            nominee.setPrice(new Double((nominee.getEffectiveNumber()/bottom) *1000).intValue());
        }
        return nominees;
    }
    public Nominee getNomineeByName(String name){
        return getNomineeByName(name,getAllNominees());
    }
    public Nominee getNomineeByName(String name,List<Nominee> allNominees){
        for (Nominee allNominee : allNominees) {
            if (allNominee.getName().equals(name))
                return allNominee;
        }
        return null;
    }

    public String nomineeListString(List<Nominee> allNominees){
        StringBuilder nomineesText = new StringBuilder();
        nomineesText.append("سهام نامزدها و ارزش فعلی آنها:");
        nomineesText.append("\n");
        nomineesText.append("\n");
        for (Nominee nominee : allNominees) {
            nomineesText.append("- "+ nominee.getName());
            nomineesText.append(" ( ");
            nomineesText.append("ارزش :");
            nomineesText.append(nominee.getPrice());
            nomineesText.append(" )");

            nomineesText.append("\n");
        }
        return nomineesText.toString();
    }
    public String nomineeListString(){
        return nomineeListString(getAllNominees());
    }

    public void startVoter(Voter voter) {
        Voter dbVoter = voterRepository.findOne(voter.getId());
        if (dbVoter==null){
            voter.setPoints(STARTING_POINTS);
            voterRepository.save(voter);
        }
    }
    @Transactional
    public String buyVote(Long voterId, String nomineeName) {
        Voter voter = voterRepository.findOne(voterId);
        Nominee nominee = getNomineeByName(nomineeName);
        if (nominee==null)
            return "نامزد مورد نظر شما در این بازی وجود ندارد!";
        if (voter.getPoints()>=nominee.getPrice()){
            voter.setPoints(voter.getPoints()-nominee.getPrice());
            UserVote userVote = userVoteRepository.getByVoterIdAndNomineeId(voter.getId(), nominee.getId());
            if (userVote==null){
                userVote = new UserVote();
                userVote.setVoter(voter);
                userVote.setNominee(nominee);
                userVote.setNumber(1);
            }else {
                userVote.setNumber(userVote.getNumber()+1);
            }
            nominee.setNumber(nominee.getNumber()+1);
            userVoteRepository.save(userVote);
            voterRepository.save(voter);
            nomineeRepository.save(nominee);
            return "سهام خریداری شد";
        }
        return "موجودی امتیاز شما ناکافی است";
    }

    public String sellVote(Long voterId, String nomineeName) {
        Voter voter = voterRepository.findOne(voterId);
        Nominee nominee = getNomineeByName(nomineeName);
        UserVote userVote1 = userVoteRepository.getByVoterIdAndNomineeId(voter.getId(), nominee.getId());
        if (userVote1!=null && userVote1.getNumber()>0){
            userVote1.setNumber(userVote1.getNumber()-1);
            voter.setPoints(voter.getPoints()+nominee.getPrice());
            nominee.setNumber(nominee.getNumber()-1);
            userVoteRepository.save(userVote1);
            voterRepository.save(voter);
            nomineeRepository.save(nominee);
            return "سهام فروخته شد";
        }
        return "شما سهام این نامزد را در سبد سهام خود ندارید";
    }

    public List<UserVote> getUserVotes(Voter from) {
        List<UserVote> userVotes = userVoteRepository.getUserVotes(from.getId());
        return userVotes;
    }

    public List<UserVote> getUserNoneZeroVotes(Voter from) {
        List<UserVote> userVotes = userVoteRepository.getUserNonZeroVotes(from.getId());
        return userVotes;
    }

    public String getUserVotesString(List<UserVote> userVotes, Long voterId) {
        StringBuilder nomineesText = new StringBuilder();
        if (userVotes.isEmpty()){
            nomineesText.append("سبد سهام شما خالی است.");
        }else {
            nomineesText.append("سبد سهام شما:");
            nomineesText.append("\n");
            for (UserVote userVote : userVotes) {
                nomineesText.append("- "+userVote.getNominee().getName());
                nomineesText.append(" ( ");
                nomineesText.append("تعداد سهام :");
                nomineesText.append(userVote.getNumber());
                nomineesText.append(" )");
                nomineesText.append("\n");
            }
        }
        nomineesText.append("\n");
        Voter voter = voterRepository.findOne(voterId);
        nomineesText.append("موجودی شما : ");
        nomineesText.append(voter.getPoints());
        nomineesText.append(" امتیاز");

        return nomineesText.toString();
    }

}
