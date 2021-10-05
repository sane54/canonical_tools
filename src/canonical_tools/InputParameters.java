package canonical_tools;

/*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import java.io.File;
import org.jfugue.Note;
import org.jfugue.PatternTransformer;
import org.jfugue.extras.InvertPatternTransformer;
import org.jfugue.extras.IntervalPatternTransformer;
//import org.jfugue.extras.ReversePatternTransformer;

/**
 * Stores and controls access to application input parameters.
 * Default values aren't really used as they are set to whatever is 
 * specified in the input GUI. 
 * @author Trick's Music Boxes
 */
public class InputParameters {
static Boolean out_to_midi_yoke = false;
static Boolean q_mode = false;
static int tempo_bpm = 120;
static File inputFilePath = null;
static File outputFilePath = null;
static File inputFileDir= null;
static File outputQueueDir = null;
static File outputFileDir = null;
static String queue_directory = null;
static String jfugueString = null;
static PatternTransformer transformer1 = null;
static PatternTransformer transformer2 = null;
static PatternTransformer transformer3 = null;
static byte fulcrum_value = 78;
static Note Fulcrum = new Note(fulcrum_value);
static int transposeInterval = 0;
public static void setInputFile(File file) {
    inputFilePath = file;
}

public static File getInputFile(){
    return inputFilePath;
}

public static Boolean get_out_to_midi_yoke () {
    return out_to_midi_yoke;
}
 public static void setQueueDirectory(String direct) {
     queue_directory = direct;
 }
 
 public static String getQueueDirectory() {
     return queue_directory;
 }
 public static void setQueueDir(File direct) {
     outputQueueDir = direct;
 }
 public static void setTransformer1(String transformer_string) {
     if (" ".equals(transformer_string)) {
         transformer1 = null;   
     }
     if ("Retrograde".equals(transformer_string)) {
         transformer1 = new JReversePatternTransformer();   
     }
     if ("Inversion".equals(transformer_string)) {
         transformer1 = new InversionPatternTransformer();   
     }
     if ("Fulcrum Inversion".equals(transformer_string)) {
         transformer1 = new InvertPatternTransformer(Fulcrum);   
     }
     if ("Rhythm Preserving Retrograde".equals(transformer_string)) {
         transformer1 = new ReverseRhythmPreservingPatternTransformer();   
     }
     if ("Transpose".equals(transformer_string)) {
         transformer1 = new IntervalPatternTransformer(transposeInterval);   
     }     
 }
 
  public static PatternTransformer getTransformer1() {
      return transformer1;
  }
  public static void setTransformer2(String transformer_string) {
     if ("".equals(transformer_string)) {
         transformer2 = null;   
     }
      if ("Retrograde".equals(transformer_string)) {
         transformer2 = new JReversePatternTransformer();   
     }
     if ("Inversion".equals(transformer_string)) {
         transformer2 = new InversionPatternTransformer();   
     }
     if ("Fulcrum Inversion".equals(transformer_string)) {
         transformer2 = new InvertPatternTransformer(Fulcrum);   
     }
     if ("Rhythm Preserving Retrograde".equals(transformer_string)) {
         transformer2 = new ReverseRhythmPreservingPatternTransformer();   
     }
     if ("Transpose".equals(transformer_string)) {
         transformer2 = new IntervalPatternTransformer(transposeInterval);    
     }     
 }
 
  public static void setTransformer3(String transformer_string) {
     if ("".equals(transformer_string)) {
         transformer3 = null;   
     }     
     if ("Retrograde".equals(transformer_string)) {
         transformer3 = new JReversePatternTransformer();   
     }
     if ("Inversion".equals(transformer_string)) {
         transformer3 = new InversionPatternTransformer();   
     }
     if ("Fulcrum Inversion".equals(transformer_string)) {
         transformer3 = new InvertPatternTransformer(Fulcrum);   
     }
     if ("Rhythm Preserving Retrograde".equals(transformer_string)) {
         transformer3 = new ReverseRhythmPreservingPatternTransformer();   
     }
     if ("Transpose".equals(transformer_string)) {
         transformer3 = new IntervalPatternTransformer(transposeInterval);    
     }     
 }
 public static File getQueueDir() {
     return outputQueueDir;
 }
public static void set_out_to_midi_yoke (Boolean out2yoke) {
    out_to_midi_yoke = out2yoke;
}

public static void set_q_mode (Boolean queue_mode) {
    q_mode = queue_mode;
}

public static Boolean get_q_mode() {
    return q_mode;
}

public static void setTempo(int my_tempo) {
    tempo_bpm = my_tempo;    
    }
public static int getTempo() {
    return tempo_bpm;
}

public static void setTranspose(int t_interval) {
    transposeInterval = t_interval;    
    }
public static int getTranspose() {
    return transposeInterval;
}
public static void setFulcrum(byte fulcrum) {
    fulcrum_value = fulcrum;    
}

public static byte getFulcrum() {
    return fulcrum_value;
}

public static void setFilePath(File file) {
    outputFilePath = file;
    if (file != null) outputFileDir = file.getParentFile();
    }

public static File getFilePath() {
    if(outputFilePath != null) return outputFilePath;
    else {
        //System.out.println("file path is null");
        return null;
        }
}

public static File getFileDir() {
    if(outputFileDir!= null) return outputFileDir;
    else {
        //System.out.println("file path is null");
        return null;
        }
}

public static String getJfugueString() {
    return jfugueString;
}

public static void setJfugueString(String fugueString){
    jfugueString = fugueString;
}

}
