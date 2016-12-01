/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.File;
import java.util.Map;
import java.util.Observable;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;


import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 *
 * @author EmilArentoft
 */
public class FXMLDocumentController {
    
    FileChooser fileChooser = new FileChooser();
    private MediaPlayer selectedMediaPlay;
    private Media selectedMedia;
    private File selectedFile;
    private Duration duration;

    
    @FXML
    private void playButton(ActionEvent event)  
    {
        
        if (selectedMediaPlay != null)
        {
            if (selectedMediaPlay.getStatus() == MediaPlayer.Status.PLAYING)
                {
                    selectedMediaPlay.pause();
                    playStatusLabel.setText("Play");
                }
            else
                if (selectedMediaPlay.getStatus() == MediaPlayer.Status.PAUSED)
                {
                    selectedMediaPlay.play();
                    playStatusLabel.setText("Pause");
                }
            else
                {
                    selectedMediaPlay.play();
                    playStatusLabel.setText("Pause");
                    totalDuration();
                    duration();
                }
        }
        else
        {
            noMusicError();
        }
    }
    
    @FXML
    private void stopButton(ActionEvent event) 
    {
        if (selectedMediaPlay != null)
        {
            selectedMediaPlay.stop();
            playStatusLabel.setText("Play");
        }
        else
        {
            noMusicError();
        }
    }


    @FXML
    private void openSong (ActionEvent event)
    {
        singleFileChooser();
    }

    @FXML
    private Label actionStatusLabel;
    
    @FXML
    private Label playStatusLabel;
    
    @FXML
    private Label totalDurationLabel;
    
    @FXML
    private Label durationLabel;
    
    @FXML
    private Slider volumeSlider;
    
    @FXML
    private Slider time;
    
    
    private void singleFileChooser() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("mp3 files (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);
        selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedMediaPlay != null) 
        {   
            selectedMediaPlay.stop();
            singleFileChooserEvent();
        }
        else
        {
            singleFileChooserEvent();
        }
    }
    
    private void singleFileChooserEvent()
    {
        if (selectedFile != null) 
        {
            actionStatusLabel.setText(selectedFile.getName());
            selectedMedia = new Media(new File(selectedFile.getPath()).toURI().toString());
            selectedMediaPlay = new MediaPlayer(selectedMedia);
        }
        else 
        {
            noMusicError();
        }
    }
    
    private void noMusicError()
    {
        actionStatusLabel.setText("No Music Chosen!");
    }
    
    private void totalDuration()
    {
        durationLabel.setText("Duration: "+selectedMedia.getDuration().toSeconds());
    }
    
    private void duration()
    {
//        duration = selectedMediaPlay.getCurrentTime().toSeconds();
        totalDurationLabel.setText("Duration: "+selectedMediaPlay.getCurrentTime().toSeconds());
    }
    //TODO
//    Double d = 3.22;
//    int i = d.intValue();
    
    
//    public void duration(){
//            selectedMediaPlay.currentTimeProperty().addListener(new InvalidationListener() 
//        {
//            public void invalidated(Observable ov) {
//                updateValues();
//                duration = selectedMediaPlay.getMedia().getDuration();
//            }
//        });
//    }
//    
//    protected void updateValues() {
//  if (durationLabel != null) {
//     Platform.runLater(new Runnable() {
//        public void run() {
//          Duration currentTime = selectedMediaPlay.getCurrentTime();
//          durationLabel.setText(selectedMediaPlay.getCurrentTime().););
//          }
//          }
//        }
//     });


    
//    public void duration(){
//    selectedMediaPlay.currentTimeProperty().addListener((Observable ov) -> {
//    updateValues();
//    });
//
//    selectedMediaPlay.setOnReady(() -> {
//    duration = selectedMediaPlay.getMedia().getDuration();
//    updateValues();
//    });
//    }
//    
//    protected void updateValues() {
//    if (durationLabel != null) {
//    runLater(() -> {
//    Duration currentTime = selectedMediaPlay.getCurrentTime();
//    durationLabel.setText(formatTime(currentTime, duration));
//
//});
//}
//}
    
}
