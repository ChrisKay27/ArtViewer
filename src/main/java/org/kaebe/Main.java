package org.kaebe;

import javax.swing.*;
import org.kaebe.ui.UI;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new UI(args[0]));
    }
}
