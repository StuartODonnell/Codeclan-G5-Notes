package Kaiju;

//This class extends from the Kaiju abstract(super) class so brings with it all of its attributes that have
//been initialized in the Kaijus constructor.

public class Godzilla extends Kaiju {

    public Godzilla(String name, int attackValue) {
        super(name, attackValue);
    }


    //This roar method is an abstract method in the super class which means that it has to be implemented here.
    //How its implemented is up to this class, but it has to return a string as is declared in the super class.
    public String roar() {
        return "ROAAAR";
    }


}
