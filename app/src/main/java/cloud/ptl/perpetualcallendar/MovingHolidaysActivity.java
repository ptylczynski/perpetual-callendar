package cloud.ptl.perpetualcallendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;

public class MovingHolidaysActivity extends AppCompatActivity {

    private DatePicker datePicker_start;
    private DatePicker datePicker_end;

    private TextView textView_header;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_holidays);

        this.datePicker_start = new DatePicker(this);
        this.datePicker_end = new DatePicker(this);

        this.textView_header = findViewById(R.id.textView_header);

        Intent intent = getIntent();
        LocalDate date_start = LocalDate.parse(intent.getStringExtra("start_date"));
        LocalDate date_end = LocalDate.parse(intent.getStringExtra("end_date"));

        this.datePicker_start.updateDate(
                date_start.getYear(),
                date_start.getMonthValue(),
                date_start.getDayOfMonth()
        );

        this.datePicker_end.updateDate(
                date_end.getYear(),
                date_end.getMonthValue(),
                date_end.getDayOfMonth()
        );

        this.setHeader();
    }

    private void setHeader(){
        Integer startYear = this.datePicker_start.getYear();
        Integer endYear = this.datePicker_end.getYear();
        if (startYear.equals(endYear))
            this.textView_header.setText(
                    String.format(
                            "Ruchome święta w roku %s to:",
                            startYear
                    )
            );
        else
            this.textView_header.setText(
                    String.format(
                            "Ruchome święta w latach %s - %s to:",
                            startYear,
                            endYear
                    )
            );
    }


}