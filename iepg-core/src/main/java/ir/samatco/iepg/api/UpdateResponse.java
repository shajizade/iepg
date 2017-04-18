package ir.samatco.iepg.api;

import java.util.List;

/**
 * @author Saeed
 *         Date: 4/18/2017
 */
public class UpdateResponse {
    boolean ok;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<Update> getResult() {
        return result;
    }

    public void setResult(List<Update> result) {
        this.result = result;
    }

    List<Update> result;
}
