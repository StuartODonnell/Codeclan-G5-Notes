package Kaiju;

//This class extends from the Kaiju abstract(super) class so brings with it all of its attributes that have
//been initialized in the Kaijus constructor.

    public class KingKong extends Kaiju {

        //This class also takes in a hairColour which the class its extending from doesn't have.  So we have to
        //declare this and initialise it like we would any other class attribute.  Within the constructor this is
        //done below the super line.
        private String hairColour;

        public KingKong(String name, int attackValue, String hairColour) {
            super(name, attackValue);
            this.hairColour = hairColour;
        }

        public String getHairColour() {
            return hairColour;
        }

        //This roar method is an abstract method in the super class which means that it has to be implemented here.
        //How its implemented is up to this class, but it has to return a string as is declared in the super class.
        public String roar() {
            return "OOOHH OOOH AHH";
        }
    }
