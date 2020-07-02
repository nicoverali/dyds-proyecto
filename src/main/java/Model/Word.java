package Model;


import java.util.Date;

public class Word {
    private String term;
    private String meaning;
    private int source;
    private Date date; // Ver el date que uso porque es cuestionable

    public void setTerm(String term){
        this.term = term;
    }
    public void setMeaning(String meaning){
        this.meaning = meaning;
    }
    public void  setSource(int source){
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
