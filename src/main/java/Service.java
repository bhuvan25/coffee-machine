import controller.MachineController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class Service {

    private static MachineController machineController;
    static String[] beverageOptions = {" ","hot_tea","hot_coffee","black_tea", "green_tea"};

    public static void main(String[] args) throws IOException, InterruptedException {
        initializeMachine();
        while (true) {
            mainView();
        }

    }

    private static void initializeMachine() {
        machineController = new MachineController();
    }

    private static void mainView() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String ingredientName;
        int option, quantity;
        int number = 4;
        System.out.println("Press 1 to serve beverage");
        System.out.println("Press 2 to fill ingredient");
        System.out.println("Press 3 to turn off machine");

        option = Integer.parseInt(reader.readLine());
        if (option == 1) {

            List<String> beverageInput = new ArrayList<>();
            System.out.println("Please enter option\n1. Hot Tea \n 2.Hot Coffee \n 3.Black Tea \n 4. Green Tea\n 0. start process");
            for (int i = 0; i < number; i++) {

                int beverageOption = Integer.parseInt(reader.readLine());
                if (beverageOption==0)
                    break;
                beverageInput.add(beverageOptions[beverageOption]);

            }
            int inputSize = beverageInput.size();
            ExecutorService executorService = Executors.newFixedThreadPool(inputSize);


            for (int i = 0; i < inputSize; i++) {
                MyMonitorThread myMonitorThread = new MyMonitorThread(machineController, beverageInput, i);
                executorService.execute(myMonitorThread);

            }

            executorService.shutdown();
            executorService.awaitTermination(60, TimeUnit.SECONDS);

        } else if (option == 2) {
            System.out.println("Provide ingredient to fill:");
            ingredientName = reader.readLine();
            System.out.println("Provide quantity");
            quantity = Integer.parseInt(reader.readLine());
            machineController.fillIngredient(ingredientName, quantity);
        } else if (option == 3) {
            exit(0);
        } else {
            System.out.println("Provide a valid input!");
        }

    }

}
