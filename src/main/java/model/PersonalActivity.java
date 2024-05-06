package model;

import java.time.LocalDate;

//Made by Christian
public class PersonalActivity {

    LocalDate start;
    LocalDate end;
    String reason;

    public PersonalActivity(LocalDate start, LocalDate end, String reason){
        this.start = start;
        this.end = end;
        this.reason = reason;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getReason(){
        return reason;
    }

}
