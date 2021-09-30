package canonical_tools;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfugue.KeySignature;
import org.jfugue.Measure;
import org.jfugue.Note;
import org.jfugue.PatternTransformer;
import org.jfugue.Tempo;
import org.jfugue.Time;
import org.jfugue.Voice;


public class CleanerPatternTransformer extends PatternTransformer {

	//int limit = 4;
	//int counter;V	//Random sync = new Random(); 
	double parallel;
        static final byte voiceLimit = 15;
	static Voice currentVoice = new Voice(voiceLimit);
        static Boolean pendingTime;
        static Double timeTokenDuration;
        static Tempo pieceTempo = new Tempo(0);
        static int tempoCounter = 0;
	
	public CleanerPatternTransformer()	{
		super();
	}
	
	//public void measureEvent(Measure measure) {
//	}
	
	public void tempoEvent(Tempo tempo) {
            //System.out.println("found tempo " + tempo.getMusicString());
	   if (pieceTempo.getTempo() == 0 & tempoCounter == 0 ) {
               pieceTempo = tempo;
               //pieceTempo.setTempo((int)tempo.getTempo()/2);
               System.out.println("adding tempo " + pieceTempo.getMusicString());
               getReturnPattern().addElement(pieceTempo);
           }
               System.out.println("tempoCounter " + tempoCounter);
                tempoCounter++;  
	}
        
//    public void timeEvent(Time time)
//    {
//        //System.out.println("cleaning time token " + time.getMusicString());
//        if (time.getTime()> 0) {
//            System.out.println("timetoken "+  time.getTime());
//            System.out.println("getTempo " + pieceTempo.getTempo());
//            System.out.println("pre fraction " + pieceTempo.getTempo()*time.getTime()/60000);
//            pendingTime = true;
//            double myfraction = (double)time.getTime()*(double)pieceTempo.getTempo()/(double)(60000);
//            System.out.println("myfraction " + myfraction);
//            double restDuration = myfraction - myfraction % .015625;
//            System.out.println("my rest duration " + restDuration);
//            String restString = "R" + Note.getStringForDuration(restDuration);
//            System.out.println("adding " + restString);
//            getReturnPattern().add(restString);
//        }
//
//    }
        
        public void voiceEvent(Voice voice)
    {
        if (voice.getMusicString() == null ? currentVoice.getMusicString() != null : !voice.getMusicString().equals(currentVoice.getMusicString())) {
            //System.out.println("new voice: " + voice.getMusicString());
            currentVoice = voice;
            getReturnPattern().addElement(voice);
        }
        else {
            //System.out.println("cleaning voice " + voice.getMusicString());
        }
        }
	
	public void noteEvent(Note note) {
		//String stringy = note.getMusicString();
		//System.out.println(stringy);
		//Matcher test = Pattern.compile("a0d0").matcher(stringy);
		//String stringy = note.getMusicString();
	//	if (test.find()) {
		//note.setDecimalDuration(0);	
	//	getReturnPattern().addElement(note);
	//	System.out.println("Caught" + note.getMusicString());
	//	}
		//else 

	    clean_this(note, note.getDecimalDuration());
	}
	public void sequentialNoteEvent(Note note)
	{
		clean_this(note, note.getDecimalDuration());
	}
	
	public void parallelNoteEvent(Note note)
	{
		
		note.setDecimalDuration(parallel);
		getReturnPattern().addElement(note);
	}
	
	public void clean_this(Note note, Double duration) {
//		int durationInt = duration.intValue();
//                double durationDec = durationInt - duration;
//                if (durationDec < 0) durationDec = durationDec*(-1);
//                System.out.println(durationDec);
//		if (durationDec < 0.0625) durationDec = 0.03125;
//		if (durationDec < 0.125 & durationDec >= 0.0625) durationDec = 0.0625;
//		if (durationDec < 0.1875 & durationDec >= 0.125) durationDec = 0.125;
//		if (durationDec < 0.25 & durationDec >= 0.1875) durationDec = 0.1875;
//		if (durationDec < 0.3125 & durationDec >= 0.25) durationDec = 0.25;
//		if (durationDec < 0.375 & durationDec >= 0.3125) durationDec = 0.3125;
//		if (durationDec < 0.4375 & durationDec >= 0.375) durationDec = 0.375;
//		if (durationDec < 0.5 & durationDec >= 0.4375) durationDec = 0.4375;
//		if (durationDec < 0.5625 & durationDec >= 0.5) durationDec = 0.5;
//		if (durationDec < 0.625 & durationDec >= 0.5625) durationDec = 0.5625;
//		if (durationDec < 0.6875 & durationDec >= 0.625) durationDec = 0.625;
//		if (durationDec < 0.75 & durationDec >= 0.6875) durationDec = 0.6875;
//		if (durationDec < 0.8125 & durationDec >= 0.75) durationDec = 0.75;
//		if (durationDec < 0.875 & durationDec >= 0.8125) durationDec = 0.8125;
//		if (durationDec < 0.9375 & durationDec >= 0.875) durationDec = 0.875;
//		if (durationDec < 1 & durationDec >= 0.9375) durationDec = 0.9375;
//		if (durationDec < 1.0625 & durationDec >= 1) durationDec = 1;
		
//		if (duration < 1.125 & duration >= 1.0625) duration = 1.0625;
//		if (duration < 1.1875 & duration >= 1.125) duration = 1.125;
//		if (duration < 1.25 & duration >= 1.1875) duration = 1.1875;
//		if (duration < 1.3125 & duration >= 1.25) duration = 1.25;
//		if (duration < 1.375 & duration >= 1.3125) duration = 1.3125;
//		if (duration < 1.4375 & duration >= 1.375) duration = 1.375;
//		if (duration < 1.5 & duration >= 1.4375) duration = 1.4375;
//		if (duration < 1.5625 & duration >= 1.5) duration = 1.5;
//		if (duration < 1.625 & duration >= 1.5625) duration = 1.5625;
//		if (duration < 1.6875 & duration >= 1.625) duration = 1.625;
//		if (duration < 1.75 & duration >= 1.6875) duration = 1.6875;
//		if (duration < 1.8125 & duration >= 1.75) duration = 1.75;
//		if (duration < 1.875 & duration >= 1.8125) duration = 1.8125;
//		if (duration < 1.9375 & duration >= 1.875) duration = 1.875;
//		if (duration < 2 & duration >= 1.9375) duration = 1.9375;
                if (duration < .015625) duration = .015625;
                else duration = duration - duration % .015625; //.03125
		note.setDecimalDuration(duration);
                if (note.getAttackVelocity() == 0 & note.getDecayVelocity() == 0 ) {
                    String restString = "R" + note.getStringForDuration(duration);
                    getReturnPattern().add(restString);
                }
                else getReturnPattern().addElement(note);
		parallel = duration;
	}
	
	

}
