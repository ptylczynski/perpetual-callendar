package cloud.ptl.perpetualcallendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

public class ShoppingSundaysActivity extends AppCompatActivity {

    private DatePicker datePicker_start;
    private DatePicker datePicker_end;
    private TextView textView_header;
    private TextView textView_table;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_sundays);

        this.datePicker_start = new DatePicker(this);
        this.datePicker_end = new DatePicker(this);

        this.textView_header = findViewById(R.id.textView_head);
        this.textView_table = findViewById(R.id.textView_table2);

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
        this.setTable();
    }

    private void setHeader(){
        Integer startYear = this.datePicker_start.getYear();
        Integer endYear = this.datePicker_end.getYear();
        if (startYear.equals(endYear))
            this.textView_header.setText(
                    String.format(
                            "Niedziele Handlowe w roku %s to:",
                            startYear
                    )
            );
        else
            this.textView_header.setText(
                    String.format(
                            "Niedziele Handlowe w latach %s - %s to:",
                            startYear,
                            endYear
                    )
            );
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void setTable(){
        Integer startYear = this.datePicker_start.getYear();
        Integer endYear = this.datePicker_end.getYear();
        StringBuilder sb = new StringBuilder();
        for(Integer year = startYear; year < endYear; year++){
            sb.append(year).append("\n");
            this.datePicker_start.updateDate(
                    year,
                    this.datePicker_start.getMonth(),
                    this.datePicker_start.getDayOfMonth()
            );
            List<LocalDate> sundays = Helpers.shoppingSundays(datePicker_start);
            for(LocalDate ld : sundays){
                sb.append(ld.toString()).append("\n");
            }
            sb.append("\n\n");
        }
        this.textView_table.setText(sb.toString());
    }
}