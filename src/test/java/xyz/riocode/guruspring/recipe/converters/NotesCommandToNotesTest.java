package xyz.riocode.guruspring.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import xyz.riocode.guruspring.recipe.commands.NotesCommand;
import xyz.riocode.guruspring.recipe.domain.Notes;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {

    private static final String ID = "1";
    private static final String RECIPE_NOTES = "Recipe_Notes";

    private NotesCommandToNotes notesCommandToNotes;

    @Before
    public void setUp() throws Exception {
        notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    public void testConvert() {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        Notes notes = notesCommandToNotes.convert(notesCommand);

        assertNotNull(notes);
        assertEquals(ID, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());

    }

    @Test
    public void testConvertNullObject() {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    public void testConvertEmptyObject() {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }
}