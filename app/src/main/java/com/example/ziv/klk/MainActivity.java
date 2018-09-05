package com.example.ziv.klk;

import android.content.DialogInterface;
import android.graphics.ColorSpace;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String toTimeString(long miliseconds) {
        long hours = miliseconds / 3600000;
        miliseconds = miliseconds % 3600000;

        long minutes = miliseconds / 60000;
        miliseconds = miliseconds % 60000;

        long seconds = miliseconds / 1000;
        miliseconds = miliseconds % 1000;

        String hoursString;
        if (hours < 10) {
            hoursString = ("0" + hours);
        } else {
            hoursString = "" + hours;
        }

        String minutesString;
        if (minutes < 10) {
            minutesString = ("0" + minutes);
        } else {
            minutesString = "" + minutes;
        }

        String secondsString;
        if (seconds < 10) {
            secondsString = ("0" + seconds);
        } else {
            secondsString = "" + seconds;
        }

        String milisecondsString;
        if (miliseconds < 10) {
            milisecondsString = ("00" + miliseconds);
        } else if (miliseconds < 100) {
            milisecondsString = ("0" + miliseconds);
        } else {
            milisecondsString = "" + miliseconds;
        }

        return (hoursString + ":" + minutesString + ":" + secondsString + "." + milisecondsString);
    }
    public static String inputToString(LinkedList q){
        String s = "";
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 2; j++){
                s+=q.peek().toString();
                q.add(q.remove());
            }
            s+=":";
        }
        s = s.substring(0, s.length() - 1);

        return s;
    }
    public static String timerToString(int hours, int minutes, int seconds){

        String hoursString;
        if (hours < 10) {
            hoursString = ("0" + hours);
        } else {
            hoursString = "" + hours;
        }

        String minutesString;
        if (minutes < 10) {
            minutesString = ("0" + minutes);
        } else {
            minutesString = "" + minutes;
        }

        String secondsString;
        if (seconds < 10) {
            secondsString = ("0" + seconds);
        } else {
            secondsString = "" + seconds;
        }

        return (hoursString + ":" + minutesString + ":" + secondsString);
    }
    public static void alarm(){

    }



    Button StopwatchStart, StopwatchStop;
    Button TimerStart, TimerStop;

    TextView StopwatchTime, TimerTime;

    Button tmr1, tmr2, tmr3, tmr4, tmr5, tmr6, tmr7, tmr8, tmr9, tmr0, tmrDelete, tmrReset;

    TabHost tabHost;

    Timer tmr = new Timer();
    Stopwatch sw = new Stopwatch();

    ProgressBar TimerBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.stopwatch);
        spec.setIndicator("Stopwatch");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.timer);
        spec.setIndicator("Timer");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.alarm);
        spec.setIndicator("Alarm");
        host.addTab(spec);

        //Setting the Stopwatch parts
        StopwatchStart = (Button) findViewById(R.id.SWstart);
        StopwatchStop = (Button) findViewById(R.id.SWstop);
        StopwatchTime = (TextView) findViewById(R.id.SWtime);

        //Setting thE TIMER PARTS
        TimerStart = (Button) findViewById(R.id.TMRstart);
        TimerStop = (Button) findViewById(R.id.TMRstop);
        TimerTime = (TextView) findViewById(R.id.TMRtime);
        TimerBar = (ProgressBar) findViewById(R.id.timerProgressBar);
        TimerBar.setVisibility(View.INVISIBLE);


        //Setting SW buttons text
        StopwatchStart.setText("Start");
        StopwatchStop.setText("Stop");

        //Setting TMR buttons text
        TimerStart.setText("Start");
        TimerStop.setText("Stop");

        //Setting SW and timer counters text
        StopwatchTime.setText(toTimeString(0));
        TimerTime.setText(inputToString(tmr.getInput()));

        //SW buttons setting listener
        StopwatchStart.setOnClickListener(this);
        StopwatchStop.setOnClickListener(this);

        //TMR buttons setting listener
        TimerStart.setOnClickListener(this);
        TimerStop.setOnClickListener(this);

        //Setting Timer KEYBOARD buttons
        tmr1 = (Button) findViewById(R.id.TMR1);
        tmr2 = (Button) findViewById(R.id.TMR2);
        tmr3 = (Button) findViewById(R.id.TMR3);
        tmr4 = (Button) findViewById(R.id.TMR4);
        tmr5 = (Button) findViewById(R.id.TMR5);
        tmr6 = (Button) findViewById(R.id.TMR6);
        tmr7 = (Button) findViewById(R.id.TMR7);
        tmr8 = (Button) findViewById(R.id.TMR8);
        tmr9 = (Button) findViewById(R.id.TMR9);
        tmr0 = (Button) findViewById(R.id.TMR0);
        tmrDelete = (Button) findViewById(R.id.TMRdel);
        tmrReset = (Button) findViewById(R.id.TMRreset);

        //Setting Timer KEYBOARD buttons listener
        tmr1.setOnClickListener(this);
        tmr2.setOnClickListener(this);
        tmr3.setOnClickListener(this);
        tmr4.setOnClickListener(this);
        tmr5.setOnClickListener(this);
        tmr6.setOnClickListener(this);
        tmr7.setOnClickListener(this);
        tmr8.setOnClickListener(this);
        tmr9.setOnClickListener(this);
        tmr0.setOnClickListener(this);
        tmrDelete.setOnClickListener(this);
        tmrReset.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(StopwatchStart)) {
            if (sw.state == State.STOPPED) {
                sw.execute();
                StopwatchStart.setText("Pause");
            } else if (sw.state == State.RUNNING) {
                sw.setState(State.PAUSED);
                StopwatchStart.setText("Resume");
            } else if (sw.state == State.PAUSED) {
                sw.setState(State.RUNNING);
                StopwatchStart.setText("Pause");
            }

        }
        if (v.equals(StopwatchStop)){
            sw.setState(State.STOPPED);
        }
        if (v.equals(tmr1)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(1);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr2)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(2);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr3)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(3);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr4)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(4);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr5)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(5);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr6)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(6);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr7)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(7);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr8)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(8);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr9)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(9);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmr0)&&tmr.getState()==State.STOPPED){
            tmr.input.poll();
            tmr.input.add(0);
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmrDelete)&&tmr.getState()==State.STOPPED){
            tmr.input.add(0);
            for (int i = 0 ; i < 5 ; i++){
                tmr.input.add(tmr.input.remove());
            }
            tmr.input.remove();
            TimerTime.setText(inputToString(tmr.getInput()));
        }
        if (v.equals(tmrReset)&&tmr.getState()==State.STOPPED){
            tmr.input = new LinkedList<Integer>();
            for(int i = 0 ; i < 6 ; i++){
                tmr.input.add(0);
            }
            TimerTime.setText(inputToString(tmr.getInput()));
        }

        if (v.equals(TimerStart)) {
            if (tmr.state == State.STOPPED) {
                tmr.execute();
                TimerStart.setText("Pause");
            } else if (tmr.state == State.RUNNING) {
                tmr.setState(State.PAUSED);
                TimerStart.setText("Resume");
            } else if (tmr.state == State.PAUSED) {
                tmr.setState(State.RUNNING);
                TimerStart.setText("Pause");
            }

        }
        if (v.equals(TimerStop)){
            tmr.setState(State.STOPPED);
        }
    }


    class Stopwatch extends AsyncTask<Long, Long, String> {
        State state;
        long time;

        public Stopwatch() {
                this.state = State.STOPPED;
                this.time = 0;
        }

        public State getState() {
            return state;
        }

        public long getTime() {
            return time;
        }

        public void setState(State state) {
            this.state = state;
        }

        public void setTime(long time) {
            this.time = time;
        }

        @Override
        protected void onPreExecute() {
            setState(State.RUNNING);
        }

        @Override
        protected String doInBackground(Long... integers) {
            while (this.state != State.STOPPED){
                if(this.state == State.RUNNING){
                    try {
                        Thread.sleep(29);
                        time+=29;
                        publishProgress(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            StopwatchTime.setText(toTimeString((values[0])));
        }

        @Override
        protected void onPostExecute(String s) {
            setTime(0);
            StopwatchStart.setText("Start");
            StopwatchTime.setText(toTimeString(0));
            sw = new Stopwatch();
        }

    }

    class Timer extends AsyncTask<Integer, Integer, String>{

        LinkedList<Integer> input;
        int hours;
        int minutes;
        int seconds;
        State state;

        public Timer() {
            this.input = new LinkedList<Integer>();
                for(int i = 0 ; i < 6 ; i++){
                    input.add(0);
                }

                this.state = State.STOPPED;
        }

        public LinkedList<Integer> getInput() {
            return input;
        }

        public int getHours() {
            return hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public int getSeconds() {
            return seconds;
        }

        public State getState() {
            return state;
        }

        public void setInput(LinkedList<Integer> input) {
            this.input = input;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public void setState(State state) {
            this.state = state;
        }

        protected void setTimeFromInput(){
            String str = "";
            for (int i = 0; i < 2 ; i++) {
                str += this.input.peek();
                this.input.add(this.input.remove());
            }
            this.hours = Integer.parseInt(str);

            str = "";
            for (int i = 0; i < 2 ; i++) {
                str += this.input.peek();
                this.input.add(this.input.remove());
            }
            this.minutes = Integer.parseInt(str);

            str = "";
            for (int i = 0; i < 2 ; i++) {
                str += this.input.peek();
                this.input.add(this.input.remove());
            }
            this.seconds = Integer.parseInt(str);

            if(this.seconds>59){
                this.minutes+=(int)this.seconds/60;
                this.seconds=this.seconds%60;
            }
            if(this.minutes>59){
                this.hours+=(int)this.minutes/60;
                this.minutes=this.minutes%60;
            }
        }

        protected int getTimeInSeconds(){
            return this.hours*3600+this.minutes*60+this.seconds;
        }

        /*public void launchAlarm(){

            this.r.play();

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Time's up!");
            alertDialog.setMessage("Press OK to dismiss");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            tmr.r.stop();

                        }
                    });
            alertDialog.show();
        }*/

        protected void onPreExecute() {
            this.setTimeFromInput();
            TimerTime.setText(timerToString(this.hours,this.minutes,this.seconds));
            this.setState(State.RUNNING);
            TimerBar.setVisibility(View.VISIBLE);
            TimerBar.setMax(getTimeInSeconds());
            TimerBar.setProgress(getTimeInSeconds());
        }

        protected String doInBackground(Integer... integers) {
            while (this.state != State.STOPPED){
                if(this.state == State.RUNNING){
                    if(this.hours == 0 && this.minutes ==0 && this.seconds ==0){
                        this.setState(State.STOPPED);
                        return null;
                    }
                    try {
                        Thread.sleep(1000);
                        if(this.state == State.RUNNING) {

                            if(this.seconds == 0){
                                if (this.minutes == 0){
                                    if (this.hours == 0){
                                        this.setState(State.STOPPED);
                                        return null;
                                    } else{
                                        this.hours--;
                                        this.minutes = 59;
                                        this.seconds = 59;
                                    }
                                } else {
                                    this.minutes--;
                                    this.seconds = 59;
                                }
                            } else {
                                this.seconds--;
                            }
                        Integer[] arr = {hours, minutes, seconds};
                        publishProgress(arr);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            TimerTime.setText(timerToString(values[0], values[1], values[2]));
            TimerBar.setProgress(getTimeInSeconds());
        }

        protected void onPostExecute(String s){
            tmr = new Timer();
            TimerStart.setText("Start");
            TimerTime.setText(inputToString(tmr.input));
            TimerBar.setVisibility(View.INVISIBLE);
            String streing = "1";



        }



    }

    protected enum State{
        STOPPED, RUNNING, PAUSED;
    }

}

