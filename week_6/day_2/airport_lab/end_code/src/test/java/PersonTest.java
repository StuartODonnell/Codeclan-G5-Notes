import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    Person person;

    @Before
    public void setup() {
        person = new Person("Colin");
    }

    @Test
    public void personHasName(){
        assertEquals(person.getName(), "Colin");
    }

}
