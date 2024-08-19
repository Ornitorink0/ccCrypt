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
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Date;

import util.Algo;

public class Decrypt extends JFrame {
    private JTextArea txtaMessage = new JTextArea();
    private JButton btnCopyResult = new JButton("Copy");

    public Decrypt(String message) {
        // Frame
        super("CCCrypt - Encrypt");
        this.setLayout(new BorderLayout());

        // Components
        this.add(new JScrollPane(txtaMessage), BorderLayout.CENTER);
        this.add(btnCopyResult, BorderLayout.PAGE_END);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(300, 200));
        this.setSize(800, 600);
        this.pack();
        this.setVisible(true);

        // Operations
        String decryptedMessage = Algo.decryptMessage(message);
        txtaMessage.setText(decryptedMessage);

        // Events
        // Copy to clipboard
        btnCopyResult.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(decryptedMessage);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            IndexPage.historyModel.addElement("Copied \"" + txtaMessage.getText() + "\" at " + new Date());
        });
    }
}
