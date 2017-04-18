package ir.samatco.iepg.service;

import ir.samatco.iepg.api.Keyboard;
import ir.samatco.iepg.api.ReplyKeyboard;
import ir.samatco.iepg.entity.Nominee;
import ir.samatco.iepg.entity.Voter;
import ir.samatco.iepg.repo.NomineeRepository;
import ir.samatco.iepg.repo.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Saeed
 *         Date: 4/17/2017
 */
@Service
public class FullService {
    @Autowired
    private NomineeRepository nomineeRepository;
    @Autowired
    private VoterRepository voterRepository;

    public List<Nominee> getAllNominees(){
        return nomineeRepository.findAll();
    }
    public String nomineeListString(List<Nominee> allNominees){
        StringBuilder nomineesText = new StringBuilder();
        nomineesText.append("\n");
        for (Nominee nominee : allNominees) {
            nomineesText.append(nominee.getName());
            nomineesText.append("-");
            nomineesText.append("ارزش :");
            nomineesText.append(nominee.getPrice());
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
            voter.setPoints(10000);
            voterRepository.save(voter);
        }
    }
}
