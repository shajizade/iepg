package ir.samatco.iepg.api;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
public class SendMessageRequest {
    Long chat_id;
    String text;
    ReplyMarkup reply_markup;

    public ReplyMarkup getReply_markup() {
        return reply_markup;
    }

    public void setReply_markup(ReplyMarkup reply_markup) {
        this.reply_markup = reply_markup;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }
}
