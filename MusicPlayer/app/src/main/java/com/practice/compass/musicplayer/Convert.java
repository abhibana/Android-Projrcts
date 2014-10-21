package com.practice.compass.musicplayer;

public class Convert {

    public static String convertToMinutes(int millis){

        int second = (millis/1000)%60;

        if(second<10) {
            return (millis / 60000) + ".0" + second;
        }
        else{
            return (millis / 60000) + "." + second;
        }
    }

}
