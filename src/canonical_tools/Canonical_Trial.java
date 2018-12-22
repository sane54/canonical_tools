/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canonical_tools;


import org.jfugue.*;
import java.io.File;
import java.io.IOException;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;


/**
 *
 * @author Owner
 */
public class Canonical_Trial {
        public Worker worker;
        public  Canonical_Trial() {
            worker = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    double completedWorkSteps = 0;
                    double totalWorkSteps =  3;
                    updateProgress(completedWorkSteps, totalWorkSteps);
                    updateMessage("Starting.... ");
                    
                    CleanerPatternTransformer cleanme = new CleanerPatternTransformer();
                    Player jplayer = new Player();
                    Pattern pattern = new Pattern( "T140 V0 G5/0.6875 B5/1.8125 B4/1.625 D#5/1.75 B5/0.4375 B4/0.5625 C5/1.375 V1 C5/0.6875 E5/1.8125 F#5/1.625 C#5/1.75 F#5/0.4375 D5/0.5625 C5/1.375 V2 C1w" );
                    if (InputParameters.getJfugueString()!= null) {
                        System.out.println("got jfugue string: " + InputParameters.getJfugueString() + "|");
                        pattern.setMusicString(InputParameters.getJfugueString());
                    }
                    else if (InputParameters.getInputFile().getAbsolutePath().contains("xml")){
                        MusicXmlParser parser = new MusicXmlParser();
                        MusicStringRenderer render = new MusicStringRenderer();
                        parser.addParserListener(render);
                        parser.parse(new File(InputParameters.getInputFile().getAbsolutePath()));
                        pattern = render.getPattern();
                    }
                    else if (InputParameters.getInputFile() != null)
                        try {
                        pattern = jplayer.loadMidi(new File(InputParameters.getInputFile().getAbsolutePath()));
                        } catch (IOException e)  {
                            System.out.println("File Not Found");
                        } catch (InvalidMidiDataException e) {
                            System.out.println("pattern is not midi");
                        };
                    System.out.println("pattern:");
                    System.out.println(pattern.getMusicString());
                    if (pattern == null) {
                        System.out.println("pattern is null - something is wrong");
                        return "cancelled";
                    }
                        
                    if (isCancelled()) {
                        return "cancelled";
                    }
                    completedWorkSteps++;
                    updateProgress(completedWorkSteps, totalWorkSteps);
                    updateMessage("Pattern Loaded");
                    //File theFile = new File(InputParameters.getInputFile().getAbsolutePath());
                    //MidiFileFormat format = MidiSystem.getMidiFileFormat(theFile);
                    //System.out.println(format.getDivisionType() + "  " + format.getResolution() );
                    //jplayer.playMidiDirectly(theFile);
                    //jplayer.play(pattern);
                    SplitPatternToVoices splitter = new SplitPatternToVoices();
                    pattern = cleanme.transform(pattern);
                    System.out.println("cleaned pattern");
                    System.out.println(pattern.getMusicString());
                    
                    if (isCancelled()) {
                        return "cancelled";
                    }
                    completedWorkSteps++;
                    updateProgress(completedWorkSteps, totalWorkSteps);
                    updateMessage("Pattern Cleaned");
                    
                    if (InputParameters.getTransformer1() != null) {
                        System.out.println("using " + InputParameters.transformer1.toString());
                        pattern = splitter.transform(pattern, InputParameters.getTransformer1());
                        System.out.println("pattern after 1st morph: " + pattern.getMusicString());
                    }
                    else System.out.println("no first morpher");
                        
                    
                    if (isCancelled()) {
                        return "cancelled";
                    }
                    completedWorkSteps++;
                    updateProgress(completedWorkSteps, totalWorkSteps);
                    updateMessage("1st transform built.... ");
                    
                    
                    if (InputParameters.transformer2 != null) {
                        pattern = splitter.transform(pattern, InputParameters.transformer2);
                        System.out.println("pattern after second morph: " + pattern.getMusicString());
                    }
                    else System.out.println("no second morpher");
                        
                    
                    if (isCancelled()) {
                        return "cancelled";
                    }
                    completedWorkSteps++;
                    updateProgress(completedWorkSteps, totalWorkSteps);
                    updateMessage("2nd transform built.... ");
                    
                    if (InputParameters.transformer3 != null) {
                        pattern = splitter.transform(pattern, InputParameters.transformer3);
                        System.out.println("pattern after third morph: " + pattern.getMusicString());
                    }
                    else System.out.println("no third morpher");    
                    
                    if (isCancelled()) {
                        return "cancelled";
                    }
                    completedWorkSteps++;
                    updateProgress(completedWorkSteps, totalWorkSteps);
                    updateMessage("3rd transform built.... Done ");
                    
                    if (InputParameters.get_q_mode()) PatternQueueStorerSaver.add_pattern_to_queue(pattern);
                    else {
                        PatternStorerSaver1.clear_pattern();
                        PatternStorerSaver1.add_pattern(pattern);
                    }
                    //pattern.setMusicString("");
                    System.out.println("about to be done");
                    return "done";
                };
            };
        }
    public void resetParams() {
        
    }
}
