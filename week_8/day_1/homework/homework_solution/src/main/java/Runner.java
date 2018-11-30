import db.DBCustomer;
import db.DBOrder;
import models.Customer;
import models.Order;

import java.util.List;

public class Runner {

    public static void main(String[] args) {


        Customer homer = new Customer("HS123","Homer Simpson", "homer@evergreen.com", "555-1234" );
        Customer marge = new Customer("MS123", "Marge Simpson", "marge@kwikemart.com", "555-1234");
        DBCustomer.save(homer);
        DBCustomer.save(marge);


        marge.setCustomerCode("MS464");
        DBCustomer.update(marge);

        List<Customer> customers = DBCustomer.getAll();

        Customer foundCustomer = DBCustomer.find(marge.getId());


        Order order1 = new Order("Donuts", "A Case of jelly glazed");
        DBOrder.save(order1);

        Order order2 = new Order("Hair Dye", "Blue Hair Dye");
        DBOrder.save(order2);

        List<Order> orders= DBOrder.getAll();

        Order foundOrder = DBOrder.find(order1.getId());

    }
}
