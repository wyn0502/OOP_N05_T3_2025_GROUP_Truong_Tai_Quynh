public class Time {
    private int hour;
    private int minute;
    private int second;
    private int day;
    private int month;
    private int year;

    // Constructors
    public Time() {
        setTime(0, 0, 0);
        setDate(1, 1, 2000);
    }

    public Time(int h) {
        setTime(h, 0, 0);
        setDate(1, 1, 2000);
    }

    public Time(int h, int m) {
        setTime(h, m, 0);
        setDate(1, 1, 2000);
    }

    public Time(int h, int m, int s) {
        setTime(h, m, s);
        setDate(1, 1, 2000);
    }

    public Time(int h, int m, int s, int d, int mo, int y) {
        setTime(h, m, s);
        setDate(d, mo, y);
    }

    
    public Time setTime(int h, int m, int s) {
        setHour(h);
        setMinute(m);
        setSecond(s);
        return this;
    }

    public Time setHour(int h) {
        this.hour = (h >= 0 && h < 24) ? h : 0;
        return this;
    }

    public Time setMinute(int m) {
        this.minute = (m >= 0 && m < 60) ? m : 0;
        return this;
    }

    public Time setSecond(int s) {
        this.second = (s >= 0 && s < 60) ? s : 0;
        return this;
    }

    public Time setDate(int d, int m, int y) {
        this.day = (d >= 1 && d <= 31) ? d : 1;
        this.month = (m >= 1 && m <= 12) ? m : 1;
        this.year = (y >= 0) ? y : 2000;
        return this;
    }

    public Time setDay(int d) {
        this.day = (d >= 1 && d <= 31) ? d : 1;
        return this;
    }

    public Time setMonth(int m) {
        this.month = (m >= 1 && m <= 12) ? m : 1;
        return this;
    }

    public Time setYear(int y) {
        this.year = (y >= 0) ? y : 2000;
        return this;
    }

    
    public int getHour() { 
        return hour; 
    }
    public int getMinute() { 
        return minute; 
    }
    public int getSecond() { 
        return second; 
    }
    public int getDay() { 
        return day; 
    }
    public int getMonth() { 
        return month; 
    }
    public int getYear() { 
        return year; 
    }

    
    
    public String toString() {
        int displayHour = (hour == 0 || hour == 12) ? 12 : hour % 12;
        String ampm = (hour < 12) ? "AM" : "PM";
        String timeStr = String.format("%02d:%02d:%02d %s", displayHour, minute, second, ampm);
        String dateStr = String.format("%02d/%02d/%04d", day, month, year);
        return dateStr + " " + timeStr;
    }
}
