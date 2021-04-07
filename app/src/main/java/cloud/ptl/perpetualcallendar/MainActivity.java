package cloud.ptl.perpetualcallendar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker_start;
    private DatePicker datePicker_end;

    private TextView textView_workingDays;

    private Switch switch_movingHolidays;
    private Switch switch_shoppingSundays;

    private Button button_check;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.datePicker_start = findViewById(R.id.date_picker);
        this.datePicker_end = findViewById(R.id.date_picker2);

        this.textView_workingDays = findViewById(R.id.textView_working_days_output);

        this.switch_movingHolidays = findViewById(R.id.switch_moving_holidays);
        this.switch_shoppingSundays = findViewById(R.id.switch_shopping_sat);

        this.button_check = findViewById(R.id.button_calculate);

        Helpers.setCurrentDate(this.datePicker_start);
        Helpers.setCurrentDate(this.datePicker_end);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.datePicker_end.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.textView_workingDays.setText(
                        Helpers.workingDays(
                                this.datePicker_start,
                                this.datePicker_end
                        ).toString()
                );
            }
        });
        this.datePicker_start.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.textView_workingDays.setText(
                        Helpers.workingDays(
                                this.datePicker_start,
                                this.datePicker_end
                        ).toString()
                );
            }
        });

        this.switch_shoppingSundays.setOnClickListener(v -> {
            this.switch_movingHolidays.setChecked(false);
        });

        this.switch_movingHolidays.setOnClickListener(v -> {
            this.switch_shoppingSundays.setChecked(false);
        });

        this.button_check.setOnClickListener(v -> {

        });
    }
}