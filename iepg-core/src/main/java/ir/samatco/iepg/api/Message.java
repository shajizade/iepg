package ir.samatco.iepg.api;

import ir.samatco.iepg.entity.Voter;

import java.util.List;
import java.util.Locale;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
public class Message {
    Long message_id;
    Voter from;
    String text;


    public Long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public Voter getFrom() {
        return from;
    }

    public void setFrom(Voter from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
