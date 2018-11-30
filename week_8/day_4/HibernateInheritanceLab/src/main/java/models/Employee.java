package models;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee {

    private String name;
    private int nINumber;
    private int salary;
    private int id;

    public Employee(){

    }

    public Employee(String name, int nINumber, int salary){
        this.name = name;
        this.nINumber = nINumber;
        this.salary = salary;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="nINumber")
    public int getnINumber() {
        return nINumber;
    }

    public void setnINumber(int nINumber) {
        this.nINumber = nINumber;
    }

    @Column(name="salary")
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
