package canonical_tools;

/*
 * JFugue - API for Music Programming
 * Copyright (C) 2003-2008  David Koelle
 *
 * http://www.jfugue.org 
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *  
 */




import java.util.LinkedList;
import org.jfugue.*;

/**
 * Reverses a given pattern.
 *
 *@author David Koelle
 *@version 2.0
 */
public class ReverseRhythmPreservingPatternTransformer extends PatternTransformer
{
    static LinkedList<Byte> noteValues = new LinkedList();
    static Tempo pieceTempo;
    
    public ReverseRhythmPreservingPatternTransformer()
    {
        super();
    }
    
    @Override
    public Pattern transform(Pattern p)
    {
        //System.out.println("listening to music string: " + p.getMusicString());
        NoteValueListener note_listener = new NoteValueListener();
        noteValues = note_listener.listen(p);
        setReturnPattern(new Pattern());
        MusicStringParser parser = new MusicStringParser();
        parser.addParserListener(this);
        try {
            parser.parse(p);
        } catch (JFugueException e)
        {
            e.printStackTrace();
        }
        noteValues.clear();
        Pattern FinalPattern = new Pattern(pieceTempo.getMusicString() + " " + getReturnPattern().getMusicString());
        setReturnPattern(FinalPattern);
        return getReturnPattern();
    }
    
    /**
     * ReversePatternTransformer does not require that the user specify any variables.
     * @return 
     */
    public String getParameters()
    {
        return "";
    }

    public String getDescription()
    {
        return "Reverses the given pattern";
    }


    @Override
    public void tempoEvent(Tempo tempo)
    {
        //insert(tempo.getMusicString(), " ");
        pieceTempo = tempo;
    }



    @Override
    public void noteEvent(Note note)
    {
        if (!(note.getAttackVelocity() == 0 & note.getDecayVelocity() == 0)){
            note.setValue(noteValues.pop());
        }
        getReturnPattern().addElement(note);
    }

    @Override
    public void sequentialNoteEvent(Note note)
    {
        note.setValue(noteValues.pop());
        getReturnPattern().addElement(note);
    }

    @Override
    public void parallelNoteEvent(Note note)
    {
        note.setValue(noteValues.pop());
        getReturnPattern().addElement(note);
    }
}