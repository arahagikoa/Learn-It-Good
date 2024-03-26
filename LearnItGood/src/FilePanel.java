import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FilePanel extends JPanel {
    private JPanel fileContainer;
    private JButton addButton;

    public FilePanel() {
        setLayout(new BorderLayout());

        fileContainer = new JPanel();
        fileContainer.setLayout(new BoxLayout(fileContainer, BoxLayout.Y_AXIS)); // Ustawienie BoxLayout do osi y
        JScrollPane scrollPane = new JScrollPane(fileContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        addButton = new JButton("Dodaj plik");
        //obsługa przycisku do dodawnaia plików
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(FilePanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    addFile(selectedFile);
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }
    //obsługa plików
    private void addFile(File file) {
        JPanel fileEntry = new JPanel(); // Nowy panel dla każdego wpisu pliku
        fileEntry.setLayout(new FlowLayout(FlowLayout.LEFT)); // Ustawienie FlowLayout, dla lewego rogu

        JLabel fileLabel = new JLabel(file.getName());
        JButton deleteButton = new JButton("Usuń");
        JButton renameButton = new JButton("Zmień nazwę");
        //obsługa usuwaina plików
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileContainer.remove(fileEntry);
                FilePanel.this.revalidate();
                FilePanel.this.repaint();
            }
        });
        //obsługa zmiany nazwy
        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog(FilePanel.this, "Podaj nową nazwę:");
                if (newName != null && !newName.isEmpty()) {
                    fileLabel.setText(newName);
                }
            }
        });

        fileEntry.add(fileLabel);
        fileEntry.add(deleteButton);
        fileEntry.add(renameButton);
        fileContainer.add(fileEntry);
        fileContainer.revalidate();
        fileContainer.repaint();
    }
    public void setBackgroundColor(Color color) {
        this.setBackground(color);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("File Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        FilePanel filePanel = new FilePanel();
        frame.add(filePanel);

        frame.setVisible(true);
    }
}
