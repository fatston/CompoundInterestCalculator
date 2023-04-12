package com.nus.compoundinterestcalculator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class CompoundInterestCalculatorAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Get the current project from the event.
        Project project = event.getProject();

        // Create an instance of the CompoundInterestCalculator implementation.
        CompoundInterestCalculator calculator = new CompoundInterestCalculatorImpl();

        // Display the calculator dialog.
        calculator.showDialog(project);
    }
}
