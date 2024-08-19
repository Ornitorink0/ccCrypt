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
 * @since 2.0
 */


/* -------------------------------------------------------------------------- */
/*                             THIS IS IN TESTING                             */
/* -------------------------------------------------------------------------- */

import util.Algo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

public class Chat extends JFrame {
    private JTextField txtIPAddress = new JTextField("127.0.0.1");
    private JTextField txtPort = new JTextField("5000");
    private JTextField txtListenPort = new JTextField("5001");
    private JTextArea txtChatArea = new JTextArea();
    private JTextField txtMessage = new JTextField();
    private JButton btnSend = new JButton("Send");

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Chat() {
        super("CCCrypt - Chat");
        this.setLayout(new BorderLayout());

        // Top panel with IP, Port, and Listening Port
        JPanel topPanel = new JPanel(new GridLayout(1, 6));
        topPanel.add(new JLabel("Remote IP:"));
        topPanel.add(txtIPAddress);
        topPanel.add(new JLabel("Remote Port:"));
        topPanel.add(txtPort);
        topPanel.add(new JLabel("Listen Port:"));
        topPanel.add(txtListenPort);
        this.add(topPanel, BorderLayout.NORTH);

        // Chat area
        txtChatArea.setEditable(false);
        this.add(new JScrollPane(txtChatArea), BorderLayout.CENTER);

        // Bottom panel with message input and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(txtMessage, BorderLayout.CENTER);
        bottomPanel.add(btnSend, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Frame settings
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Event handling
        // Event on pressing enter key in input field
        txtMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexPage.historyModel.addElement("Sent \"" + txtMessage.getText() + "to " + txtIPAddress.getText() + "\" at " + new Date());
                sendMessage(txtMessage.getText());
                txtMessage.setText("");
            }
        });
        // Event on clicking send button
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexPage.historyModel.addElement("Sent \"" + txtMessage.getText() + "to " + txtIPAddress.getText() + "\" at " + new Date());
                sendMessage(txtMessage.getText());
                txtMessage.setText("");
            }
        });

        // Start the server in a separate thread to listen for incoming messages
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            IndexPage.historyModel.addElement("Started server at " + new Date());
            serverSocket = new ServerSocket(Integer.parseInt(txtListenPort.getText()));
            txtChatArea.append("Listening on port: " + txtListenPort.getText() + "\n");

            while (true) {
                socket = serverSocket.accept(); // Attende la connessione di un client
                txtChatArea.append("Connected to peer.\n");

                IndexPage.historyModel.addElement("Connected to peer to" + txtListenPort.getText() + " at " + new Date());

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Start a thread to handle incoming messages from this connection
                new Thread(new ReceiveMessages()).start();
            }
        } catch (IOException e) {
            IndexPage.historyModel.addElement("Failed to start server: " + txtListenPort.getText() + " at " + new Date());
            txtChatArea.append("Failed to start server. Try again or check if the port is available.\n");
            e.printStackTrace();
        }
    }

    private void connectToPeer() {
        try {
            socket = new Socket(txtIPAddress.getText(), Integer.parseInt(txtPort.getText()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            txtChatArea.append("Connected to remote peer.\n");

            IndexPage.historyModel.addElement("Connected to remote peer at " + txtIPAddress.getText() + ":" + txtPort.getText() + " at " + new Date());

            // Start a thread to handle incoming messages from the connected peer
            new Thread(new ReceiveMessages()).start();

        } catch (IOException e) {
            IndexPage.historyModel.addElement("Failed to connect: " + txtIPAddress.getText() + ":" + txtPort.getText() + " at " + new Date());
            txtChatArea.append("Failed to connect to remote peer. Try again or check if the port is available.\n");
            e.printStackTrace();
        }
    }

    class ReceiveMessages implements Runnable {
        @Override
        public void run() {
            try {
                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    // Decrypt the message before displaying it
                    String decryptedMessage = Algo.decryptMessage(receivedMessage);
                    txtChatArea.append("Peer: " + decryptedMessage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String message) {
        // Connect to the peer if not connected already
        if (socket == null || socket.isClosed()) {
            connectToPeer();
        }

        if (out != null) {
            String encryptedMessage = Algo.encryptMessage(message);
            out.println(encryptedMessage);
            txtChatArea.append("You: " + message + "\n");
        }
    }
}
