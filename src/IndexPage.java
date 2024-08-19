/*
 * CCCrypt - Simple GUI Text Cryptor
 * By Ornitorink0
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Ornitorink0
 * @since 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class IndexPage extends JFrame {
    private JButton btnEncrypt = new JButton("Encrypt");
    private JButton btnDecrypt = new JButton("Decrypt");
    private JTextArea txtaMessage = new JTextArea();
    private JPanel contentPane = new JPanel();
    private JButton btnSave = new JButton("Save");
    private JButton btnLoad = new JButton("Load");
    private JFileChooser fileChooser = new JFileChooser();
    public static DefaultListModel<String> historyModel = new DefaultListModel<>();
    private JList<String> lstHistory = new JList<>(historyModel);

    public IndexPage() {
        // Frame
        super("CCCrypt");
        this.setLayout(new BorderLayout());

        // Components
        contentPane.setLayout(new FlowLayout());
        contentPane.add(btnSave);
        contentPane.add(btnLoad);
        contentPane.add(btnEncrypt);
        contentPane.add(btnDecrypt);
        this.add(contentPane, BorderLayout.PAGE_START);
        this.add(new JScrollPane(txtaMessage), BorderLayout.CENTER);
        this.add(new JScrollPane(lstHistory), BorderLayout.PAGE_END);

        // Frame Settings
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(350, 400));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // Events
        btnSave.addActionListener(e -> saveToFile(txtaMessage.getText()));
        btnLoad.addActionListener(e -> txtaMessage.setText(loadFromFile()));
        btnEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtaMessage.getText().isEmpty()) {
                    historyModel.addElement("Failed to encrypt at " + new Date());
                    txtaMessage.setText("Message cannot be empty!");
                } else {
                    historyModel.addElement("Encrypted \"" + txtaMessage.getText() + "\" at " + new Date());
                    new Encrypt(txtaMessage.getText());
                }
            }
        });
        btnDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtaMessage.getText().isEmpty()) {
                    historyModel.addElement("Failed to decrypt at " + new Date());
                    txtaMessage.setText("Message cannot be empty!");
                } else {
                    historyModel.addElement("Decrypted \"" + txtaMessage.getText() + "\" at " + new Date());
                    new Decrypt(txtaMessage.getText());
                }
            }
        });
    }

    private String saveToFile(String message) {
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".txt");
                }
            }

            @Override
            public String getDescription() {
                return "Text Files (*.txt)";
            }
        });
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".txt")) {
                    file = new File(file.getParentFile(), file.getName() + ".txt");
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(message);
                writer.close();
                historyModel.addElement("Saved \"" + file + "\" at " + new Date());
                return file.getAbsolutePath();
            } catch (IOException e) {
                historyModel.addElement("IOException: Failed to save \"" + fileChooser.getSelectedFile() + "\" at " + new Date());
                e.printStackTrace();
            }
        }
        return null;
    }

    private String loadFromFile() {
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".txt");
                }
            }

            @Override
            public String getDescription() {
                return "Text Files (*.txt)";
            }
        });
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String message = reader.readLine();
                reader.close();
                historyModel.addElement("Loaded \"" + file + "\" at " + new Date());
                return message;
            } catch (IOException e) {
                historyModel.addElement("IOException: Failed to load \"" + fileChooser.getSelectedFile() + "\" at " + new Date());
                e.printStackTrace();
            }
        }
        return null;
    }
}
