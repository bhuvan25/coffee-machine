import controller.MachineController;
import lombok.SneakyThrows;

import java.util.List;

public class MyMonitorThread implements Runnable {

    private MachineController machineController;
    private List<String> beverageName;
    private int index;

    public MyMonitorThread(MachineController machineController, List<String> l, int index) {
        this.machineController = machineController;
        this.beverageName = l;
        this.index = index;
    }

    @SneakyThrows
    @Override
    public void run() {
        machineController.serveBeverage(beverageName.get(index));

    }
}
