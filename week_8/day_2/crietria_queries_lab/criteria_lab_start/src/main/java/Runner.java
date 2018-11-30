import db.DBHelper;
import models.Child;

public class Runner {

    public static void main(String[] args) {

        Child liesl = new Child("Liesl", 16, "Soprano");
        Child friedrich = new Child("Friedrich", 14, "Tenor");
        Child louisa = new Child("Louisa", 13, "Contralto");
        Child kurt = new Child("Kurt", 11, "Tenor");
        Child brigitta = new Child("Brigitta", 10, "Soprano");
        Child marta = new Child("Marta", 7, "Mezzo-Soprano");
        Child gretl = new Child("Gretl", 5, "Soprano");

        DBHelper.save(liesl);
        DBHelper.save(friedrich);
        DBHelper.save(louisa);
        DBHelper.save(kurt);
        DBHelper.save(brigitta);
        DBHelper.save(marta);
        DBHelper.save(gretl);

    }
}
