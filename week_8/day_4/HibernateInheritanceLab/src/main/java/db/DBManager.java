package db;

import models.Administrator;
import models.Department;
import models.Manager;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.Console;
import java.util.List;

public class DBManager {
    private static Session session;


    public static List<Administrator> getAdministratorsForManager(Manager manager) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Administrator> results = null;
        try {
            Criteria cr = session.createCriteria(Administrator.class);
            cr.add(Restrictions.eq("manager", manager));
            results =  cr.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static Department getManagersDepartment(Manager manager){
        session = HibernateUtil.getSessionFactory().openSession();
        Department result = null;

        try {
            Criteria cr = session.createCriteria(Department.class);
            cr.add(Restrictions.eq("manager", manager));
            result = (Department)cr.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return result;
    }

}
