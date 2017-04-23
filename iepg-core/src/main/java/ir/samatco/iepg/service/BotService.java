package ir.samatco.iepg.service;

import ir.samatco.iepg.api.*;
import ir.samatco.iepg.entity.Nominee;
import ir.samatco.iepg.entity.UserVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
@Service
public class BotService {
    public static final String BOT_URL = "https://api.telegram.org/bot352284833:AAGdeUra0P_hA4MbyukewfbrA3wgr4FcmO4/";
    public static final String BUY_COMMAND = "خرید";
    public static final String SELL_COMMAND = "فروش";
    public static final String LIST_COMMAND = "لیست نامزدها";
    public static final String RULES_COMMAND = "قوانین";
    public static final String ASSETS_COMMAND = "سبد سهام من";
    public static final String MAIN_MENU_COMMAND = "بازگشت به منوی اصلی";
    public static final String START_COMMAND = "/start";
    private static final String GAME_RULES = "قوانین بازی:";
    public static final String VOTE_LABLE = " سهام ";
    public static final String WELCOME_TEXT = "به بازی پیش‌بینی انتخابات خوش آمدید. \n پیشنهاد می‌کنیم با زدن دکمه‌ی "+RULES_COMMAND+" با این بازی بیشتر آشنا شوید.";
    public static final String MAIN_MENU_TEXT = "عملیات مورد نظر را انتخاب کنید";
    public static final String VOTE_BUY_SEPERATOR = "-";
    public static final String VOTE_NUMBER_2 = "۲ سهم";
    public static final String VOTE_NUMBER_1 = "۱ سهم";
    public static final String VOTE_NUMBER_5 = "۵ سهم";
    public static final String VOTE_NUMBER_3 = "۳ سهم";
    public static Long lastUpdateId =0L;
    @Autowired
    private FullService fullService;

