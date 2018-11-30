package db;

import models.Course;
import models.Lesson;
import models.Student;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.rmi.CORBA.Stub;
import java.util.List;

public class DBCourse {

    private static Session session;

    public static List<Student> getAllCourseStudents(Course course){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Student> results = null;

        try {
            Criteria cr = session.createCriteria(Student.class);
            cr.add(Restrictions.eq("course", course));
            results = cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return results;
    }

    public static List<Lesson> getAllCourseLessons(Course course){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Lesson> results = null;

        try {
            Criteria cr = session.createCriteria(Lesson.class);
            cr.add(Restrictions.eq("course", course));
            results = cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return results;
    }
}
