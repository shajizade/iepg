package ir.samatco.iepg.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
public class ReplyKeyboard implements ReplyMarkup{
    List<List<Keyboard>> keyboard;
    public List<List<Keyboard>> getKeyboard() {
        return keyboard;
    }

    public ReplyKeyboard() {
        setKeyboard(new ArrayList<List<Keyboard>>());
    }
    public ReplyKeyboard(int rowNumber) {
        ArrayList<List<Keyboard>> keyboard = new ArrayList<>();
        for(int i=0;i<rowNumber;i++)
            keyboard.add(new ArrayList<Keyboard>());
        setKeyboard(keyboard);
    }

    public void setKeyboard(List<List<Keyboard>> keyboard) {
        this.keyboard = keyboard;
    }
}