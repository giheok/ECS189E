import api.IAdmin;
import api.core.impl.Admin;
import api.core.impl.DataManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;

import static org.junit.Assert.*;

public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testInvalidClass2() {
        this.admin.createClass("Test", -1, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", -1));
    }

    @Test
    public void testChangeCapacity(){
        Integer i, j;
        for (i = 5; i <= 200; i++) {
            this.admin.createClass("Test",2015, "Instructor", i);
            int pastCapacity = this.admin.getClassCapacity("Test", 2015);
            for (j = 0; j < i; j ++) {
                this.admin.changeCapacity("Test", 2015, j);
                int currentCapacity = this.admin.getClassCapacity("Test", 2015);
                try {
                    assertFalse(pastCapacity < currentCapacity);
                }
                catch (Throwable t){
                    collector.addError(t);
                }
            }
        }
    }

    @Test
    public void testValidChangeCapacity(){
        Integer i, j;
        for (i = 5; i <= 200; i++) {
            this.admin.createClass("Test",2015, "Instructor", i);
            int pastCapacity = this.admin.getClassCapacity("Test", 2015);
            for (j = i; j < i + 10; j ++) {
                this.admin.changeCapacity("Test", 2015, j);
                int currentCapacity = this.admin.getClassCapacity("Test", 2015);
                try {
                    assertFalse(pastCapacity < currentCapacity);
                }
                catch (Throwable t){
                    collector.addError(t);
                }
            }
        }
    }

    @Test
    public void testUniqueInstructors() {
        String instructor = "Instructor";
        String[] unique = new String[5];
        Integer i, j, count = 1;

        for (i = 1; i < 5; i++) {
            this.admin.createClass("Test" + i, 2017, instructor, 15);
        }

        for (i = 1; i < 5; i++) {
            unique[i] = this.admin.getClassInstructor("Test" + i, 2017);
        }

        i = 1;

        for (j = 2; j < 5; j++) {
            if (unique[i].equals(unique[j])) {
                count++;
            }
        }

        assertFalse(count > 2);

    }

    @Test
    public void testValidInstructors() {
        String instructor = "Instructor";
        String[] unique = new String[5];
        Integer i, j, count = 1;

        for (i = 1; i < 3; i++) {
            this.admin.createClass("Test" + i, 2017, instructor, 15);
        }

        for (i = 1; i < 3; i++) {
            unique[i] = this.admin.getClassInstructor("Test" + i, 2017);
        }

        i = 1;

        for (j = 2; j < 3; j++) {
            if (unique[i].equals(unique[j])) {
                count++;
            }
        }

        assertTrue(count <= 2);

    }

    @Test
    public void testValidCapacity(){
        this.admin.createClass("Test", 2017, "Instructor", 15);
        int capacity = this.admin.getClassCapacity("Test",2017);
        assertTrue(capacity > 0);

    }

    @Test
    public void testInvalidCapacity(){
        this.admin.createClass("Test", 2017, "Instructor", 0);
        int capacity = this.admin.getClassCapacity("Test",2017);
        assertFalse(capacity > 0);
    }

    @Test
    public void testNegativeCapacity(){
        this.admin.createClass("Test", 2017, "Instructor", -1);
        int capacity = this.admin.getClassCapacity("Test",2017);
        assertFalse(capacity > 0);
    }

    @Test
    public void testNonUniquePair(){
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        assertFalse(this.admin.classExists("Test",2017));
    }

}
