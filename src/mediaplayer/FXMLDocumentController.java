/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.File;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

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
                    timeSlider();
                    volumeSlider();
                    updateValuesListner();
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
    private Label durationLabel;
    
    @FXML
    private Slider volumeSlider;
    
    @FXML
    private Slider timeSlider;
    
    @FXML
    private void testButton(ActionEvent event)
    {
        volumeSlider.setValue(50);
        selectedMediaPlay.setVolume(50.0);
    }
    
    
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
    
    private void timeSlider()
    {
    duration = selectedMediaPlay.getMedia().getDuration();
        timeSlider.valueProperty().addListener(new InvalidationListener() 
        {
            @Override
            public void invalidated(Observable ov) 
            {
                if (timeSlider.isValueChanging()) 
                {
                selectedMediaPlay.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }   
        });
    }
    
    private void volumeSlider()
    {
        volumeSlider.valueProperty().addListener(new InvalidationListener() 
        {
            public void invalidated(Observable ov) 
            {
                if (volumeSlider.isValueChanging()) 
                {
                    selectedMediaPlay.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
    }
    
private void updateValues() 
{
    if (timeSlider != null && volumeSlider != null) 
    {
        Platform.runLater(new Runnable() 
        {
            public void run() 
            {
                Duration currentTime = selectedMediaPlay.getCurrentTime();
                durationLabel.setText(formatTime(currentTime, duration));
                timeSlider.setDisable(duration.isUnknown());
                
                if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) 
                {
                    timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                }
                
                if (!volumeSlider.isValueChanging()) 
                {
                    volumeSlider.setValue((int)Math.round(selectedMediaPlay.getVolume() * 100));
                }
            }
        });
    }
}

private void updateValuesListner()
{
    selectedMediaPlay.currentTimeProperty().addListener(new InvalidationListener() 
    {
        public void invalidated(Observable ov) 
        {
            updateValues();
        }
    });
}

    
private static String formatTime(Duration elapsed, Duration duration) 
{
   int intElapsed = (int)Math.floor(elapsed.toSeconds());
   int elapsedHours = intElapsed / (60 * 60);
   
   if (elapsedHours > 0) 
   {
       intElapsed -= elapsedHours * 60 * 60;
   }
   int elapsedMinutes = intElapsed / 60;
   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
 
   if (duration.greaterThan(Duration.ZERO)) 
   {
        int intDuration = (int)Math.floor(duration.toSeconds());
        int durationHours = intDuration / (60 * 60);
        
        if (durationHours > 0) 
        {
        intDuration -= durationHours * 60 * 60;
        }
        
        int durationMinutes = intDuration / 60;
        int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
        
        if (durationHours > 0) 
        {
            return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds, durationHours, durationMinutes, durationSeconds);
        }
        else
        {
            return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds,durationMinutes, durationSeconds);
        }
    }
    else
    {
        if (elapsedHours > 0) 
        {
            return String.format("%d:%02d:%02d", elapsedHours,elapsedMinutes, elapsedSeconds);
        }
        else
        {
            return String.format("%02d:%02d",elapsedMinutes, elapsedSeconds);
        }   
    }
}
}
