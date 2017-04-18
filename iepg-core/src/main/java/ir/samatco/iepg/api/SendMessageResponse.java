package ir.samatco.iepg.api;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
public class SendMessageResponse {
    Boolean ok;
    Message result;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Message getResult() {
        return result;
    }

    public void setResult(Message result) {
        this.result = result;
    }
}
