package sample;

/**
 * Created by Роман on 04.03.2017.
 */
public class TrainCouple{
    Train from;
    Train to;
    String numtonum;
    String transfer;

    public String getTransfer() {
        return transfer;
    }

    public Train getFrom() {
        return from;
    }

    public Train getTo() {
        return to;
    }

    String allTime;

    public String getNumtonum() {
        return numtonum;
    }

    public void setNumtonum(String numtonum) {
        this.numtonum = numtonum;
    }

    public String getAllTime() {
        return allTime;
    }

    public void setAllTime(String allTime) {
        this.allTime = allTime;
    }

    TrainCouple(Train from, Train to,String transfer,String numtonum, String allTime){
        this.from = from;
        this.to = to;
        this.transfer = transfer;
        this.numtonum = numtonum;
        this.allTime = allTime;
    }

}
