import db.DBCourse;
import db.DBHelper;
import db.DBInstructor;
import models.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Runner {

    public static void main(String[] args) {

        Course course1 = new Course("Medical Proficiency", CourseLevel.BMA);
        DBHelper.save(course1);

        Student student1 = new Student("Jack", "Jarvis", 67, 1234, course1);
        DBHelper.save(student1);
        Student student2 = new Student("Victor", "McDade", 65, 2345, course1);
        DBHelper.save(student2);
        Student student3 = new Student("Tam", "Mullen", 69, 3456, course1);
        DBHelper.save(student3);
        Student student4 = new Student("Winston", "Ingrim", 62, 4567, course1);
        DBHelper.save(student4);

        Instructor instructor1 = new Instructor("Kenneth Jordan");
        DBHelper.save(instructor1);

        Instructor instructor2 = new Instructor("Isa Drennan");
        DBHelper.save(instructor2);

        Calendar cal = Calendar.getInstance();

        cal.set(2018, 06, 21);
        Lesson lesson1 = new Lesson("Burns", "101",  cal, course1, instructor1);
        DBHelper.save(lesson1);

        cal.set(2018, 06, 28);
        Lesson lesson2 = new Lesson("Bandages", "104",  cal, course1, instructor2);
        DBHelper.save(lesson2);

        Course foundCourse = DBHelper.find(Course.class, course1.getId());
        Instructor foundInstructor = DBHelper.find(Instructor.class, instructor1.getId());

        List<Student> courseStudents = DBCourse.getAllCourseStudents(course1);

        List<Lesson> courseLessons = DBCourse.getAllCourseLessons(course1);

        List<Lesson> instructorLessons = DBInstructor.getAllInstructorLessons(instructor1);


    }
}
