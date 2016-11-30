/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

/**
 *
 * @author EmilArentoft
 */
public class FXMLDocumentController {
    
//    Media media = new Media(new File("C:/08 - Jason Hayes - Stormwind (City Theme).mp3").toURI().toString());
//    MediaPlayer mediaPlayer = new MediaPlayer(media);
    FileChooser fileChooser = new FileChooser();
    public MediaPlayer selectedMediaPlay;
    public Media selectedMedia;
    public File selectedFile;
    
    @FXML
    private void playButton(ActionEvent event)  {
        selectedMediaPlay.play();
    }
    
    @FXML
    private void stopButton(ActionEvent event) {
        selectedMediaPlay.stop();
    }

    @FXML
    private void pauseButton(ActionEvent event) {
        selectedMediaPlay.pause();
    }

    @FXML
    private void openSong (ActionEvent event)
    {
        singleFileChooser();
    }

    @FXML
    private Label actionStatusLabel;
    
    public void singleFileChooser() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("mp3 files (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);
        selectedFile = fileChooser.showOpenDialog(null);
//        fileGet();
        
	if (selectedFile != null) 
        {
            actionStatusLabel.setText(selectedFile.getPath());
            selectedMedia = new Media(new File(selectedFile.getPath()).toURI().toString());
            selectedMediaPlay = new MediaPlayer(selectedMedia);
	}
	else 
        {
            actionStatusLabel.setText("No file chosen!");
	}   
    }
    
    public void fileGet()
    {
        File selectedFile = fileChooser.showOpenDialog(null);
    }
}
