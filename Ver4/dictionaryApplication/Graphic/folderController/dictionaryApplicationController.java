package dictionaryApplication.Graphic.folderController;

import dictionaryApplication.BasicDict.DictionaryManagement;
import dictionaryApplication.BasicDict.Word;
import dictionaryApplication.textToSpeech.TextToSpeech;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import static dictionaryApplication.dictionaryApplication.catchingException;
import static dictionaryApplication.dictionaryApplication.window;
import static dictionaryApplication.dictionaryApplication.dict;

public class dictionaryApplicationController implements Initializable {
    
    //region dictionaryApplication scene variables
    @FXML public TextField input;
    @FXML public Button searchButton;
    @FXML public Button luckyButton;
    @FXML public Label explainLabel;
    @FXML public ListView<String> relatedTarget;
    @FXML public MenuItem menuItemAdd, menuItemEdit, menuItemDelete;
    @FXML public MenuBar menuBar;
    //endregion
    
    //region Init
    
    /**
     * Init Controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create ArrayList to store possible Target in List View
        ArrayList<String> possibleTarget = new ArrayList<>();
        for (int i = 0; i < dict.getSize(); i++) {
            possibleTarget.add(dict.getDict().get(i).getTarget());
        }
        // Set possible target to list view using observable list
        ObservableList<String> targetForListView = FXCollections.observableArrayList(possibleTarget);
        relatedTarget.setItems(targetForListView);
        
        // Bind auto complete textfield
        TextFields.bindAutoCompletion(input, possibleTarget);
    }
    //endregion
    
    //region dictionaryApplication handle method
    
    /**
     * handle search button
     * @param actionEvent
     */
    @FXML
    public void handleSearchButton(ActionEvent actionEvent) {
        String textSet = new String();
        String searchingInput = Word.standardizeString(input.getText());
        if (searchingInput.isEmpty()){
            textSet = "Chưa nhập từ à ";
        }
        else {
            if (DictionaryManagement.dictionarySearcher(dict, searchingInput, relatedTarget) != -1) {
                int indexSearch = DictionaryManagement.dictionarySearcher(dict, searchingInput, relatedTarget);
                Word inputWord = dict.getDict().get(indexSearch);
                textSet = inputWord.getExplain();
            }

            else {
                textSet = "Không có đâu";
            }
        }
        explainLabel.setText(textSet);
    }
    
    @FXML
    public void handleSpeakButton(ActionEvent event) {
        TextToSpeech tts = new TextToSpeech();
        
        tts.setVoice("cmu-slt-hsmm");
        if (!input.getText().equals("")) {
            tts.speak(input.getText(), 2.0f, false, false);
        }
    }
    
    /**
     * handle menu item "add"
     * @param event
     */
    @FXML
    public void handleMenuItemAdd(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/dictionaryApplication/Graphic/FXML/addWord.fxml"));
            window.setScene(new Scene(root));
            window.show();
        }
        catch (Exception e) {
            catchingException(e);
        }
    }

    /**
     * handle menu item "Edit"
     * @param actionEvent
     */
    public void handleMenuItemEdit(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/dictionaryApplication/Graphic/FXML/editWord.fxml"));
            window.setScene(new Scene(root));
            window.show();
        }
        catch (Exception e) {
            catchingException(e);
        }
    }

    /**
     * handle menu item "Delete"
     * @param actionEvent
     */
    public void handleMenuItemDelete(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/dictionaryApplication/Graphic/FXML/deleteWord.fxml"));
            window.setScene(new Scene(root));
            window.show();
        }
        catch (Exception e) {
            catchingException(e);
        }
    }

    /**
     * Close window
     * @param event
     */
    @FXML
    public void handleMenuItemClose(ActionEvent event) {
        // Exit user inteface
        Platform.exit();
        // Exit system
        System.exit(0);
    }
    
    @FXML
    public void handleMenuItemGoogleTranslate(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/dictionaryApplication/Graphic/FXML/googleTranslate.fxml"));
            window.setScene(new Scene(root));
            window.show();
        }
        catch (Exception e) {
            catchingException(e);
        }
    }
    //endregion
}
