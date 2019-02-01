package com.meraki.meraki;

import java.sql.Timestamp;

public class Utility {

    public String timeDifference( Timestamp postedWhen , Timestamp currentTime ){
        long diff = currentTime.getTime() - postedWhen.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String time = "";
        if ( diffDays> 0 ){
            time = "Posted " + diffDays + " d ago";
        } else if ( diffHours > 0 ){
            time = "Posted " + diffHours + " hr ago";
        } else {
            time = "Posted " + diffMinutes + " min ago";
        }
        return time;
    }

}
