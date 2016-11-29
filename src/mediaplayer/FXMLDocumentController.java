/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.File;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

/**
 *
 * @author EmilArentoft
 */
public class FXMLDocumentController {
    
    Media media = new Media(new File("C:/08 - Jason Hayes - Stormwind (City Theme).mp3").toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    FileChooser fileChooser = new FileChooser();
    
    @FXML
    private void playButton(ActionEvent event) {
        mediaPlayer.play();
    }
    
    @FXML
    private void stopButton(ActionEvent event) {
        mediaPlayer.stop();
    }

    @FXML
    private void pauseButton(ActionEvent event) {
        mediaPlayer.pause();
//        mediaPlayer.getMedia();
    }

    @FXML
    private void openSong (ActionEvent event)
    {
        singleFileChooser();
    }

    
    private void singleFileChooser() {
	
	FileChooser fileChooser = new FileChooser();
	File selectedFile = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.mp3"));
        
	if (selectedFile != null) 
        {
            System.out.println("No file Chosen!");
	}
	else 
        {
//		actionStatus.setText("File selection cancelled.");
	}
    }
    
}
