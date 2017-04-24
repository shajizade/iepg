package ir.samatco.iepg.service;

import ir.samatco.iepg.api.NomineeList;
import ir.samatco.iepg.entity.Nominee;
import ir.samatco.iepg.entity.NomineeHistory;
import ir.samatco.iepg.entity.UserVote;
import ir.samatco.iepg.entity.Voter;
import ir.samatco.iepg.repo.NomineeHistoryRepository;
import ir.samatco.iepg.repo.NomineeRepository;
import ir.samatco.iepg.repo.UserVoteRepository;
import ir.samatco.iepg.repo.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Saeed
 *         Date: 4/17/2017
 */
@Service
public class FullService {
    public static final int STARTING_POINTS = 2000;
    @Autowired
    private NomineeRepository nomineeRepository;
    @Autowired
    private VoterRepository voterRepository;
    @Autowired
    private UserVoteRepository userVoteRepository;
    @Autowired
    private NomineeHistoryRepository nomineeHistoryRepository;

    //public static List<Nominee> nomineeList;

    public NomineeList getAllNominees(){
        return new NomineeList(nomineeRepository.findAll());
    }
    public Nominee getNomineeByName(String name){
        return getNomineeByName(name,getAllNominees());
    }
    public Nominee getNomineeByName(String name,NomineeList allNominees){
        for (Nominee allNominee : allNominees.getNomineeList()) {
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
        boolean nonValidLabel=false;
        for (Nominee nominee : allNominees) {
            if(!nominee.getValid() && !nonValidLabel) {
                nomineesText.append("\n" + "لیست سهام نامزدهایی که در بازی ارزشی ندارد:" + "\n");
                nonValidLabel=true;
            }
            nomineesText.append("- "+ nominee.getName());
            nomineesText.append(" ( ");
            nomineesText.append("قیمت خرید: ");
            nomineesText.append(nominee.getBuyPrice());
            nomineesText.append("| قیمت فروش: ");
            nomineesText.append(nominee.getSellPrice());
            nomineesText.append(" )");
            nomineesText.append("\n");
        }
        return nomineesText.toString();
    }
    public String nomineeListString(){
        return nomineeListString(getAllNominees().getNomineeList());
    }

    public void startVoter(Voter voter) {
        Voter dbVoter = voterRepository.findOne(voter.getId());
        if (dbVoter==null){
            voter.setPoints(STARTING_POINTS);
            voterRepository.save(voter);
        }
    }
    @Transactional
    public String buyVote(Long voterId, String nomineeName, int voteNumber) {
        if (voteNumber==0)
            return "عدد وارد شده صحیح نیست";
        Voter voter = voterRepository.findOne(voterId);
        NomineeList nomineeList=getAllNominees();
        Nominee nominee = getNomineeByName(nomineeName,nomineeList);
        if (nominee==null)
            return "نامزد مورد نظر شما در این بازی وجود ندارد!";
        if (!nominee.getValid())
            return "سهام مربوط به این نامزد دیگر در این بازی قابلیت خرید و فروش ندارد و بی‌ارزش است!";
        if (voter.getPoints()>=nominee.getBuyPrice()){
            voter.setPoints(voter.getPoints()-(nominee.getBuyPrice()*voteNumber));
            UserVote userVote = userVoteRepository.getByVoterIdAndNomineeId(voter.getId(), nominee.getId());
            if (userVote==null){
                userVote = new UserVote();
                userVote.setVoter(voter);
                userVote.setNominee(nominee);
                userVote.setNumber(voteNumber);
            }else {
                userVote.setNumber(userVote.getNumber()+voteNumber);
            }
            nominee.setNumber(nominee.getNumber()+voteNumber);
            userVoteRepository.save(userVote);
            voterRepository.save(voter);
            nomineeRepository.save(nominee);
            StringBuilder sb = new StringBuilder("سهام خریداری شد");
            sb.append("\n");
            sb.append("موجودی شما: ");
            sb.append(voter.getPoints());
            sb.append(" امتیاز");
            return sb.toString();
        }
        return "موجودی امتیاز شما ناکافی است";
    }

    public String sellVote(Long voterId, String nomineeName) {
        NomineeList nomineeList = getAllNominees();
        Nominee nominee = getNomineeByName(nomineeName,nomineeList);
        if (nominee==null)
            return "شما سهام این نامزد را در سبد سهام خود ندارید";
        if (!nominee.getValid())
            return "سهام مربوط به این نامزد دیگر در این بازی قابلیت خرید و فروش ندارد و بی‌ارزش است!";
        UserVote userVote = userVoteRepository.getByVoterIdAndNomineeId(voterId, nominee.getId());
        if (userVote!=null && userVote.getNumber()>0){
            Voter voter = voterRepository.findOne(voterId);

            userVote.setNumber(userVote.getNumber()-1);
            voter.setPoints(voter.getPoints()+nominee.getSellPrice());
            nominee.setNumber(nominee.getNumber()-1);

            userVoteRepository.save(userVote);
            voterRepository.save(voter);
            nomineeRepository.save(nominee);
            userVoteRepository.save(userVote);
            voterRepository.save(voter);
            nomineeRepository.save(nominee);
            StringBuilder sb = new StringBuilder("سهام فروخته شد");
            sb.append("\n");
            sb.append("موجودی شما: ");
            sb.append(voter.getPoints());
            sb.append(" امتیاز");
            return sb.toString();
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
        NomineeList allNomineesList = getAllNominees();
        List<Nominee> allNominees = allNomineesList.getNomineeList();
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
                Nominee validNominee = getNomineeByName(userVote.getNominee().getName(), allNomineesList);
                if (validNominee.getValid())
                {
                    nomineesText.append(" سهم به ارزش ");
                    nomineesText.append((validNominee==null)?0:validNominee.getSellPrice());
                }else
                {
                    nomineesText.append(" سهم بدون ارزش");
                }
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

    public void saveReport() {
        NomineeList allNominees = getAllNominees();
        List<NomineeHistory> historyList= new ArrayList<>();
        for (Nominee nominee : allNominees.getNomineeList()) {
            NomineeHistory nomineeHistory= new NomineeHistory();
            nomineeHistory.setNominee(nominee);
            nomineeHistory.setPrice(nominee.getBuyPrice());
            nomineeHistory.setReportTime(new Date());
            historyList.add(nomineeHistory);
        }
        Iterable<NomineeHistory> save = nomineeHistoryRepository.save(historyList);
        System.out.printf("");
    }
}
