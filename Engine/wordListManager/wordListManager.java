package Engine.wordListManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;

import Engine.word.word;
import javafx.scene.control.Alert;

public class wordListManager
{
    static boolean mode;
    static public ArrayList<word>mylist = new ArrayList<word>();
    static public ArrayList<String>LIST = new ArrayList<String>();
    static public ArrayList<Long>Interval = new ArrayList<Long>();
    static public int len,current,pointer;
    static private String currentFilePath;
    static public String currentListName;

    static public void init()
    {
        String fileName = "./Data/List.list";
        File listFile = new File(fileName);
        if(!listFile.exists())
        {
            try
            {
                listFile.createNewFile();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            Scanner in =  new Scanner(new BufferedReader(new FileReader(fileName)));
            String temp;
            while(in.hasNext())
            {
                temp=in.nextLine();
                LIST.add(temp);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
        Interval.add((long)300000);
        Interval.add((long)1800000);
        Interval.add((long)43200000);
        Interval.add((long)86400000);
        Interval.add((long)172800000);
        Interval.add((long)345600000);
        Interval.add((long)691200000);
        Interval.add((long)1296000000);
    }

    static public boolean pickList(String listName,boolean isMemorize)
    {
        String fileName = "./Data/"+listName+"/"+listName;
        currentListName=listName;
        mylist.clear();

        if(isMemorize)
        {
            fileName+=".memorize";
        }
        else
        {
            fileName+=".review";
        }
        fileName+=".list";

        currentFilePath=fileName;
        
        long date;
        int level,tot,accurate;
        String ENG;
        StringBuffer note = new StringBuffer();
        try
        {
            Scanner in =  new Scanner(new BufferedReader(new FileReader(fileName)));   
            boolean LOCK=false;
            String temp;
            long now = (new Date()).getTime();
            while(in.hasNext())
            {
                if(LOCK==false)
                {
                    temp=in.nextLine();
                    temp = temp.trim();
                    if(temp.length()==0)continue;
                    if(temp.charAt(0)=='*')LOCK=true;
                    else continue;
                }
                else
                {
                    LOCK=false;
                    date=Long.parseLong(in.nextLine());
                    level=Integer.parseInt(in.nextLine());
                    tot=Integer.parseInt(in.nextLine());
                    accurate=Integer.parseInt(in.nextLine());
                    ENG=in.nextLine();
                    note.setLength(0);
                    String mark;
                    while(in.hasNext())
                    {
                        temp=in.nextLine();
                        mark=temp.trim();
                        if(mark.length()!=0&&mark.charAt(0)=='*')break;
                        else
                        {
                            note.append(temp+"\r\n");
                        }
                    }
                    mylist.add(new word(ENG,date,level,note.toString(),tot,accurate));
                }
            }
            current=0;
            len=0;
            pointer=0;
            for(word k : mylist)
            {
                if(isMemorize==false||k.date<=now)
                {
                    len++;
                    k.visible=true;
                }   
            }
            return true;
        }catch(Exception e)
        {
            Alert Info = new Alert(Alert.AlertType.INFORMATION);
            Info.setTitle("Invalid word file!!!");
            Info.setHeaderText("Fail to read the word list file!!!");
            Info.setContentText("Fatal Error : the word list file may have been broken.");
            Info.show();
            return false;
        }
    }

    static public boolean createList(String listName)
    {
        String fileName = "./Data/"+listName+"/"+listName;
        String reviewFileName = fileName+".review.list";
        String memorizeFileName = fileName+".memorize.list";

        File reviewFile = new File(reviewFileName);
        File memorizeFile = new File(memorizeFileName);
        if(!reviewFile.exists()||!memorizeFile.exists())
        {
            File dir = new File("./Data/"+listName);
            dir.mkdir();
            try
            {
                reviewFile.createNewFile();
                memorizeFile.createNewFile();
                LIST.add(listName);
                LISTUPDATE();
                return true;
            }catch(Exception e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    static public void Import(String listName,String path)
    {
        String fileName = "./Data/"+listName+"/"+listName+".memorize.list";
        System.out.println(fileName);
        System.out.println(path);
        try
        {
            String temp;
            String temp_UTF;
            Scanner in =  new Scanner(new BufferedReader(new FileReader(path)));
            FileWriter writer = new FileWriter(fileName,true);
            boolean LOCK=false;
            StringBuffer record = new StringBuffer();
            Date myDate = new Date();
            while(in.hasNext())
            {
                temp=in.nextLine();
                //System.out.println(temp);

                //note_GBK = new String(note.getBytes("GBK"),"UTF-8");
                if(temp.trim().length()==0)continue;
                //System.out.println(temp+"%%%");

                if(LOCK==false)
                {
                    if(temp.trim().charAt(0)=='*')
                    {

                        LOCK=true;
                        record.setLength(0);
                        record.append("*\r\n"+Long.toString(myDate.getTime())+"\r\n");
                        record.append(0+"\r\n");  // level
                        record.append(0+"\r\n"); // tot cnt
                        record.append(0+"\r\n"); // accurate cnt
                    }
                    else 
                    {
                       // System.out.println(temp+"SB");
                        continue;
                    }
                }
                else
                {
                    if(temp.trim().charAt(0)=='*')
                    {
                        LOCK=false;
                        record.append("*\r\n");
                        //System.out.println(record.toString());
                        writer.write(record.toString());
                    }
                    else
                    {   
                        record.append(temp+"\r\n");
                    }
                }
            }
            writer.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    static public word getWord()
    {
        if(current<len)
        {
            current++;
            while(mylist.get(pointer).visible==false)pointer++;
            System.out.print(pointer);
            return mylist.get(pointer++);
        }
        else return null;
    }
    static public void deleteWord()
    {
        if(pointer>0)
        {
            mylist.get(pointer-1).discard=true;
            current--;
            len--;
        }
        updateDisk();
        return;
    }
    static public void insertWord(String listName,boolean isMemorize,word x)
    {
        String path="./Data/"+listName+"/"+listName;
        if(isMemorize)path+=".memorize";
        else path+=".review";
        path+=".list";
        try
        {
            FileWriter writer = new FileWriter(path,true);
            writer.write(x.toString());
            writer.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return;
    }
    static public word updateWord(boolean known)
    {
        word temp=null;
        if(pointer>0)
        {
            temp=mylist.get(pointer-1);
            if(known==true)
            {
                temp.level++;
                if(temp.level==8)
                {
                    temp.finish=true;
                    insertWord(currentListName,false,temp);
                }
            }
        }
        return temp;
    }
    static private void LISTUPDATE()
    {
        String fileName = "./Data/List.list";
        File listFile = new File(fileName);
        try
        {
            BufferedWriter out=new BufferedWriter(new FileWriter(fileName));
            for(String t:LIST)
            {
                out.write(t+"\r\n");
            }
            out.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    static public void updateDisk()
    {
        try
        {
            FileWriter writer = new FileWriter(currentFilePath);
            for(word k : mylist)
            {
                if(k.discard||k.finish)continue;
                else writer.write(k.toString());
            }
            writer.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

