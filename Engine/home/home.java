package Engine.home;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Engine.controlManager.controlManager;
import Engine.word.word;
import Engine.wordListManager.wordListManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class home implements Initializable
{
    @FXML
    private WebView myPage;
    @FXML
    private TextArea noteField;
    @FXML
    private Button Button_Insert;
    @FXML
    private ChoiceBox<String>targetList;
    @FXML
    private Text myWord;
    @FXML 
    private Text HINT;

    static public WebEngine myEngine;
    private Alert message = new Alert(Alert.AlertType.INFORMATION);
    private FileChooser importer;
    private Timer webTimer,hintTimer;


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        myEngine=myPage.getEngine();
        myEngine.load("https://cn.bing.com/dict/HelloWorld");
        //myPage.setFontScale(1.5);
        myPage.setFontScale(1.5);
        //myPage.setScaleX(0.9);
        //myPage.setScaleY(0.9);
        message.setTitle("Easy-Word By Unispac");
        message.setHeaderText("About This Software");
        message.setContentText(
            "Easy-Word is a free software developed by Unispac, \r\n"
            +
            "which aims to help people memorize English words more conveniently."
        );
        importer = new FileChooser();
        importer.setTitle("Import From");
        importer.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("wordFile", "*.words")
        );
        //targetList
    
        updateChoiceBox();
        setTimer();
    }
    private void setTimer()
    {
        webTimer = new Timer();
        webTimer.schedule(new TimerTask() 
        {
            public void run() 
            {
                String temp= myEngine.getTitle();
                //System.out.println(temp);
                if(temp!=null)
                {
                    int k = temp.indexOf(" -");
                    if(k!=-1)myWord.setText(temp.substring(0, k));
                    //myWord.setAccessibleHelp(temp);
                }
            }
        }, 0,500);

        hintTimer = new Timer();
        hintTimer.schedule(new TimerTask() 
        {
            public void run() 
            {
                HINT.setText("");
            }
        }, 0,4000);
    }
    private void updateChoiceBox()
    {
        /*
        List<String>tempList = new ArrayList<String>();
        
        targetList.setItems(FXCollections.observableArrayList(tempList));
        */
        targetList.setItems(FXCollections.observableArrayList());
        ObservableList<String>temp = targetList.getItems();
        for(String k : wordListManager.LIST)
        {
            temp.add(k);
        }
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

    public void Review()
    {
        List<String> options = new ArrayList<String>(wordListManager.LIST);
        boolean empty=false;
        if(options.size()==0)
        {
            options.add("");
            empty=true;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<String>(options.get(0),options);
        dialog.setTitle("Import Words");
        dialog.setHeaderText("Choose the target list.");
        dialog.setContentText("Target List : ");
        Optional<String> result = dialog.showAndWait();
        if(empty==true)return;
        String listName=null;
        if(result.isPresent())listName=result.get();
        else return;

        webTimer.cancel();
        if(wordListManager.pickList(listName, false))controlManager.showPage("review");
        
        return;
    }

    public void Memorize()
    {
        List<String> options = new ArrayList<String>(wordListManager.LIST);
        boolean empty=false;
        if(options.size()==0)
        {
            options.add("");
            empty=true;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<String>(options.get(0),options);
        dialog.setTitle("Import Words");
        dialog.setHeaderText("Choose the target list.");
        dialog.setContentText("Target List : ");
        Optional<String> result = dialog.showAndWait();
        if(empty==true)return;
        String listName=null;
        if(result.isPresent())listName=result.get();
        else return;

        webTimer.cancel();
        if(wordListManager.pickList(listName, true))controlManager.showPage("memorize");
        
        return;
    }

    public void Import()
    {
        boolean empty=false;
       //rrayList<String> H = new ArrayList<String>();
        List<String> options = new ArrayList<String>(wordListManager.LIST);
        if(options.size()==0)
        {
            options.add("");
            empty=true;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<String>(options.get(0),options);
        dialog.setTitle("Import Words");
        dialog.setHeaderText("Choose the target list.");
        dialog.setContentText("Target List : ");
        Optional<String> result = dialog.showAndWait();
        if(empty==true)return;
        String listName=null;
        if(result.isPresent())listName=result.get();
        else return;

        File target  = importer.showOpenDialog(controlManager.currentStage);
        if(target==null)return;
        String path = target.getAbsolutePath();

        wordListManager.Import(listName,path);
    }

    public void createNewList()
    {
        String listName;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Create New Word List");
        dialog.setHeaderText("New Word List");
        dialog.setContentText("List Name : ");
        Optional<String>result = dialog.showAndWait();
        if(result.isPresent()==false)return;
        listName = result.get();
        
        if(listName==null||listName.trim().equals(""))return;

        //System.out.println("CHECK : "+listName);

        boolean state = wordListManager.createList(listName);
        Alert resultInfo = new Alert(Alert.AlertType.INFORMATION);
        resultInfo.setTitle("Create New Word List");
        if(state==true)
        {
            resultInfo.setHeaderText("Success!!!");
            resultInfo.setContentText("Successfully create new word list \" " +listName + " \" .");
            resultInfo.show();
            updateChoiceBox();
        }
        else
        {
            resultInfo.setHeaderText("Fail!!!");
            resultInfo.setContentText("Fail to create new word list \" " +listName + " \" .\r\n"
                +
                "The list has already existed ? "
            );
            resultInfo.show();
        }
    }

    public void insertItem()
    {
        String result = targetList.getSelectionModel().getSelectedItem();
        if(result!=null)
        {
            String listName = result;
            String ENG = myWord.getText();
            String note = noteField.getText()+"\r\n";
            long date = (new Date()).getTime();
            word temp = new word(ENG, date, 0, note, 0, 0);
            wordListManager.insertWord(listName, true, temp);
            HINT.setText("Done : Insert "+ENG+" to "+listName+".");
        }
        else
        {
            HINT.setText("No Target List !");
        }        
    }
}