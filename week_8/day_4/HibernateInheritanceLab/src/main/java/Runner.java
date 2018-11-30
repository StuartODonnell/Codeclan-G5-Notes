import db.DBHelper;
import db.DBManager;
import models.Administrator;
import models.Department;
import models.Manager;

import java.util.List;

public class Runner {

    public static void main(String[] args) {


        Manager manager1 = new Manager("Randy Lahey", 123459, 30000, 150000);
        Department research = new Department("Research",manager1);
        DBHelper.save(manager1);
        DBHelper.save(research);
        Administrator admin1 = new Administrator("Ricky Bobby", 123456, 30000, manager1);
        DBHelper.save(admin1);
        Administrator admin2 = new Administrator("Bubbles", 123457, 20000, manager1);
        DBHelper.save(admin2);

        List<Administrator> manager1Employees = DBManager.getAdministratorsForManager(manager1);

        Department managersDepartment = DBManager.getManagersDepartment(manager1);
    }
}
