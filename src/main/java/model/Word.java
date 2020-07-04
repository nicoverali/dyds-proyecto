package model;


import java.util.Date;

public class Word {
    private final String term;
    private final String meaning;
    private int source;
    private Date date; // Ver el date que uso porque es cuestionable

    public Word(String term, String meaning){
        this.term = term;
        this.meaning = meaning;
    }

    public void setSource(int source){
        this.source = source;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public String getTerm(){
        return term;
    }
    public String getMeaning(){
        return meaning;
    }
    public int getSource(){
        return source;
    }
    public Date getDate(){
        return date;
    }
}
