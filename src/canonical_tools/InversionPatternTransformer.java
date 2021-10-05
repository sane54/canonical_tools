package canonical_tools;



import org.jfugue.Note;
import org.jfugue.PatternTransformer;



public class InversionPatternTransformer extends PatternTransformer {
	byte previousNote;
	byte archiveNote;
	int dif;
	public InversionPatternTransformer()	{
		super();
	}
	
	public void noteEvent(Note note) {
                if (!(note.getAttackVelocity() == 0 & note.getDecayVelocity() == 0))
		invert_this(note.getValue(), note.getDecimalDuration());
	}

	public void sequentialNoteEvent(Note note)
	{
		invert_this(note.getValue(), note.getDecimalDuration());
	}
	public void parallelNoteEvent(Note note)
	{
		invert_this(note.getValue(), note.getDecimalDuration());
	}
	public void invert_this(byte pitch, double duration) {
	int returnInt;
	Note returnNote = new Note();
	if (archiveNote == 0) archiveNote = pitch;
	if (previousNote == 0) previousNote = pitch;
	//returnNote.setValue(previousNote);
	//System.out.println("previousNote Before  " + returnNote.getMusicString());
	dif = (archiveNote - pitch);
	//System.out.println(dif);
	returnInt = previousNote + dif;
	//if (returnInt < 0 ) returnInt = returnInt + 12;
	returnNote.setValue((byte)returnInt);
	returnNote.setDecimalDuration(duration);
	getReturnPattern().add (returnNote.getMusicString());
	archiveNote = pitch;
	previousNote = (byte)returnInt;
	//returnNote.setValue(previousNote);
	//System.out.println("previousNote After  " + returnNote.getMusicString());
	}

}


