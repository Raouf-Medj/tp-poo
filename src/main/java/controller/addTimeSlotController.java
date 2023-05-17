package controller;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class addTimeSlotController {
    private Spinner<Integer> createSpinnerHours() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(valueFactory);
        return spinner;
    }

    private Spinner<Integer> createSpinnerMinutes() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0,1);
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(valueFactory);
        return spinner;
    }
}
