package cloud.ptl.perpetualcallendar;

import android.os.Build;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Helpers {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setCurrentDate(DatePicker control){
        LocalDateTime now = LocalDateTime.now();
        control.updateDate(
                now.getYear(),
                now.getMonth().getValue() - 1,
                now.getDayOfMonth()
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate easterDate(DatePicker datePicker){
        final int year = datePicker.getYear();
        final int a = year % 19;
        final int b = (int) Math.ceil(year / 100.0);
        final int c = year % 100;
        final int d = (int) Math.ceil(b / 4.0);
        final int e = b % 4;
        final int f = (int) ((b + 8) / 25.0);
        final int g = (int) ((b - f + 1) / 3.0);
        final int h = (19 * a + b - d - g + 15) % 30;
        final int i = (int)  (c / 4.0);
        final int k = c % 4;
        final int l = (32 + 2 * e + 2 * i - h - k) % 7;
        final int m = (int)  (Math.ceil(a + 11.0 * h + 22.0 * l) / 451.0);
        final int p = (h + l - 7 * m + 114) % 31;

        final int day = p + 1;
        final int month = (int) ((h + l - 7.0 * m + 114.0) / 31.0);

        return LocalDate.of(
                year,
                month,
                day
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate popielecDate(DatePicker datePicker){
        LocalDate easterDate = Helpers.easterDate(datePicker);
        return easterDate.minus(46, ChronoUnit.DAYS);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate corpusDeiDate(DatePicker datePicker){
        LocalDate easterDate = Helpers.easterDate(datePicker);
        return easterDate.plus(60, ChronoUnit.DAYS);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate adventStartDate(DatePicker datePicker){
        LocalDate xmas = LocalDate.of(
                datePicker.getYear(),
                12,
                25
        );

        return xmas.minus(
                4 * 7 + xmas.getDayOfWeek().getValue(),
                ChronoUnit.DAYS
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static List<LocalDate> shoppingSundays(DatePicker datePicker){
        int year = datePicker.getYear();
        LocalDate januaryLastDay = LocalDate.of(year, 1, 31);
        LocalDate aprilLastDay = LocalDate.of(year, 4, 30);
        LocalDate juneLastDay = LocalDate.of(year, 6, 30);
        LocalDate augustLastDay = LocalDate.of(year, 8, 31);
        LocalDate xmas = LocalDate.of(year, 12, 25);
        LocalDate easter = Helpers.easterDate(datePicker);
        return List.of(
                januaryLastDay
                        .minus(
                                (januaryLastDay.getDayOfWeek().getValue() + 1) % 7,
                                ChronoUnit.DAYS
                        ),
                aprilLastDay
                        .minus(
                                (aprilLastDay.getDayOfWeek().getValue() + 1) % 7,
                                ChronoUnit.DAYS
                        ),
                juneLastDay.minus(
                        (juneLastDay.getDayOfWeek().getValue() + 1) % 7,
                        ChronoUnit.DAYS
                ),
                augustLastDay.minus(
                        (augustLastDay.getDayOfWeek().getValue() + 1) % 7,
                        ChronoUnit.DAYS
                ),
                xmas.minus(
                        (xmas.getDayOfWeek().getValue()  + 1) % 7,
                        ChronoUnit.DAYS
                ),
                xmas.minus(
                        (xmas.getDayOfWeek().getValue() + 1) % 7 + 7,
                        ChronoUnit.DAYS
                ),
                easter.minus(
                        (easter.getDayOfWeek().getValue() + 1) % 7,
                        ChronoUnit.DAYS
                )
        );
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    public static Integer workingDays(DatePicker start, DatePicker end){
        List<LocalDate> stateHolidays = new ArrayList<>();
        Integer amount = 0;

        LocalDate startDate = LocalDate.of(
                start.getYear(),
                start.getMonth(),
                start.getDayOfMonth()
        );

        LocalDate endDate = LocalDate.of(
                end.getYear(),
                end.getMonth(),
                end.getDayOfMonth()
        );

        if (endDate.isBefore(startDate)) return 0;

        for(int year = start.getYear(); year < end.getYear(); year++){
            stateHolidays.addAll(
                    List.of(
                            LocalDate.of(
                                    year, 1, 1
                            ),
                            LocalDate.of(
                                    year, 1, 6
                            ),
                            LocalDate.of(
                                    year, 5, 1
                            ),
                            LocalDate.of(
                                    year, 5, 3
                            ),
                            LocalDate.of(
                                    year, 8, 15
                            ),
                            LocalDate.of(
                                    year,11, 1
                            ),
                            LocalDate.of(
                                    year, 12, 25
                            ),
                            LocalDate.of(
                                    year, 12, 26
                            )
                    )
            );
        }
        for (
                LocalDate date :
                Stream.iterate(
                        startDate, date -> date.plus(1, ChronoUnit.DAYS)
                ).limit(
                        ChronoUnit.DAYS.between(
                                startDate,
                                endDate
                        )
                ).collect(Collectors.toList())
        ){
            if (!stateHolidays.contains(date))
                if(date.getDayOfWeek().getValue() != 6)
                    if(date.getDayOfWeek().getValue() != 5)
                        amount++;
        }
        return amount;
    }
}