    @Scheduled(fixedDelay = 500)
    public void response(){
        System.out.println("strat");
        RestTemplate restTemplate= new RestTemplate();
        UpdateResponse response = restTemplate.getForObject(BOT_URL + "getUpdates?offset="+lastUpdateId, UpdateResponse .class);
        List<Update> results = response.getResult();
        if (response.isOk() && results.size()>1){
            Boolean skipFirst=true;
            for (Update result : results) {
                if(skipFirst)
                    skipFirst=false;
                else {
                    if (lastUpdateId < result.getUpdate_id())
                        lastUpdateId = result.getUpdate_id();
                    try{
                        handleMessage(result.getMessage());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.println("sent");
                }
            }
        }else{
            System.out.println("Empty or Not OK");
        }
        System.out.println("end");
    }

    private void handleMessage(Message message) {
        RestTemplate restTemplate = new RestTemplate() ;
        SendMessageRequest request = new SendMessageRequest();
        request.setChat_id(message.getFrom().getId());

        String text = message.getText().trim();
        if (text.startsWith(LIST_COMMAND)) {
            NomineeList nomineeList = fullService.getAllNominees();
            nomineeList.sort();
            List<Nominee> allNominees = nomineeList.getNomineeList();
            request.setText(fullService.nomineeListString(allNominees));
            request.setReply_markup(getBaseMenuKeyboard());
        }else if (text.startsWith(ASSETS_COMMAND)) {
            List<UserVote> userVotes = fullService.getUserNoneZeroVotes(message.getFrom());
            request.setText(fullService.getUserVotesString(userVotes,message.getFrom().getId()));
            request.setReply_markup(getBaseMenuKeyboard());
        }
        else if (text.startsWith(RULES_COMMAND)) {
            request.setText(GAME_RULES);
            request.setReply_markup(getBaseMenuKeyboard());
        }
        else if (text.startsWith(BUY_COMMAND)) {
            if (text.length()==BUY_COMMAND.length())
            {
                List<Nominee> allNominees = fullService.getAllNominees().getNomineeList();
                request.setText("سهام مورد نظر برای خرید را انتخاب کنید");
                request.setReply_markup(getBuyNomineeListKeyboard(allNominees));
            }else if(!text.contains(VOTE_BUY_SEPERATOR )){
                String nomineeName=text.substring(BUY_COMMAND.length()+VOTE_LABLE.length());
                request.setText("تعداد سهام برای خرید را مشخص کنید");
                request.setReply_markup(getBuyNomineeNumberKeyboard(nomineeName));
            }else{
                String[] split = text.split(VOTE_BUY_SEPERATOR);
                String nomineeName=split[0].trim().substring(BUY_COMMAND.length()+VOTE_LABLE.length());
                request.setText(fullService.buyVote(message.getFrom().getId(),nomineeName,voteNumber(split[1].trim())));
                request.setReply_markup(getBaseMenuKeyboard());
            }
        }else if (text.startsWith(SELL_COMMAND)) {
            if (text.length()==SELL_COMMAND.length())
            {
                List<UserVote> userVotes = fullService.getUserNoneZeroVotes(message.getFrom());
                if (userVotes.isEmpty()){
                    request.setText("شما سهامی برای فروش ندارید");
                }else {
                    request.setText("سهام مورد نظر برای فروش را انتخاب کنید");
                }
                request.setReply_markup(getSellNomineeListKeyboard(userVotes));
            }else{
                String nomineeName=text.substring(SELL_COMMAND.length()+VOTE_LABLE.length());
                request.setText(fullService.sellVote(message.getFrom().getId(),nomineeName));
                request.setReply_markup(getBaseMenuKeyboard());
            }
        }else if(text.startsWith(START_COMMAND)) {
            fullService.startVoter(message.getFrom());
            request.setText(WELCOME_TEXT);
            request.setReply_markup(getBaseMenuKeyboard());
        }else if(text.startsWith(MAIN_MENU_COMMAND)) {
            request.setText(MAIN_MENU_TEXT);
            request.setReply_markup(getBaseMenuKeyboard());
        }
        else if(text.startsWith("اکو")) {
            StringBuilder nomineesText = new StringBuilder(text.substring(3));
            nomineesText.append("\n");
            request.setText(nomineesText.toString());
        }else {
            request = null;
        }
        if (request!=null) {
            if (request.getReply_markup() == null)
                request.setReply_markup(new ReplyRemoveKeyboard());
            restTemplate.postForEntity(BOT_URL + "sendMessage", request, SendMessageResponse.class);
        }
    }

    private int voteNumber(String voteNumberString) {
        if (VOTE_NUMBER_1.equals(voteNumberString))
            return 1;
        if (VOTE_NUMBER_2.equals(voteNumberString))
            return 2;
        if (VOTE_NUMBER_3.equals(voteNumberString))
            return 3;
        if (VOTE_NUMBER_5.equals(voteNumberString))
            return 5;
        return 0;
    }

    private ReplyKeyboard getSellNomineeListKeyboard(List<UserVote> userVotes) {
        ReplyKeyboard replyKeyboard= new ReplyKeyboard(userVotes.size()+1);
        int index=0;
        for (UserVote userVote : userVotes) {
            replyKeyboard.getKeyboard().get(index).add(new Keyboard(SELL_COMMAND+ VOTE_LABLE +userVote.getNominee().getName()));
            index++;
        }
        replyKeyboard.getKeyboard().get(index).add(new Keyboard(MAIN_MENU_COMMAND));
        return replyKeyboard;
    }
    private ReplyKeyboard getBuyNomineeListKeyboard(List<Nominee> allNominees) {
        int numberOfRows = (allNominees.size()/2) + 1;
        ReplyKeyboard replyKeyboard= new ReplyKeyboard(numberOfRows);
        int index=0;
        int rowNumber=0;
        for (Nominee nominee : allNominees) {
            replyKeyboard.getKeyboard().get(rowNumber).add(new Keyboard(BUY_COMMAND+ VOTE_LABLE +nominee.getName()));
            index++;
            if (index % 2 ==0){
                rowNumber++;
            }

        }
        replyKeyboard.getKeyboard().get(rowNumber).add(new Keyboard(MAIN_MENU_COMMAND));
        return replyKeyboard;
    }
    private ReplyKeyboard getBuyNomineeNumberKeyboard(String nomineeName) {
        ReplyKeyboard replyKeyboard= new ReplyKeyboard(3);
        replyKeyboard.getKeyboard().get(0).add(new Keyboard(BUY_COMMAND+ VOTE_LABLE +nomineeName+" " + VOTE_BUY_SEPERATOR +" " + VOTE_NUMBER_2));
        replyKeyboard.getKeyboard().get(0).add(new Keyboard(BUY_COMMAND+ VOTE_LABLE +nomineeName+" " + VOTE_BUY_SEPERATOR +" " + VOTE_NUMBER_1));
        replyKeyboard.getKeyboard().get(1).add(new Keyboard(BUY_COMMAND+ VOTE_LABLE +nomineeName+" " + VOTE_BUY_SEPERATOR +" " + VOTE_NUMBER_5));
        replyKeyboard.getKeyboard().get(1).add(new Keyboard(BUY_COMMAND+ VOTE_LABLE +nomineeName+" " + VOTE_BUY_SEPERATOR +" " + VOTE_NUMBER_3));
        replyKeyboard.getKeyboard().get(2).add(new Keyboard(MAIN_MENU_COMMAND));
        return replyKeyboard;
    }
    private ReplyKeyboard getBaseMenuKeyboard() {
        ReplyKeyboard replyKeyboard= new ReplyKeyboard(2);
        replyKeyboard.getKeyboard().get(0).add(new Keyboard(BUY_COMMAND));
        replyKeyboard.getKeyboard().get(0).add(new Keyboard(SELL_COMMAND));
        replyKeyboard.getKeyboard().get(1).add(new Keyboard(LIST_COMMAND));
        replyKeyboard.getKeyboard().get(1).add(new Keyboard(RULES_COMMAND));
        replyKeyboard.getKeyboard().get(1).add(new Keyboard(ASSETS_COMMAND));
        return replyKeyboard;
    }
}
