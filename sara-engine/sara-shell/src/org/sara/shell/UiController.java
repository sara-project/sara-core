package org.sara.shell;

import org.sara.interfaces.IUiController;

public class UiController implements IUiController {

    public UiController() {
    }

    @Override
    public void printProgressBar(int currentValue, int maxValue) {
        String progressBar = "";
        double percent = (currentValue * 1.0) / (maxValue * 1.0);

        for(int i = 0; i < 20; i++) {
            if(((int)(20 * percent)) > i)    
               progressBar +="#";
            else
                progressBar +=" ";
        }

        progressBar = "["+progressBar+"] " + ((int) (percent * 100)) + "%";
        progressBar += " - (" + currentValue + "/" + maxValue + ")";
        System.out.print(progressBar+"\r");
    }
}
