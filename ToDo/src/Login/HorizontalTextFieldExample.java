/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class HorizontalTextFieldExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Horizontal TextField Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Create a JTextField
        JTextField textField = new JTextField("Enter text here");
        
        // Set horizontal alignment for the text
        textField.setHorizontalAlignment(JTextField.LEFT); // Options: LEFT, CENTER, RIGHT

        textField.setPreferredSize(new Dimension(200, 30));

        // Add to frame
        frame.add(textField);

        frame.setSize(30, 70); 
        frame.setVisible(true);
    }
}

