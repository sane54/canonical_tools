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
package canonical_tools;

import org.jfugue.*;
import java.util.LinkedList;

public class NoteValueListener implements ParserListener{
    LinkedList<Byte> noteValues = new LinkedList();
    MusicStringParser parser = new MusicStringParser();//uses the jfugue parser
    
    public LinkedList<Byte> listen(Pattern p) {
        parser.addParserListener(this);
        //DEBUG
        //System.out.println("about to parse " + p.getMusicString());
        try {
            parser.parse(p);
        } catch (JFugueException e)
        {
            e.printStackTrace();
        }
        return noteValues;
    }

    public void voiceEvent(Voice voice){
        
	}

 
    public void tempoEvent(Tempo tempo) {
		
	}

    public void instrumentEvent(Instrument instrument){
	}

    
    public void layerEvent(Layer layer){
		
	}

    
    public void measureEvent(Measure measure){
	}
    
    
    public void timeEvent(Time time){
	}
    
    
    public void keySignatureEvent(KeySignature keySig){
	}
    
    
    public void controllerEvent(Controller controller){
	}
    
    
    public void channelPressureEvent(ChannelPressure channelPressure){
	}
    
    
    public void polyphonicPressureEvent(PolyphonicPressure polyphonicPressure){
	}
    
    
    public void pitchBendEvent(PitchBend pitchBend){
	}
    
    @Override
    public void noteEvent(Note note){
        //decdurations.add(note.getDecimalDuration());
        //System.out.println("processing note: " + note.getMusicString());
        if(!(note.getAttackVelocity() == 0 & note.getDecayVelocity() == 0))
        noteValues.addFirst(note.getValue());
        //System.out.print("duration list: ");
//        for (double myduration : decdurations) {
//            System.out.print(myduration + " ");
//        }
//        System.out.println();
    }

    
    @Override
    public void sequentialNoteEvent(Note note){
        //decdurations.addFirst(note.getDecimalDuration());
	}

    
    @Override
    public void parallelNoteEvent(Note note){
        //decdurations.addFirst(note.getDecimalDuration());
	}
}
