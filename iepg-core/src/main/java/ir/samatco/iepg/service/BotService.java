package ir.samatco.iepg.service;

import ir.samatco.iepg.api.*;
import ir.samatco.iepg.entity.Nominee;
import ir.samatco.iepg.entity.UserVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
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
    public static final String LIST_COMMAND = "ارزش لحظه‌ای سهام نامزدها";
    public static final String RULES_COMMAND = "مشاهده‌ی قوانین";
    public static final String TEXT_RULES_COMMAND = "مشاهده‌ی قوانین به شکل متنی";
    public static final String ASSETS_COMMAND = "سبد سهام من";
    public static final String MAIN_MENU_COMMAND = "بازگشت به منوی اصلی";
    public static final String START_COMMAND = "/start";
    private static final String GAME_RULES = " برای مشاهده‌ی قوانین بازی بر روی دکمه‌ی INSTANT VIEW زیر همین پست کلیک کنید."+"\n"+"اگر این دکمه را نمی‌بینید گزینه‌ی «"+TEXT_RULES_COMMAND+"» را انتخاب کنید."+"\n\n"+"https://goo.gl/V0faEZ";
    private static final String GAME_RULES_TEXT = "به بازی پیش\u200Cبینی انتخابات ریاست جمهوری ایران خوش آمدید. در اینجا کسی بهتر بازی می\u200Cکند که هم شم اقتصادی خوبی داشته باشد و هم هوش پیش\u200Cبینی خوب و بتواند حدس بزند کدام یک از نامزدهای انتخابات ریاست جمهوری برنده\u200Cی انتخابات خواهد بود."
            +"\n"
            +"\n"
            +"- بازی؟"
            +"\n"
            +"در آغاز بازی به شما 2000 امتیاز داده می\u200Cشود. این امتیاز در حقیقت سرمایه\u200Cی اولیه\u200Cی شماست. شما می\u200Cتوانید با این امتیاز در این بازی به خرید و فروش سهام مشغول شوید."
            +"\n"
            +"\n"
            +"- سهام؟"
            +"\n"
            +"بله! شما می توانید با امتیازی که دارید روی برنده شدن نامزدهای ریاست جمهوری سرمایه\u200Cگذاری کنید. برای این کار سهام آن نامزد را می\u200Cخرید. پس از مشخص شدن نتایج انتخابات، به ازای هر سهمی که از نامزد برنده در سبد سهام شما باشد به شما 1000 امتیاز داده خواهد شد."
            +"\n"
            +"\n"
            + "- اگر نامزدی انصراف بدهد چطور؟"
            +"\n"
            + "بلافاصله ارزش  سهام آن نامزد بی\u200Cارزش می\u200Cشود و اگر سهام آن نامزد را خریده باشید، یک جورهایی آن بخش از سرمایه\u200C\u200Eگذاری شما سوخته است!"
            +"\n"
            +"\n"
            +"- اگر انتخابات  به دور دوم کشیده شود؟"
            +"\n"
            +"در آن صورت به جز دو نامزدی که به دور دوم رسیده\u200Cاند، ارزش سهام بقیه\u200Cی نامزدها صفر می\u200Cشود و بازی تا  پایان دور دوم انتخابات ادامه پیدا می\u200Cکند."
            +"\n"
            +"\n"
            +"- سهام را از کی بخریم؟"
            +"\n"
            +"شما می\u200Cتوانید تا حدی که موجودی شما اجازه دهد از ربات ما سهام مورد نظر خود را بخرید. قیمت لحظه\u200Cای سهام\u200Cها را ربات تعیین می\u200Cکند. با زدن دکمه\u200Cی «قیمت لحظه\u200Cای سهام نامزدها» می\u200Cتوانید از لیست قیمت\u200Cها در آن لحظه خبردار شوید."
            +"\n"
            +"با زدن دکمه\u200Cی «خرید» می\u200Cتوانید سهام بخرید. پس از زدن این دکمه باید نامزدی که می\u200Cخواهید سهامش را بخرید مشخص کنید. در نهایت تعداد سهامی را که می\u200Cخواهید بخرید، مشخص کنید."
            +"\n"
            +"\n"
            +"- از کجا می\u200Cتوانم سهام\u200Cهایی که خریده\u200Cام را ببینم؟"
            +"\n"
            +"یک دکمه داریم برای این کار: «سبد سهام من» با زدن این دکمه سبد سهام\u200Cهایی که شما خریداری کرده\u200Cاید را ببینید."
            +"\n"
            +"\n"
            +"- می\u200Cشود سهام را فروخت؟"
            +"\n"
            +"چرا که نه؟ شما می\u200Cتوانید سهام\u200Cهایی که در سبدتان موجود است را بفروشید. به کی؟ به ربات. هر موقع که بخواهید می\u200Cتوانید سهام\u200Cهای خود را دوباره به ربات بفروشید. کافی است پس از زدن دکمه\u200Cی «فروش» سهامی که می\u200Cخواهید بفروشید را انتخاب کنید.  "
            +"\n"
            +"دقت کنید در هر بار تنها می\u200Cتوانید یک سهم را بفروشید. مثلا اگر سه سهام از آقای فلانی در سبدتان باشد، با زدن دکمه\u200Cی فروش و انتخاب «فروش سهام آقای فلانی» یکی از سهام\u200Cها فروخته می\u200Cشود و شما هنوز دو سهام از آقای فلانی در سبدتان دارید."
            +"\n"
            +"\n"
            +"- قیمت\u200Cها چطور تعیین می\u200Cشوند؟"
            +"\n"
            +"ارزش سهام\u200Cها را ربات ما بر اساس میزان خرید و فروش\u200Cها تعیین می\u200Cکند. به همین دلیل با انجام خرید و فروش\u200Cها ارزش سهام\u200Cها تغییر می\u200Cکند. هرچقدر یک سهام پرطرفدارتر باشد ارزشش بیشتر می\u200Cشود و بلعکس. پس شما به جز هوش پیش\u200Cبینی، باید شم اقتصادی هم داشته باشید تا بدانید کی کدام سهام را بخرید و کی کدام سهام را بفروشید."
            +"\n"
            +"اگر پس از دیدن لیست قیمت سهام\u200Cها و خرید یک سهام، قیمتی که خریده\u200Cاید با قیمتی که در لیست دیدید کمی متفاوت بود تعجب نکنید. علت، خرید و فروش\u200Cهایی است که دیگران در همین فاصله انجام داده\u200Cاند."
            +"\n"
            +"\n"
            +"- جایزه؟"
            +"\n"
            +"پس از مشخص شدن نتایج انتخابات  اسامی افراد برتر به همه اعلام خواهد شد. راستش هنوز اسپانسری برای اهدای جایزه نداریم، خدا را چه دیدید؟ شاید اسپانسری برای اهدای جایزه هم پیدا شد.";









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

    @Scheduled(fixedDelay = (15*60*1000))
    public void saveReport(){
        fullService.saveReport();
    }

    @Scheduled(fixedDelay = 700)
    public void response(){
        System.out.print(new Date().toString()+"\t :strat");
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
                    System.out.print(" :sent");
                }
            }
        }else{
            System.out.print(" :empty");
        }
        System.out.println(" :end");
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
        else if (text.equals(RULES_COMMAND)) {
            request.setText(GAME_RULES);
            ReplyKeyboard baseMenuKeyboard = getBaseMenuKeyboard();
            baseMenuKeyboard.getKeyboard().get(2).remove(0);
            baseMenuKeyboard.getKeyboard().get(2).add(new Keyboard(TEXT_RULES_COMMAND));
            request.setReply_markup(baseMenuKeyboard);
        }
        else if (text.startsWith(TEXT_RULES_COMMAND)) {
            request.setText(GAME_RULES_TEXT);
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
        ReplyKeyboard replyKeyboard= new ReplyKeyboard(3);
        replyKeyboard.getKeyboard().get(0).add(new Keyboard(BUY_COMMAND));
        replyKeyboard.getKeyboard().get(0).add(new Keyboard(SELL_COMMAND));
        replyKeyboard.getKeyboard().get(1).add(new Keyboard(LIST_COMMAND));
        replyKeyboard.getKeyboard().get(1).add(new Keyboard(ASSETS_COMMAND));
        replyKeyboard.getKeyboard().get(2).add(new Keyboard(RULES_COMMAND));
        return replyKeyboard;
    }
}
