package Engine.review;

import java.net.URL;
import java.util.ResourceBundle;

import Engine.controlManager.controlManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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



    private WebEngine myEngine;

    private Alert message = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        myEngine=myPage.getEngine();
        myEngine.load("https://cn.bing.com/dict/HelloWorld");
        
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
        ENGWORD.setText("HelloWorld");
        myPage.setOpacity(0);
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
    public void Known()
    {
        myPage.setOpacity(1);
    }
    public void Unknown()
    {
        myPage.setOpacity(1);
    }
    public void deleteFromList()
    {
        
    }
    public void Next()
    {
        myPage.setOpacity(0);
    }
    public void mainPage()
    {
        controlManager.showPage("home");
    }
}