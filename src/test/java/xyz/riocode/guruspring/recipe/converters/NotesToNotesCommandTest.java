package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.NotesCommand;
import xyz.riocode.guruspring.recipe.domain.Notes;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {

    private static final String ID = "1";
    private static final String RECIPE_NOTES = "Recipe_Notes";

    private NotesToNotesCommand notesToNotesCommand;

    @Before
    public void setUp() throws Exception {
        notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    public void testConvert() {
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        NotesCommand notesCommand = notesToNotesCommand.convert(notes);

        assertNotNull(notesCommand);
        assertEquals(ID, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }

    @Test
    public void testConvertEmptyObject() {
        assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    public void testConvertNullObject() {
        assertNull(notesToNotesCommand.convert(null));
    }
}