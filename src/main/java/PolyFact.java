/*
 * Copyright 2010 Martynas Mickevičius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lt.dvim.polyfact;

import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import javax.swing.*;
import javax.swing.border.*;

/** Constructs GUI for polynomial factorization. */
public class PolyFact {

  public static JFrame frame = null;

  private static JTextField nField;
  private static JTextField qField;
  private static JLabel polyToFactorize;
  private static JLabel cyclotomicCosets;
  private static JLabel gBasis;
  private static JLabel factors;

  public static void addComponentsToPane(Container rootPane) {
    // rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.Y_AXIS));

    JPanel pane = new JPanel();
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

    JPanel p = new JPanel();
    p.setBorder(new TitledBorder("Input"));

    JLabel nLabel = new JLabel("n = ");
    p.add(nLabel);

    nField = new JTextField("7", 3);
    p.add(nField);

    JLabel qLabel = new JLabel("q = ");
    p.add(qLabel);

    qField = new JTextField("2", 3);
    p.add(qField);

    JButton factorizeButton = new JButton("Factorize");
    factorizeButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
            // all the GUI logic is here

            int n = 0;
            try {
              n = Integer.valueOf(nField.getText());
            } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(
                  frame, "n is invalid.", "Wrong value of n", JOptionPane.ERROR_MESSAGE);
              return;
            }

            int q = 0;
            try {
              q = Integer.valueOf(qField.getText());
            } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(
                  frame, "q is invalid.", "Wrong value of q", JOptionPane.ERROR_MESSAGE);
              return;
            }

            if (n < 1) {
              JOptionPane.showMessageDialog(
                  frame,
                  "n must be greater than zero.",
                  "Wrong value of n",
                  JOptionPane.ERROR_MESSAGE);
              return;
            }

            if (q < 2) {
              JOptionPane.showMessageDialog(
                  frame,
                  "q must be greater than one.",
                  "Wrong value of q",
                  JOptionPane.ERROR_MESSAGE);
              return;
            }

            // check if q is prime with probability exceeding 1 - (1/2)^4
            if (!BigInteger.valueOf(q).isProbablePrime(4)) {
              JOptionPane.showMessageDialog(
                  frame, "q must be prime.", "Wrong value of q", JOptionPane.ERROR_MESSAGE);
              return;
            }

            polyToFactorize.setText(String.format("<html>f(x) = x<sup>%s</sup>-1", n));

            Factorization factorization = new Factorization(n, q);

            factorization.constructCosets();
            cyclotomicCosets.setText(factorization.cosetToHTMLString());

            factorization.construcyGBasis();
            gBasis.setText(factorization.gBasisToHTMLString());

            factorization.factorize();
            factors.setText(factorization.getFactorsToHTMLString());

            frame.pack();
          }
        });
    p.add(factorizeButton);

    pane.add(p);
    p = new JPanel();

    JLabel polyToFactorizeLabel = new JLabel("Polynomial to be factorized:");
    p.add(polyToFactorizeLabel);

    polyToFactorize = new JLabel("<html>f(x) = x<sup>n</sup>-1");
    p.add(polyToFactorize);

    pane.add(p);
    p = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p.setBorder(new TitledBorder("Cyclotomic cosets"));

    cyclotomicCosets = new JLabel("<html>C<sub>0</sub> = {0}");
    p.add(cyclotomicCosets);

    pane.add(p);
    p = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p.setBorder(new TitledBorder("Basis for G"));

    gBasis = new JLabel("<html>g<sub>1</sub>(x) = 1");
    p.add(gBasis);

    pane.add(p);
    p = new JPanel(new FlowLayout(FlowLayout.LEFT));
    p.setBorder(new TitledBorder("Irreducible factors"));

    factors = new JLabel("<html>f(x) = x<sup>n</sup>-1");
    p.add(factors);

    pane.add(p);

    JScrollPane jsp = new JScrollPane(pane);
    rootPane.add(jsp);
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private static void createAndShowGUI() {

    // Create and set up the window.
    frame = new JFrame("Factorization of polynomials");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set up the content pane.
    addComponentsToPane(frame.getContentPane());

    // Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ex) {
      System.out.println("Look and feel was not found.");
    }

    // Schedule a job for the event dispatch thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            createAndShowGUI();
          }
        });
  }
}
