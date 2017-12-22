package com.spaceapp.oli.spaceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.spaceapp.oli.spaceapp.utils.LunarDataCalculation;
import com.spaceapp.oli.spaceapp.utils.SimpleGestureFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.text.DateFormat.getDateInstance;

public class LunarDataActivity extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener {

    public int moonPhaseState = 0;
    ImageView moonView;
    TextView textView, phaseView, ageView, distanceView, justPhaseView;
    TextView constellationView;
    Calendar cal;
    String timeStamp;
    SimpleDateFormat sdf;
    int year;
    int month_x;
    int day_x;
    LunarDataCalculation ld;
    private SimpleGestureFilter detector;
    private int moonPhase[] = {
            R.drawable.moon0, R.drawable.moon1, R.drawable.moon2, R.drawable.moon3, R.drawable.moon4,
            R.drawable.moon5, R.drawable.moon6, R.drawable.moon7, R.drawable.moon8, R.drawable.moon9,
            R.drawable.moon10, R.drawable.moon11, R.drawable.moon12, R.drawable.moon13, R.drawable.moon14,
            R.drawable.moon15, R.drawable.moon16, R.drawable.moon17, R.drawable.moon18, R.drawable.moon19,
            R.drawable.moon20, R.drawable.moon21, R.drawable.moon22, R.drawable.moon23, R.drawable.moon24,
            R.drawable.moon25, R.drawable.moon26, R.drawable.moon27, R.drawable.moon28, R.drawable.moon29
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunar_data);

        moonView = (ImageView) findViewById(R.id.moonView);
        textView = (TextView) findViewById(R.id.textView);
        phaseView = (TextView) findViewById(R.id.phaseView);
        ageView = (TextView) findViewById(R.id.ageView);
        distanceView = (TextView) findViewById(R.id.distanceView);
        justPhaseView = (TextView) findViewById(R.id.justPhaseView);
        constellationView = (TextView) findViewById(R.id.constellationView);

        detector = new SimpleGestureFilter(this, this);

        timeStamp = getDateInstance().format(Calendar.getInstance().getTime());
        sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        textView.setText(timeStamp);

        ld = new LunarDataCalculation();

        setData();

        moonPhaseState = (int) ld.getAge(year, month_x, day_x);
        moonView.setImageResource(moonPhase[moonPhaseState]);
    }


    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {

    }

    @Override
    public void onDoubleTap() {

    }

    @Override
    public void onScrollRight() {

        moonPhaseState--;
        if (moonPhaseState < 0) {
            moonPhaseState = 29;
        }

        moonView.setImageResource(moonPhase[moonPhaseState]);

        try {
            cal.setTime(sdf.parse(timeStamp));
        } catch (ParseException e) {

        }
        cal.add(Calendar.DATE, -1);  // number of days to add
        timeStamp = sdf.format(cal.getTime());

        year = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        textView.setText(timeStamp);
        setData();

    }

    @Override
    public void onScrollLeft() {

        moonPhaseState++;
        if (moonPhaseState > 29)
            moonPhaseState = 0;

        moonView.setImageResource(moonPhase[moonPhaseState]);

        try {
            cal.setTime(sdf.parse(timeStamp));
        } catch (ParseException e) {
            Log.e("exception", e.toString());
        }
        cal.add(Calendar.DATE, 1);  // number of days to add
        timeStamp = sdf.format(cal.getTime());

        year = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        textView.setText(timeStamp);
        setData();

    }

    private void setData() {
        String phasestr = "Phase: " + ld.getPhase((int) ld.getAge(year, month_x, day_x));
        justPhaseView.setText(phasestr);
        String agestr = "Age: " + ld.getAge(year, month_x, day_x);
        ageView.setText(agestr);
        String distancestr = "Distance: " + ld.getDistance(year, month_x, day_x);
        distanceView.setText(distancestr);
        String constellationstr = "Constellation: " + ld.moonConstellation(ld.moonLongitude(year, month_x, day_x));
        constellationView.setText(constellationstr);
    }

}
