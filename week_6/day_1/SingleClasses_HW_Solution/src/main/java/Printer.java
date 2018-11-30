public class Printer {

    private int sheetsLeft;
    private int tonerVol;

    public Printer(int sheetsLeft, int tonerVol) {
        this.sheetsLeft = sheetsLeft;
        this.tonerVol = tonerVol;
    }

    public int getSheetsLeft() {
        return sheetsLeft;
    }

    public void print(int pages, int copies){
        int noPages = pages * copies;
        if(noPages <= this.sheetsLeft){
            this.sheetsLeft -= noPages;
            this.tonerVol -= noPages;
        }
    }

    public void refill() {
        this.sheetsLeft = 100;
    }

    public int getTonerVol() {
        return tonerVol;
    }
}
