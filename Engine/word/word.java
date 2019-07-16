package Engine.word;

import java.util.Date;

public class word
{
    public word(String ENG,long date,int level,String note,int tot,int accurate)
    {
        this.ENG=ENG;
        this.date=date;
        this.level=level;
        this.note=note;
        discard=false;
        visible=false;
        finish=false;
        this.tot=tot;
        this.accurate=accurate;
    }
    /*public String toString()
    {
        StringBuffer x = new StringBuffer();
        x.append("Vocabulary  : "+ENG+"\r\n");
        x.append("Note : "+"\r\n"+note+"\r\n");
        x.append("Level : "+level+"\r\n");
        x.append("Date : "+ (new Date(date)).toString()+"\r\n");
        return x.toString();
    }*/
    public String toString()
    {
        StringBuffer x= new StringBuffer();
        x.append("*\r\n");
        x.append(date+"\r\n");
        x.append(level+"\r\n");
        x.append(tot+"\r\n");
        x.append(accurate+"\r\n");
        x.append(ENG+"\r\n");
        x.append(note);
        x.append("*\r\n");
        return x.toString();
    }
    public String ENG;
    public long date;
    public String note;
    public int level;
    public boolean discard;
    public boolean visible;
    public boolean finish;
    public int tot,accurate;
}