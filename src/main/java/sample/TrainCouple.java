package sample;

/**
 * Created by Роман on 04.03.2017.
 */
public class TrainCouple{
    Train from;
    Train to;
    String numtonum;
    String transfer;
    String transferTime;
    String allTime;
    public String getTransfer() {
        return transfer;
    }
    public String getTransferTime(){
        return transferTime;
    }
    public Train getFrom() {
        return from;
    }
    public Train getTo() {
        return to;
    }
    public String getNumtonum() {
        return numtonum;
    }
    public String getAllTime() {
        return allTime;
    }
    public TrainCouple(Train from, Train to,String transfer,String numtonum, String allTime, String transferTime){
        this.from = from;
        this.to = to;
        this.transfer = transfer;
        this.numtonum = numtonum;
        this.allTime = allTime;
        this.transferTime = transferTime;
    }

}
