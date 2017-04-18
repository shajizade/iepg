package ir.samatco.iepg.api;

import java.util.List;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
public class Keyboard {
    String text;

    public Keyboard() {
    }

    public Keyboard(String text) {
        this.text=text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
