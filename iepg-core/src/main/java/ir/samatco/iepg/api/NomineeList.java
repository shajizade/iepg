package ir.samatco.iepg.api;

import ir.samatco.iepg.entity.Nominee;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Saeed
 *         Date: 4/23/2017
 */
public class NomineeList {
    List<Nominee> nomineeList;
    Double bottom=0D;

    public NomineeList(List<Nominee> nomineeList) {
        this.nomineeList = nomineeList;
        reCalculate();
    }

    public List<Nominee> getNomineeList() {
        return nomineeList;
    }

    public void sort(){
        List<Nominee> tmpList= new ArrayList<>();
        while (!nomineeList.isEmpty()){
            Nominee maxNominee = getMaxNominee(nomineeList);
            tmpList.add(maxNominee);
            nomineeList.remove(maxNominee);
        }
        nomineeList = tmpList;
    }

    private Nominee getMaxNominee(List<Nominee> nomineeList) {
        Nominee resultNominee = nomineeList.get(0);
        for (Nominee nominee : nomineeList) {
            if (nominee.getBuyPrice()>resultNominee.getBuyPrice())
            {
                resultNominee=nominee;
            }
        }
        return resultNominee;
    }

    public void reCalculate(){
        bottom=0D;
        for (Nominee nominee : nomineeList) {
            if (nominee.getValid())
                bottom +=  nominee.getEffectiveNumber();
        }
        for (Nominee nominee : nomineeList) {
            if (nominee.getValid()) {
                int buyPrice = new Double((nominee.getEffectiveNumber() / bottom) * 1000).intValue();
                nominee.setBuyPrice((buyPrice > 0) ? buyPrice : 1);
                int sellPrice = new Double(((nominee.getEffectiveNumber() - 1) / (bottom - 1)) * 1000).intValue();
                nominee.setSellPrice((sellPrice > 0) ? sellPrice : 1);
            }else{
                nominee.setBuyPrice(0);
                nominee.setSellPrice(0);
            }
        }
/*
        for (Nominee nominee : nominees) {
            bottom += Math.pow(Math.E, nominee.getNumber());
        }
        for (Nominee nominee : nominees) {
            nominee.setBuyPrice(new Double(Math.pow(Math.E, nominee.getNumber())*1000 / bottom).intValue());
        }
*/
    }
}
