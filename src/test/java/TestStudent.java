import api.IAdmin;
import api.core.impl.Admin;
import api.IStudent;
import api.core.impl.Student;
import api.core.impl.DataManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import static org.junit.Assert.*;

public class TestStudent {

    private IStudent student;
    private IAdmin admin;

    @Before
    public void setup() {
        this.student = new Student();
        this.admin = new Admin();
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testValidRegister() {
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.student.registerForClass("Student", "Class", 2017);
        assertTrue(this.student.isRegisteredFor("Student", "Class", 2017));
    }

    @Test
    public void testInvalidRegister() {
        this.admin.createClass("Class1", 2017, "Instructor", 15);
        this.student.registerForClass("Student", "Class", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
    }

    @Test
    public void testValidDrop(){
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.student.registerForClass("Student", "Class", 2017);
        this.student.dropClass("Student","Class",2017);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
    }

    @Test
    public void testInvalidDrop(){
            this.admin.createClass("Class", 2017, "Instructor", 15);
            this.student.registerForClass("Student", "Class", 2017);
            this.student.dropClass("Student2","Class",2017);
            assertFalse(this.student.isRegisteredFor("Student2", "Class", 2017));
    }

    @Test
    public void testPastDrop(){
        this.admin.createClass("Class", 2016, "Instructor", 15);
        this.student.registerForClass("Student", "Class", 2016);
        this.student.dropClass("Student","Class",2016);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2016));
    }

    @Test
    public void testValidHomework(){
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.student.registerForClass("Student", "Class", 2017);
        this.student.submitHomework("Student","Homework","Hello","Class",2017);
        assertTrue(this.student.hasSubmitted("Student","Homework","Class",2017));
    }

    @Test
    public void testPastHomework(){
        this.admin.createClass("Class", 2016, "Instructor", 15);
        this.student.registerForClass("Student", "Class", 2016);
        this.student.submitHomework("Student","Homework","Hello","Class",2016);
        assertFalse(this.student.hasSubmitted("Student","Homework","Class",2016));
    }

    @Test
    public void testUnregHomework(){
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.student.registerForClass("Student2", "Class", 2017);
        this.student.submitHomework("Student","Homework","Hello","Class",2017);
        assertFalse(this.student.hasSubmitted("Student","Homework","Class",2017));
    }

    @Test
    public void testWrongClassHomework(){
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.student.registerForClass("Student", "Class", 2017);
        this.student.submitHomework("Student","Homework","Hello","Class2",2017);
        assertFalse(this.student.hasSubmitted("Student","Homework","Class2",2017));
    }








}
