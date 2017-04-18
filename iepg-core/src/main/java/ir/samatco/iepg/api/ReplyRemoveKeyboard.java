package ir.samatco.iepg.api;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
public class ReplyRemoveKeyboard implements ReplyMarkup{
    Boolean remove_keyboard=true;

    public Boolean getRemove_keyboard() {
        return remove_keyboard;
    }

    public void setRemove_keyboard(Boolean remove_keyboard) {
        this.remove_keyboard = remove_keyboard;
    }
}
