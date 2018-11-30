import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class People {
    private ArrayList<String> names;

    public People() {
        this.names = new ArrayList<>();
    }

    public People(String[] names) {
        this.names = new ArrayList<>(Arrays.asList(names));
    }

    public ArrayList<String> getNames() {
        return new ArrayList<>(this.names);
    }

    private boolean isEmpty() {
        return (this.names.size() == 0);
    }

    public String getRandomName() {
        if (this.isEmpty()) {
            return null;
        }

        Collections.shuffle(this.names);
        String randomName = this.names.get(0);
        return randomName;
    }

    public ArrayList<String> getRandomNames(int size) {
        if (size > this.names.size()) {
            return new ArrayList<>(this.names);
        }

        if (this.isEmpty()) {
            return null;
        }

        Collections.shuffle(this.names);
        ArrayList<String> randomNames = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            randomNames.add(this.names.get(i));
        }
        return randomNames;
    }
}
