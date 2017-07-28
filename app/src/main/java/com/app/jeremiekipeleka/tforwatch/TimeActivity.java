package com.app.jeremiekipeleka.tforwatch;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnTouchListener;

public class TimeActivity extends AppCompatActivity  implements  OnTouchListener{

    TextView GlobalTime;
    TextView LapOne;
    TextView LapTwo;
    TextView LapThree;

    String LapOnevalue;
    String LapTwoValue;
    String Lapthreevalue;


    int TapCount= 0;
    float Ex1;
    float Ey1;
    float Cx2;
    float Cy2;

    float Diff_E;
    float Diff_C;
    //Number of seconds displayed on the stopwatch.
    private int seconds = 0;
    int hours = 0;
    int minutes = 0;
    //Is the stopwatch running?
    private boolean Active = false;
    private boolean running;
    private boolean timer;
    private boolean timesUP = false;
    private boolean wasRunning;

    private boolean SwipedDown= false ;
    private boolean SwipedUp=false ;
    private boolean SwipedLeft= false;
    private boolean SwipedRight=false;
    private boolean SingleTap=false;
    private boolean DoubleTap=false;
    private boolean Pinch = false;

    private ScaleGestureDetector mScaleDetector;
    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/DIGITALDREAM.ttf");
            View Mainlayout = findViewById(R.id.myLayout);
            myView = Mainlayout;
        GlobalTime = (TextView)findViewById(R.id.time_view);
        LapOne = (TextView)findViewById(R.id.txtLapOne);
        LapTwo = (TextView)findViewById(R.id.txtLapTwo);
        LapThree = (TextView)findViewById(R.id.txtLapThree);

        mScaleDetector = new ScaleGestureDetector(this, new MyPinchListener());
        GlobalTime.setTypeface(typeface);

