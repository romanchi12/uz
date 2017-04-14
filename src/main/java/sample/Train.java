package sample;

/**
 * Created by Роман on 04.03.2017.
 */
public class Train {
    public String getNum() {
        return num;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public String getToStation() {
        return toStation;
    }

    public String getFromStation() {
        return fromStation;
    }

    String num;
    String travelTime;
    String fromStation;
    String toStation;

    public String getTimeDepartment() {
        return timeDepartment;
    }



    public String getTimeArrival() {
        return timeArrival;
    }



    String timeDepartment;
    String timeArrival;
    public Train(String num, String travelTime, String fromStation, String toStation,String timeDepartment, String timeArrival){
        System.out.println(timeDepartment+"***********"+timeArrival);

        this.num = num;
        this.travelTime = travelTime;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.timeDepartment = timeDepartment;
        this.timeArrival = timeArrival;
    }
}
