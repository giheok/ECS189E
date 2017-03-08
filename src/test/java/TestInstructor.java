import api.IAdmin;
import api.core.impl.Admin;
import api.IStudent;
import api.core.impl.Student;
import api.IInstructor;
import api.core.impl.Instructor;
import api.core.impl.DataManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import static org.junit.Assert.*;

public class TestInstructor {
    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testAddValidHomework(){
        this.admin.createClass("Class",2017,"Instructor",15);
        assertTrue(this.admin.classExists("Class",2017));
        this.instructor.addHomework("Instructor","Class",2017,"Homework","Description");
        assertTrue(this.instructor.homeworkExists("Class",2017,"Homework"));
    }

    @Test
    public void testAddInvalidHomework(){
        this.admin.createClass("Class",2017,"Instructor2",15);
        assertTrue(this.admin.classExists("Class",2017));
        this.instructor.addHomework("Instructor","Class",2017,"Homework","Description");
        assertFalse(this.instructor.homeworkExists("Class",2017,"Homework"));
    }

    @Test
    public void testAddValidGrade(){
        this.admin.createClass("Class",2017,"Instructor",15);
        assertTrue(this.admin.classExists("Class",2017));
        this.instructor.addHomework("Instructor","Class",2017,"Homework","Description");
        assertTrue(this.instructor.homeworkExists("Class",2017,"Homework"));
        this.student.submitHomework("Student","Homework","Answer","Class",2017);
        assertTrue(this.student.hasSubmitted("Student","Homework","Class",2017));
        this.instructor.assignGrade("Instructor","Class",2017,"Homework","Student",100);
        assertNotNull(this.instructor.getGrade("Class",2017,"Homework","Student"));

    }

    @Test
    public void testNoSubmitInvalidGrade(){
        this.admin.createClass("Class",2017,"Instructor",15);
        assertTrue(this.admin.classExists("Class",2017));
        this.instructor.addHomework("Instructor","Class",2017,"Homework","Description");
        assertTrue(this.instructor.homeworkExists("Class",2017,"Homework"));
        //this.student.submitHomework("Student","Homework","Answer","Class",2017);
        //assertTrue(this.student.hasSubmitted("Student","Homework","Class",2017));
        this.instructor.assignGrade("Instructor","Class",2017,"Homework","Student",100);
        assertNull(this.instructor.getGrade("Class",2017,"Homework","Student"));
    }

    @Test
    public void testNoHomeworkInvalidGrade(){
        this.admin.createClass("Class",2017,"Instructor",15);
        assertTrue(this.admin.classExists("Class",2017));
        //this.instructor.addHomework("Instructor","Class",2017,"Homework","Description");
        //assertTrue(this.instructor.homeworkExists("Class",2017,"Homework"));
        this.student.submitHomework("Student","Homework","Answer","Class",2017);
        assertTrue(this.student.hasSubmitted("Student","Homework","Class",2017));
        this.instructor.assignGrade("Instructor","Class",2017,"Homework","Student",100);
        assertNull(this.instructor.getGrade("Class",2017,"Homework","Student"));
    }


    @Test
    public void testNoInstructorInvalidGrade(){
        this.admin.createClass("Class",2017,"Instructor2",15);
        assertTrue(this.admin.classExists("Class",2017));
        this.instructor.addHomework("Instructor2","Class",2017,"Homework","Description");
        assertTrue(this.instructor.homeworkExists("Class",2017,"Homework"));
        this.student.submitHomework("Student","Homework","Answer","Class",2017);
        assertTrue(this.student.hasSubmitted("Student","Homework","Class",2017));
        this.instructor.assignGrade("Instructor","Class",2017,"Homework","Student",100);
        assertNull(this.instructor.getGrade("Class",2017,"Homework","Student"));
    }

}
