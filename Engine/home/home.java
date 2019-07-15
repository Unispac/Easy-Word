package Engine.home;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import Engine.controlManager.controlManager;
import Engine.wordListManager.wordListManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class home implements Initializable
{
    @FXML
    private WebView myPage;

    private WebEngine myEngine;
    private Alert message = new Alert(Alert.AlertType.INFORMATION);
    private FileChooser importer;

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        myEngine=myPage.getEngine();
        myEngine.load("https://cn.bing.com/dict/HelloWorld");
        //myPage.setFontScale(1.5);
        myPage.setFontScale(1.5);
        myPage.setScaleX(0.9);
        myPage.setScaleY(0.9);
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
        controlManager.showPage("review");
    }
    public void Memorize()
    {
        controlManager.showPage("memorize");
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

        File target  = importer.showOpenDialog(controlManager.currentStage);
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
}