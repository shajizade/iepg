package ir.samatco.iepg.api;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
public class Update {
    public Long getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(Long update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    Long update_id;
    Message message;
}
