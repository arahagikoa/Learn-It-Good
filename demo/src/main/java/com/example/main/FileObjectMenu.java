package com.example.main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.*;


public class FileObjectMenu extends JMenuBar {
    private FileObject basefield;
    MainPanel mainPanel;
    private ChatWindow chat_Window;

    public FileObjectMenu(FileObject Panel, FilePanel frame, ChatWindow chat_Window) {
        this.basefield = Panel;
        this.chat_Window = chat_Window;
        JMenu menu = new JMenu("\\/");
        this.add(menu);
        JDBCconn cmds = new JDBCconn();
        JMenuItem usun = new JMenuItem("Usuń");
        JMenuItem zmien = new JMenuItem("Zmień nazwę");
        JMenuItem uzyj = new JMenuItem("Użyj");

        usun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = basefield.getParent();
                if (parent instanceof JPanel) {
                    DataBaseManager mng = DataBaseManager.getInstance();
                    String tableName = basefield.TableName;
                    String fileName = basefield.docTitle.getText();
                    try (Connection conn = mng.getConnection();) {
                        cmds.deleteDataFile(conn, tableName, fileName);
                        ((JPanel) parent).remove(basefield);
                        ((JPanel) parent).revalidate();
                        ((JPanel) parent).repaint();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        zmien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Podaj nową nazwę dla tego pliku");
                if (name != null && !name.trim().isEmpty()) {
                    DataBaseManager mng = DataBaseManager.getInstance();
                    try (Connection conn = mng.getConnection();) {
                        String tableName = basefield.TableName;
                        String fileName = basefield.docTitle.getText();
                        String newFileName = name.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");

                        cmds.changeDataFileName(conn, tableName, newFileName, fileName);
                        basefield.docTitle.setText(newFileName);
                        ((JPanel) basefield.getParent()).revalidate();
                        ((JPanel) basefield.getParent()).repaint();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        uzyj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("file name(uzyj): " + Panel.docTitle.getText() + "\n");
                System.out.println("table name(uzyj): " + Panel.TableName);
            	chat_Window.settableData(Panel.docTitle.getText(), Panel.TableName);
            	
            	
            	chat_Window.setFileObject(basefield);
                //basefield.save_file_text(basefield);
                //basefield.call();
                
            	
            	
            	//chat_Window.updateChatWithPrompt(basefield.prompt);
            }
        });

        menu.add(usun);
        menu.add(zmien);
        menu.add(uzyj);
    }
}
