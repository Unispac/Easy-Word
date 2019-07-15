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

public class wordListManager
{
    //static String listName=null;
    static boolean mode;
    static public ArrayList<word>mylist = new ArrayList<word>();
    static public ArrayList<String>LIST = new ArrayList<String>();
    static int len;
    static int current;
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
    }
    static public void pickList(boolean isMemorize)
    {
        String listName = "haha";
        String fileName = "./Data/"+listName;
        
        if(isMemorize)
        {
            fileName+=".memorize";
        }
        else
        {
            fileName+=".review";
        }
        fileName+=".list";
        
        String ENG;
        String CNE;
        String Time;
        String Level;

        try
        {
            Scanner in =  new Scanner(new BufferedReader(new FileReader(fileName)));   
            in.nextLine();
        }catch(Exception e)
        {
            
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
            //System.out.println("hello?");
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
            Scanner in =  new Scanner(new BufferedReader(new FileReader(path)));
            FileWriter writer = new FileWriter(fileName,true);
            boolean LOCK=false;
            StringBuffer record = new StringBuffer();
            Date myDate = new Date();
            while(in.hasNextLine())
            {
                temp=in.nextLine();
                if(temp.trim().length()==0)continue;
                if(LOCK==false)
                {
                    if(temp.trim().charAt(0)=='*')
                    {
                        LOCK=true;
                        record.setLength(0);
                        record.append("*\r\n"+Long.toString(myDate.getTime())+"\r\n");
                    }
                    else continue;
                }
                else
                {
                    if(temp.trim().charAt(0)=='*')
                    {
                        LOCK=false;
                        record.append("*\r\n");
                        System.out.println(record.toString());
                        writer.write(record.toString());
                    }
                    else
                    {
                        record.append(temp+"\r\n");
                    }
                }
            }
            writer.close();


            /*String Vocabulary;
            String Note=""; 
            
            while(in.hasNext())
            {
                temp=in.nextLine();
                if(LOCK==false)
                {
                    if(temp.trim().charAt(0)=='*'){LOCK=true;FIRST=true;}
                    else continue;
                }
                else
                {
                    if(temp.trim().charAt(0)=='*')LOCK=false;
                    else
                    {
                        if(FIRST==true)
                        {
                            FIRST=false;
                            Vocabulary=temp;
                            Note="";
                        }
                        else
                        {
                            Note+=(temp+"\r\n");
                        }
                    }
                }
            }*/
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    static public word getWord()
    {
        return mylist.get(current);
    }
    static public void updateWord()
    {

    }
    static private void LISTUPDATE()
    {
        String fileName = "./Data/List.list";
        File listFile = new File(fileName);
        try
        {
            BufferedWriter out=new BufferedWriter(new FileWriter(fileName));
            //Scanner out =  new Scanner(new BufferedReader(new FileReader(fileName)));
            for(String t:LIST)
            {
                System.out.println(t);
                out.write(t+"\r\n");
            }
            out.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

