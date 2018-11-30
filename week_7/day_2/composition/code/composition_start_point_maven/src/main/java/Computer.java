package com.codeclan.composition_start_point_maven;

public class Computer {
    private int ram;
    private int hddSize;

    private device_management.Monitor monitor;

    public Computer(int ram, int hddSize, Monitor monitor) {
        this.ram = ram;
        this.hddSize = hddSize;
        this.monitor = monitor;
    }

    public int getRam() {
        return this.ram;
    }

    public int getHddSize() {
        return this.hddSize;
    }

    public Monitor getMonitor() {
        return this.monitor;
    }

    public String outputData(String data) {
        return this.monitor.outputData(data);
    }
}