                //Action Movement
        myView.setOnTouchListener(new OnActionTouchListener(this) {



            @Override
            public void onSingleTapConfirmed(MotionEvent e) {
                Toast.makeText(TimeActivity.this, "Single Tap", Toast.LENGTH_SHORT).show();

                if (running)
                {
                    TapCount += 1;
                }
            if (GlobalTime.getText().toString().equals("0:00:00")){
                running = true;
                Active = true;
                timer = false;
                timesUP= false;

                SingleTap= true;

                DoubleTap= false;
                SwipedLeft= false;
                SwipedUp= false;
                SwipedDown= false;
                SwipedRight=false;
            }

                if (SwipedDown && Active !=true && seconds>0 && timesUP==false)
                {
                    timer= true;
                    Active= true;

                }

                if (SwipedUp && Active !=true && seconds>0 && timesUP==false)
                {
                    timer= true;
                    Active= true;

                }


                if (SwipedLeft && Active !=true && seconds>0 && timesUP==false)
                {
                    timer= true;
                    Active= true;

                }


                if (SwipedRight && Active !=true && seconds>0 && timesUP==false)
                {
                    timer= true;
                    Active= true;

                }


            }

            @Override
            public void onDoubleTap(MotionEvent e) {
                Toast.makeText(TimeActivity.this, "Double Tap", Toast.LENGTH_SHORT).show();

                running = false;
                Active = false;
                timer = false;
                timesUP= false;

                DoubleTap= true;

                SingleTap= false;
                SwipedLeft= false;
                SwipedUp= false;
                SwipedDown= false;
                SwipedRight=false;
                Pinch= false;
            }

            @Override
            public void onSwipeDown() {
                Toast.makeText(TimeActivity.this, "Down", Toast.LENGTH_SHORT).show();
                //if(seconds>60){seconds-=60;}
               if (timesUP!=true && Active!=true){

                running = false;


                 SwipedDown= true;

                 SwipedUp= false;
                 SwipedLeft= false;
                 SwipedRight=false;
                 DoubleTap= false;
                 SingleTap= false;
                   Pinch= false;


                    Ex1 = getVelX1();
                    Ey1 = getVelY1();
                    Cx2=getVelX2();
                    Cy2=getVelY2();

                    Diff_E= Math.abs(Ex1 - Ey1);
                    Diff_C= Math.abs(Cx2 - Cy2);


                    // if the user has made a small swipe movement we increase the minutes by 2
                    if (Diff_C > 100 && Diff_C<= 399)
                    {
                        double x = seconds/60.0;
                        //seconds-=120;
                        double rem = x %1;
                        double q ;

                        if (x>2)
                        {
                            x = x - 2.0;
                            q = x * 60.00;
                            int i = (int)(x* 60.0);
                            double p  = i+ rem;
                            seconds = (int)q;
                            seconds = seconds +(int)rem;

                        }
                        if(seconds<0)
                        {
                            seconds = 0;
                        }
                    }
                    // if the user has made a medium swipe movement we increase the minutes by 3
                    else if (Diff_C>=400 && Diff_C <599)
                    {
                        double x = seconds/60.0;
                        //seconds-=120;
                        double rem = x %1;
                        double q ;

                        if (x>3)
                        {
                            x = x - 3.0;
                            q = x * 60.00;
                            int i = (int)(x* 60.0);
                            double p  = i+ rem;
                            seconds = (int)q;
                            seconds = seconds +(int)rem;
                        }
                        if(seconds<0)
                        {
                            seconds = 0;
                        }
                    }
                    // if the user has made a big swipe movement we decrease the minutes by 4
                    else if (Diff_C >=600)
                    {
                        double x = seconds/60.0;
                        //seconds-=120;
                        double rem = x %1;
                        double q ;

                        if (x>4)
                        {
                            x = x - 4.0;
                            q = x * 60.00;
                            int i = (int)(x* 60.0);
                            double p  = i+ rem;
                            seconds = (int)q;
                            seconds = seconds +(int)rem;
                        }
                        if(seconds<0)
                        {
                            seconds = 0;
                        }

                    }

                    //we increment by 1 minutes
                    else
                        {
                        seconds-=60;

                        if(seconds<0)
                        {
                             seconds = 0;
                        }

                        }



                }
            }



            @Override
            public void onSwipeUp() {
                Toast.makeText(TimeActivity.this, "Up", Toast.LENGTH_SHORT).show();
                //minutes+=120;
                if (timesUP!=true && Active!=true){
                running = false;
                //timer = true;
                timesUP= false;

                SwipedUp= true;

                SwipedDown= false;
                SwipedLeft= false;
                SwipedRight=false;
                DoubleTap= false;
                SingleTap= false;
                Pinch= false;
                //when swipe up we increase the minutes



             Ex1 = getVelX1();
             Ey1 = getVelY1();
             Cx2=getVelX2();
             Cy2=getVelY2();

             Diff_E= Math.abs(Ex1 - Ey1);
             Diff_C= Math.abs(Cx2 - Cy2);


          // if the user has made a small swipe movement we increase the minutes by 2
                if (Diff_C > 100 && Diff_C<= 399){ seconds+=120;}
         // if the user has made a medium swipe movement we increase the minutes by 3
                else if (Diff_C>=400 && Diff_C <599){seconds+=180;}
         // if the user has made a big swipe movement we increase the minutes by 4
                else if (Diff_C >=600){seconds+=240;}

                //we increment by 1 minutes
                else {seconds+=60;}

            }

            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(TimeActivity.this, "Left", Toast.LENGTH_SHORT).show();

                if (timesUP != true && Active != true) {
                    running = false;
                    //timer = true;
                    timesUP = false;

                    SwipedLeft = true;

                    SwipedUp = false;
                    SwipedDown = false;
                    SwipedRight = false;
                    DoubleTap = false;
                    SingleTap = false;
                    Pinch = false;



                    Ex1 = getVelX1();
                    Ey1 = getVelY1();
                    Cx2 = getVelX2();
                    Cy2 = getVelY2();

                    Diff_E = Math.abs(Ex1 - Ey1);
                    Diff_C = Math.abs(Cx2 - Cy2);


                    // if the user has made a small swipe movement we decrease the second by 4
                    if (Diff_C > 100 && Diff_C <= 399) {

                        seconds -= 4;

                        if(seconds<0)
                        {
                            seconds = 0;
                        }
                    }
                    // if the user has made a medium swipe movement we decrease the second by 6
                    else if (Diff_C >= 400 && Diff_C < 599)
                    {
                        seconds -= 6;

                        if(seconds<0)
                        {
                            seconds = 0;
                        }
                    }
                    // if the user has made a big swipe movement we decrease the second by  10
                    else if (Diff_C >= 600)
                    {
                        seconds -= 10;

                        if(seconds<0)
                        {
                            seconds = 0;
                        }
                    }

                    //we increment by 2 seconds
                    else {
                        seconds -= 2;

                        if(seconds<0)
                        {
                            seconds = 0;
                        }
                    }


                }
            }
            @Override
            public void onSwipeRight() {
                Toast.makeText(TimeActivity.this, "Right", Toast.LENGTH_SHORT).show();
                if (timesUP != true && Active != true) {


                running = false;
               // timer = true;
                timesUP= false;

                SwipedRight=true;

                SwipedLeft= false;
                SwipedUp= false;
                SwipedDown= false;
                DoubleTap= false;
                SingleTap= false;
                Pinch= false;
                //when swipe up we increase the minutes



                Ex1 = getVelX1();
                Ey1 = getVelY1();
                Cx2=getVelX2();
                Cy2=getVelY2();

                Diff_E= Math.abs(Ex1 - Ey1);
                Diff_C= Math.abs(Cx2 - Cy2);


                // if the user has made a small swipe movement we increase the second by 4
                if (Diff_C > 100 && Diff_C<= 399){ seconds+=4;}
                // if the user has made a medium swipe movement we increase the seconds by 6
                else if (Diff_C>=400 && Diff_C <599){seconds+=6;}
                // if the user has made a big swipe movement we increase the minutes by 10
                else if (Diff_C >=600){seconds+=10;}

                //we increment by 2 seconds
                else {seconds+=2;}


            }

            }





        });

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        //running = true;
        runTimer();
    }




    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
    }

    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;
    }

    //Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    //Sets the number of seconds on the timer.
    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        //handler allows us to schedule when should our piece of code get executed
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                hours = seconds/3600;
                minutes =(seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                if (seconds==0 | seconds <0){timeView.setTextColor(getResources().getColor(R.color.red));}
                else{timeView.setTextColor(getResources().getColor(R.color.white));}

                timeView.setText(time);

                if (running && Active) {
                    seconds++;

                }

                else if (seconds <0){
                    timeView.setText("0:00:00");


                    timer= false;
                    timesUP = true;
                }

                if (running && Active && TapCount==1) {

                    LapOnevalue = "LAP 1: " + timeView.getText().toString();
                    LapOne.setText(LapOnevalue);
                    TapCount+=1;
                    LapOne.setTextColor(getResources().getColor(R.color.white));

                }

                if (running && Active && TapCount==3) {

                    LapTwoValue = "LAP 2: " + timeView.getText().toString();
                    LapTwo.setText(LapTwoValue);
                    TapCount+=1;
                    LapTwo.setTextColor(getResources().getColor(R.color.white));

                }

                if (running && Active && TapCount==5) {

                    Lapthreevalue = "LAP 3: " + timeView.getText().toString();
                    LapThree.setText(Lapthreevalue);
                    TapCount+=1;
                    LapThree.setTextColor(getResources().getColor(R.color.white));

                }

                if (SwipedDown && Active)
                {

                if (timer == true && timesUP == false) {
                        //running = false;
                        seconds--;


                    }
                }


                if (SwipedUp && Active)
                {

                    if (timer == true && timesUP == false) {
                        //running = false;

                        seconds--;


                    }
                }

                if (SwipedLeft && Active)
                {

                    if (timer == true && timesUP == false) {
                        //running = false;

                        seconds--;


                    }
                }


                if (SwipedRight && Active)
                {

                    if (timer == true && timesUP == false) {
                        //running = false;

                        seconds--;


                    }
                }

                handler.postDelayed(this, 1000);
            }
        });
    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent ev)
    {
        mScaleDetector.onTouchEvent(ev);
        return  true;
    }

    public class MyPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {



            Toast.makeText(TimeActivity.this, "Pinch: Time Reset", Toast.LENGTH_SHORT).show();

            Pinch= true;
            seconds= 0;

            running = false;
            timer = false;
            timesUP= true ;

            DoubleTap= false;

            SingleTap= false;
            SwipedLeft= false;
            SwipedUp= false;
            SwipedDown= false;
            SwipedRight=false;

            return true;

        }
    }
}
