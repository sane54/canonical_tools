package canonical_tools;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jfugue.Pattern;

/**
 *
 * @author Owner
 */
public class FXMLDocumentController implements Initializable {
    Stage ctrlStage = new Stage();
    private Label label;
    @FXML
    private Font x1;
    @FXML
    private Font x2;
    @FXML
    private Button fileChooserButton;
    @FXML
    private Button runButton = new Button();
    @FXML
    private ComboBox<String> toolChooser1 = new ComboBox();
    @FXML
    private ComboBox<String> toolChooser2 = new ComboBox();
    @FXML
    private ComboBox<String> toolChooser3 = new ComboBox();
    @FXML
    private CheckBox midiOut = new CheckBox();
    @FXML
    private CheckBox queueMode = new CheckBox();
    @FXML
    private TextField enterString = new TextField();  
    @FXML
    private TextField fulcrumField = new TextField(); 
    @FXML
    private TextField transposeField = new TextField();
    
    @FXML
    Button PlayButton = new Button();
    
    @FXML
    Button PauseButton = new Button();
    
    @FXML
    Button StopButton = new Button();
    
    @FXML
    private void handleFileChooserButtonAction(ActionEvent event) {
        if (fileChooserButton.isDisabled()) {
            MessageBox.show( "You have to wait until the current run is over", "Be Patient");

        }
        else {
            final FileChooser fileChooser = new FileChooser();
            if (InputParameters.getFileDir() != null) {
                File dirLoc = InputParameters.getFileDir();
                System.out.println(dirLoc);
                fileChooser.setInitialDirectory(dirLoc);
            }
            File file = fileChooser.showOpenDialog(ctrlStage);
            InputParameters.setInputFile(file);
        }
    }

