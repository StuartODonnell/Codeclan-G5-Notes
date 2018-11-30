import java.util.*;

public class Network {
    private String name;
    private ArrayList<Iconnect> devices;


    public Network(String name){
        this.devices = new ArrayList<Iconnect>();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int deviceCount(){
        return devices.size();
    }

    public void connect(Iconnect device){
        devices.add(device);
    }

    public void disconnectAll(){
        devices.clear();
    }
}
