package Engine.review;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import Engine.controlManager.controlManager;
import Engine.word.word;
import Engine.wordListManager.wordListManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class review implements Initializable
{
    
    @FXML
    private WebView myPage;


    @FXML
    private Text ENGWORD;
    @FXML
    private Text Progress;
    @FXML
    private Text noteField;
    @FXML
    private Button Button_Next;
    @FXML
    private Button Button_Delete;
    @FXML
    private Button Button_Known;
    @FXML
    private Button Button_Unknown;


    private WebEngine myEngine;

    private Alert message = new Alert(Alert.AlertType.INFORMATION);


    private String ENG,note;
    private int level;
    private long date;
    private int totCnt,accurateCnt;
    private boolean myLock;


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        myEngine=myPage.getEngine();
        myEngine.load("https://cn.bing.com/dict/HelloWorld");
        
        myPage.setFontScale(1.5);
        //myPage.setScaleX(1.0);
        //myPage.setScaleY(1.0);
        message.setTitle("Easy-Word By Unispac");
        message.setHeaderText("About This Software");
        message.setContentText(
            "Easy-Word is a free software developed by Unispac, \r\n"
            +
            "which aims to help people memorize English words more conveniently."
        );
        ENGWORD.setText("HelloWorld");
        myPage.setOpacity(0);
        totCnt=0;
        accurateCnt=0;
        //Next();
        Button_Next.setDisable(false);
        Button_Delete.setDisable(true);
        Button_Known.setDisable(true);
        Button_Unknown.setDisable(true);
        Button_Next.setText("START");
        totCnt=accurateCnt=0;
    }
    static public void touch()
    {

    }
    public void toExit()
    {
        System.exit(0);
    }
    public void showHelpInfo()
    {
        message.show();
        return;
    }
    private void showNote(word currentWord)
    {
        myPage.setOpacity(1);
        //Button_Next.setOpacity(1);
        Button_Next.setDisable(false);
        Button_Delete.setDisable(false);
        Button_Known.setDisable(true);
        Button_Unknown.setDisable(true);
        //String note_GBK;
        try
        {
            noteField.setText(note);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }   
    public void Known()
    {
        if(myLock==true)return;
        myLock=true;
        accurateCnt++;
        word currentWord = wordListManager.updateWord(false);
        showNote(currentWord);
    }
    public void Unknown()
    {
        if(myLock==true)return;
        myLock=true;
        word currentWord = wordListManager.updateWord(false);
        showNote(currentWord);
        currentWord.discard=true;
        currentWord.accurate=currentWord.tot=0;
        currentWord.level=0;
        currentWord.date=(new Date()).getTime();
        wordListManager.insertWord(wordListManager.currentListName, true, currentWord);
        wordListManager.updateDisk();
    }
    public void deleteFromList()
    {
        wordListManager.deleteWord();
        Next();
    }
    public void Next()
    {
        Button_Next.setText("Next");
        myLock=false;
        myPage.setOpacity(0);
        //Button_Next.setOpacity(0);
        Button_Next.setDisable(true);
        Button_Delete.setDisable(true);
        Button_Known.setDisable(false);
        Button_Unknown.setDisable(false);

        noteField.setText("");
        word current = wordListManager.getWord();
        if(current==null)
        {
            Button_Known.setDisable(true);
            Button_Unknown.setDisable(true);
            Alert Info = new Alert(Alert.AlertType.INFORMATION);
            Info.setTitle("Finish");
            Info.setHeaderText("Congratulation!!!");
            Info.setContentText("You have finished current memorizing task!!!\r\n"
                +
                "Accuracy : "+accurateCnt+"/"+totCnt
            );
            Info.show();
            //System.out.print("DONE");
            controlManager.showPage("home");
            return;
        }
        totCnt++;
        ENG=current.ENG;
        note=current.note;
        level=current.level;
        date=current.date;
        myEngine.load("https://cn.bing.com/dict/"+ENG);
        ENGWORD.setText(ENG);
    
        Progress.setText((wordListManager.current)+"/"+wordListManager.len);

        long last=date;
        if(level!=0)last-=wordListManager.Interval.get(level-1);
        //lastTime.setText("Last Time : "+(new Date(last)).toString());
        //nextTime.setText("Next Time : "+" - ");
        //History.setText("");
    }
    public void mainPage()
    {
        controlManager.showPage("home");
    }
}