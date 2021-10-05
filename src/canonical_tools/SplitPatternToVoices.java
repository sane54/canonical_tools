package canonical_tools;





import java.util.ArrayList;
import org.jfugue.ChannelPressure;
import org.jfugue.Controller;
import org.jfugue.Instrument;
import org.jfugue.JFugueException;
import org.jfugue.KeySignature;
import org.jfugue.Layer;
import org.jfugue.Measure;
import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Pattern;
import org.jfugue.PatternTransformer;
import org.jfugue.PitchBend;
import org.jfugue.PolyphonicPressure;
import org.jfugue.Tempo;
import org.jfugue.Time;
import org.jfugue.Voice;



public class SplitPatternToVoices extends PatternTransformer {
    
    static Pattern voice_pattern = new Pattern();
    static PatternTransformer trans;
    static ArrayList<Pattern> patterns;
    static Boolean firstTempo = true;
    static Integer tempoCounter = 0;
    static Tempo pieceTempo = new Tempo(0);
    static EditToGenericPatternTransformer ee = new EditToGenericPatternTransformer();
    
    public SplitPatternToVoices()	{
		super();
                patterns = new ArrayList();
	}
    
        public Pattern transform(Pattern p, PatternTransformer t)
    {
        trans = t;
        MusicStringParser parser = new MusicStringParser();
        p.add("V15");
        System.out.println("pattern at beginning of split");
        System.out.println(p.getMusicString());
        parser.addParserListener(this);
        try {
            parser.parse(p);
        } catch (JFugueException e)
        {
            e.printStackTrace();
        }
        Pattern masterPattern = new Pattern();
        masterPattern = ee.transform(masterPattern);
        //if (pieceTempo.getTempo() == 0) pieceTempo.setTempo(120);
        masterPattern.addElement(pieceTempo);
        for (int i = 0; i < patterns.size(); i++) {
            masterPattern.add("V" + i);
            //masterPattern.addElement(pieceTempo);
            masterPattern.add(patterns.get(i));
        }
        patterns.clear();
        return masterPattern;
    }
    
    
    @Override
    public void voiceEvent(Voice voice){
        if (voice.getVoice() > 0 ) {
            System.out.println("voice = " + (voice.getVoice()));
            System.out.println("about to add " + voice_pattern.getMusicString() + " to ArrayList" );
            Pattern temp_array_pattern = new Pattern();
            System.out.println("created temp_array_pattern");
            temp_array_pattern = ee.transform(temp_array_pattern);
            //temp_array_pattern.addElement(pieceTempo);
            temp_array_pattern.setMusicString(voice_pattern.getMusicString());
            System.out.println("before: " + temp_array_pattern.getMusicString());
            temp_array_pattern = trans.transform(temp_array_pattern);
            temp_array_pattern = ee.transform(temp_array_pattern);
            System.out.println("after: " + temp_array_pattern.getMusicString());
            patterns.add(temp_array_pattern);
//            System.out.println("patterns thus far");
//            for (Pattern mypattern : patterns) {
//                System.out.println(mypattern.getMusicString());
//            }
//            System.out.println("***************************************************");
            voice_pattern.setMusicString("");
        }
    }
 
    @Override
    public void tempoEvent(Tempo tempo) {
        if (tempoCounter == 0) {
            //voice_pattern.addElement(tempo);
            pieceTempo = tempo;
        }
        firstTempo = false;
        tempoCounter++;
    }

    @Override
    public void instrumentEvent(Instrument instrument){
        voice_pattern.addElement(instrument);
	}

    
    @Override
    public void layerEvent(Layer layer){
	voice_pattern.addElement(layer);
	}

    @Override
    public void measureEvent(Measure measure){
        voice_pattern.addElement(measure);
	}
    
    
    @Override
    public void timeEvent(Time time){
        voice_pattern.addElement(time);
	}
    
    
    @Override
    public void keySignatureEvent(KeySignature keySig){
        voice_pattern.addElement(keySig);
	}
    
    
    @Override
    public void controllerEvent(Controller controller){
        voice_pattern.addElement(controller);
	}
    
    
    @Override
    public void channelPressureEvent(ChannelPressure channelPressure){
        voice_pattern.addElement(channelPressure);
	}
    
    
    @Override
    public void polyphonicPressureEvent(PolyphonicPressure polyphonicPressure){
        voice_pattern.addElement(polyphonicPressure);
	}
    
    
    @Override
    public void pitchBendEvent(PitchBend pitchBend){
        voice_pattern.addElement(pitchBend);
	}
    
   
    @Override
    public void sequentialNoteEvent(Note note){
        voice_pattern.addElement(note);
	}

    
    @Override
    public void parallelNoteEvent(Note note){
        voice_pattern.addElement(note);
	}

    @Override
    public void noteEvent(Note note)
    {
        voice_pattern.addElement(note);
    }
 }

