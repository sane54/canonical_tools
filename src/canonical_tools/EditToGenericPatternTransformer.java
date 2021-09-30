package canonical_tools;



import org.jfugue.Instrument;
import org.jfugue.PatternTransformer;
import org.jfugue.Tempo;
import org.jfugue.Time;


public class EditToGenericPatternTransformer extends PatternTransformer
{
	public EditToGenericPatternTransformer()	{
		super();
	}
	public void instrumentEvent(Instrument instrument) {
		System.out.println("removed instrument");
	}
	public void tempoEvent(Tempo tempo) {
		System.out.println("removed tempo");
	}
	//public void timeEvent(Time time) {}
}
