package sample;

/**
 * Created by Роман on 04.03.2017.
 */
public class TrainCouple{
    String numtonum;
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

    TrainCouple(String numtonum, String allTime){
        this.numtonum = numtonum;
        this.allTime = allTime;
    }

}
