import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BearTest{
    @Test
    public void hasName(){
        Bear bear = new Bear("Baloo", 25, 95.62);
        assertEquals( "Baloo", bear.getName() );
    }

    @Test
    public void hasAge(){
        Bear bear = new Bear("Baloo", 25, 95.62);
        assertEquals( 25, bear.getAge() );
    }

    @Test
    public void hasWeight(){
        Bear bear = new Bear("Baloo", 25, 95.62);
        assertEquals( 95.62, bear.getWeight(), 0.01 );
    }

    @Test
    public void readyToHibernateIfGreaterThan80(){
        Bear bear = new Bear("Baloo", 25, 95.62);
        assertEquals( true, bear.readyToHibernate() );
    }
    @Test
    public void notReadyToHibernateIfLessThan80(){
        Bear thinBear = new Bear("Baloo", 25, 65.44);
        assertEquals( false, thinBear.readyToHibernate() );
    }
}
