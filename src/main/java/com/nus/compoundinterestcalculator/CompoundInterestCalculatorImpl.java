package com.nus.compoundinterestcalculator;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import groovy.util.logging.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class CompoundInterestCalculatorImpl implements CompoundInterestCalculator {

    @Override
    public void showDialog(Project project) {
        // Create the UI components for the calculator dialog.
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);

        JLabel principalLabel = new JLabel("Principal:");
        JTextField principalField = new JTextField(10);
        JLabel rateLabel = new JLabel("Annual Interest Rate:");
        JTextField rateField = new JTextField(10);
        JLabel yearsLabel = new JLabel("Number of Years:");
        JTextField yearsField = new JTextField(10);
        JLabel compoundingLabel = new JLabel("Compounding Period:");
        ComboBox<String> compoundingBox = new ComboBox<>(new String[]{"Monthly", "Quarterly", "Semi-Annually", "Annually"});
        JLabel resultLabel = new JLabel("Result:");
        JTextField resultField = new JTextField(10);
        resultField.setEditable(false);

        // Add the UI components to the panel.
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(principalLabel, gbc);
        gbc.gridx = 1;
        panel.add(principalField, gbc);

        // Create a border around the entire form
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(rateLabel, gbc);
        gbc.gridx = 1;
        panel.add(rateField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(yearsLabel, gbc);
        gbc.gridx = 1;
        panel.add(yearsField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(compoundingLabel, gbc);
        gbc.gridx = 1;
        panel.add(compoundingBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(resultLabel, gbc);
        gbc.gridx = 1;
        panel.add(resultField, gbc);

        // Create the "Calculate" and "Cancel" buttons.
        JButton calculateButton = new JButton("Calculate");
        JButton cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(calculateButton);
        buttonPanel.add(cancelButton);

        // Add the button panel to the main panel.
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Create the calculator dialog.
        JDialog dialog = new JDialog();
        dialog.setTitle("Compound Interest Calculator");
        dialog.setModal(true);
        dialog.getContentPane().add(panel);

        // Attach a listener to the "Calculate" button.
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Parse the input values from the text fields.
                    double principal = Double.parseDouble(principalField.getText());
                    double rate = Double.parseDouble(rateField.getText());
                    int years = Integer.parseInt(yearsField.getText());
                    int compoundingPeriod = compoundingBox.getSelectedIndex() + 1;

                    // Calculate the compound interest and display the result.
                    double result = calculateCompoundInterest(principal, rate, years, compoundingPeriod);
                    resultField.setText(String.format("%.2f", result));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Attach a listener to the "Cancel" button.
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        // Show the calculator dialog.
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true);
    }


    private double calculateCompoundInterest(double principal, double rate, int years, int compoundingPeriod) {
        // Calculate the compound interest using the formula:
        // A = P * (1 + r/n)^(n*t)
        // where A is the amount, P is the principal, r is the annual interest rate, n is the number of times interest is compounded per year, and t is the number of years.

        // Convert the rate to a decimal
        rate = rate / 100.0;

        // Determine the number of compounding periods based on the selected index
        int n;
        switch (compoundingPeriod) {
            case 1: // Monthly
                n = 12;
                break;
            case 2: // Quarterly
                n = 4;
                break;
            case 3: // Semi-Annually
                n = 2;
                break;
            case 4: // Annually
            default:
                n = 1;
                break;
        }

        double t = years;
        double A = principal * Math.pow(1 + rate / n, n * t);
        return A;
    }
}
