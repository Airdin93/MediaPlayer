/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer.GUI;

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
 * @author Emil Arentoft
 */
public class MainViewController {
    
    FileChooser fileChooser = new FileChooser();
    private MediaPlayer selectedMediaPlay;
    private Media selectedMedia;
    private File selectedFile;
    private Duration duration;
    
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
    
    /**
     * The playButton is used to play the song while it not playing, and when it
     * is playing it pauses the song, if no song is selected it engages
     * the noMusicError method.
     */
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
    /**
     * The stopButton is used to stop the song, if no song is selected it engages
     * the noMusicError method.
     */
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

    /**
     *  The openSongButton is used to open a new window allowing
     *  the user to select a song.
     */
    @FXML
    private void openSongButton (ActionEvent event)
    {
        singleFileChooser();
    }
    
    
    @FXML
    private void testButton(ActionEvent event)
    {
        volumeSlider.setValue(50);
        selectedMediaPlay.setVolume(50.0);
    }
    
    /**
     * The singleFileChooser method is the file chooser which opens a new
     * window and allows the user to pick a .mp3 file.
     */
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
    
    /**
     * The singleFileChooserEvent method is engaged when the user closes the window
     * of the singleFileChooser method without selecting a file.
     */
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
    
    /**
     * The noMusicError method tells the user that no song have been selected in
     * the player.
     */
    private void noMusicError()
    {
        actionStatusLabel.setText("No Music Chosen!");
    }
    
    /**
     * The timeSlider method adds a time slider to the player, visually 
     * showing how long the song is and allows the user to chose a point in
     * the song he wishes to fast forward to.
     */
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
    
    /**
     * The volumeSlider method adds a volumeSlider to the player allowing
     * the user to turn the volume up and down of the song currently playing.
     */
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
    
    /**
     * The updateValues updates the value of our time and volume sliders
     * if the user interact with the sliders.
     */
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
    
    /**
     * The updateValuesListner is the listner which checks if the user have
     * interacted with the volume and time slider.
     */
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

    /**
     * The formatTime method is used to format the duration and total duration 
     * of the song currently playing.
     */
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