    @FXML
    private void setOutputFile() {
            Date today = new Date(System.currentTimeMillis());
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy-HH-mm");
            String dateString = DATE_FORMAT.format(today);
            int tempo_bpm = InputParameters.getTempo();
            final FileChooser fileChooser = new FileChooser();
            if (InputParameters.getFileDir() != null) {
                File dirLoc = InputParameters.getFileDir();
                System.out.println(dirLoc);
                fileChooser.setInitialDirectory(dirLoc);
            }
            fileChooser.setInitialFileName(tempo_bpm + "-" + dateString);
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MIDI", "*.mid"),
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("ALL", "*.*")    
            );
            File file = fileChooser.showSaveDialog(ctrlStage);
            if (file != null) {
                File midi_file = new File(file.getAbsolutePath() + ".mid");
                InputParameters.setFilePath(midi_file);
            }        
        }
    
    @FXML
    public void handleRunButton (ActionEvent event) {
        Pattern pattern = new Pattern();
        if (!"".equals(fulcrumField.getText())) InputParameters.setFulcrum(Byte.parseByte(fulcrumField.getText()));
        if (!"".equals(transposeField.getText())) InputParameters.setTranspose(Integer.parseInt(transposeField.getText()));
        if (toolChooser1.getValue() != null )InputParameters.setTransformer1(toolChooser1.getValue());
        if (toolChooser2.getValue() != null )InputParameters.setTransformer2(toolChooser2.getValue());
        if (toolChooser3.getValue() != null )InputParameters.setTransformer3(toolChooser3.getValue());
        System.out.println("STARTING A NEW SET OF TRANSFORMS");
        System.out.println("*********************************");
        System.out.println("toolChooser1  = " + toolChooser1.getValue());
        System.out.println("toolChooser2  = " + toolChooser2.getValue());
        System.out.println("toolChooser3  = " + toolChooser3.getValue());

        System.out.println(enterString.getText() + " " + transposeField.getText() + " " + fulcrumField.getText() );
        if (enterString.getText().length()> 2) InputParameters.jfugueString = enterString.getText();
        
        if(midiOut.isSelected()) {
            InputParameters.set_out_to_midi_yoke(true);
        }
        else InputParameters.set_out_to_midi_yoke(false);
        
        if(queueMode.isSelected()) InputParameters.set_q_mode(true);
        else InputParameters.set_q_mode(false);
        
        runButton.setDisable(true);
        fileChooserButton.setDisable(true);
        
        Canonical_Trial model = new Canonical_Trial();
        Thread generatorThread = new Thread((Runnable) model.worker);
        generatorThread.start();
        
        model.worker.runningProperty().addListener(
            (ObservableValue<? extends Boolean> observable, Boolean oldvalue, Boolean newvalue) -> {
         
                if(newvalue.equals(false)) {
                    //Buttons disabled when thread is running - re-enabled when thread stops
                    runButton.setDisable(false);
                    fileChooserButton.setDisable(false);
                    if (model.worker.getState().equals(model.worker.getState().CANCELLED)) return;
                        playSaveDialog();

                } 
        });
        
        //build the cancel screen
        Stage stage = new Stage();
        stage.setMinWidth(250);
        stage.setTitle("Transforming");
        
        ProgressBar pb = new ProgressBar();
        pb.progressProperty().bind(model.worker.progressProperty());
        pb.progressProperty().addListener(
            (observable, oldvalue, newvalue) -> {
                if((double)newvalue == 1.0) {
                    stage.close();
                }    
        });  
        
        
        Label mylabel = new Label("");
        mylabel.textProperty().bind(model.worker.messageProperty());
        
        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(c -> {
            mylabel.textProperty().unbind();
            mylabel.setText("Cancelling... please wait");
            model.worker.cancel();
            stage.close();
        });
        
        VBox pane = new VBox(20);
        pane.getChildren().addAll(pb, mylabel, cancelButton);
        pane.setAlignment(Pos.CENTER);
        pane.setMinWidth(400);
        pane.setStyle("-fx-font: 11px \"Lucida Sans Unicode\"; -fx-padding: 10; -fx-background-color: beige;");
        
        Group mystuff = new Group();
        mystuff.getChildren().addAll(pane);
        stage.setScene(new Scene(mystuff));
        stage.show(); 
    }
    
        public void playSaveDialog() {
        boolean proceed;
        PlayButton.setVisible(true);
        PauseButton.setVisible(true);
        StopButton.setVisible(true);
        String pattern_or_queue = "Pattern?";
        if (InputParameters.get_q_mode() == true) {
            pattern_or_queue = "Queue?";
        }
        else PatternQueueStorerSaver.clear_queue();
        
        CancelBox.show("Play " + pattern_or_queue, " ");
        proceed = CancelBox.getProceed();
        if (proceed) {
            PlayerBox myPlayerBox = new PlayerBox();
        }
        CancelBox.show("Save " + pattern_or_queue, " ");
        proceed = CancelBox.getProceed();
        if (proceed) {
            if (InputParameters.get_q_mode() == true) {
                boolean newQDir = true;
                if(InputParameters.getQueueDir() != null) {
                    CancelBox.show("Change Queue Directory", InputParameters.getQueueDir().getAbsolutePath());
                    newQDir = CancelBox.getProceed();
                }
                if (newQDir) {
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    File selectedDirectory = directoryChooser.showDialog(ctrlStage);
                    if (selectedDirectory != null) {
                        InputParameters.setQueueDirectory(selectedDirectory.getAbsolutePath());
                        InputParameters.setQueueDir(selectedDirectory);
                    }                    
                }

                PatternQueueStorerSaver.save_queue();
            }
            else {
                if (InputParameters.getFilePath() == null) setOutputFile();
                PatternStorerSaver1.save_pattern();
                PatternStorerSaver1.clear_pattern();
            }
        }
        if (InputParameters.get_q_mode() == false) {
            PatternStorerSaver1.clear_pattern();
        }
        
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        transposeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                String oldValue, String newValue) {
                int tpose= 0;
                try {
                    tpose = Integer.parseInt(newValue);
                }//end try
                catch (NumberFormatException e) {
                    transposeField.setText("12");
                }//end catch  
                if (tpose < -24 || tpose > 24) {
                Platform.runLater(() -> { transposeField.clear();});
                }//end if block
            }//ends changed method
        });
        transposeField.setTooltip(new Tooltip 
                        ("has to be between -24 and 24"));
        
        fulcrumField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                String oldValue, String newValue) {
                int fc = 60;
                try {
                    fc = Byte.parseByte(newValue);
                }//end try
                catch (NumberFormatException e) {
                    fulcrumField.setText("60");
                }//end catch   
                if (fc < 1 || fc > 100) {
                Platform.runLater(() -> { fulcrumField.clear();});
                }//end if block
            }//ends changed method
        });
        fulcrumField.setTooltip(new Tooltip 
                        ("has to be between 1 and 128"));    
        
    }    
    
}
