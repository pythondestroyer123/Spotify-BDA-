/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package mx.itson.spotify.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mx.itson.spotify.db.ConnectionDB;



/**
 *
 * @author emili
 */
public class PlaylistForm extends javax.swing.JDialog {

    private DefaultTableModel model;
    private int selectedPlaylistId = -1;
    private int currentUserId;
    private Map<Integer, Integer> rowIdMap = new HashMap<>();

    public PlaylistForm(java.awt.Frame parent, boolean modal, int userId) {
        super(parent, modal);
        this.currentUserId = userId;
        initComponents();
        model = (DefaultTableModel) tblMyPlaylists.getModel();
        loadPlaylists();
        
        tblMyPlaylists.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblMyPlaylists.getSelectedRow();
                if (row >= 0) {
                    selectedPlaylistId = rowIdMap.get(row);
                    txtMyPlaylistName.setText(model.getValueAt(row, 0).toString());
                    txtMyPlaylistDescription.setText(model.getValueAt(row, 1).toString());
                }
            }
        });
    }

    private void loadPlaylists() {
 
    rowIdMap.clear();
    try (Connection conn = ConnectionDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(
             "SELECT playlist_id, playlistName, description FROM Playlist WHERE user_id = ?")) {
        
        ps.setInt(1, currentUserId);
        ResultSet rs = ps.executeQuery();
        
        model.setRowCount(0);
        while (rs.next()) {
            int playlistId = rs.getInt("playlist_id");
            rowIdMap.put(model.getRowCount(), playlistId);
            model.addRow(new Object[]{
                rs.getString("playlistName"),
                rs.getString("description")
            });
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading playlists: " + e.getMessage());
        e.printStackTrace();
    }
}
   
    private void clearFields() {
        txtMyPlaylistName.setText("");
        txtMyPlaylistDescription.setText("");
        selectedPlaylistId = -1;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMyPlaylists = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMyPlaylistName = new javax.swing.JTextField();
        txtMyPlaylistDescription = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnManagePlaylistSongs = new javax.swing.JButton();
        btnBackToMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("My Playlists");

        tblMyPlaylists.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {".", "."},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Playlist Name", "Description"
            }
        ));
        jScrollPane1.setViewportView(tblMyPlaylists);

        jLabel2.setText("Playlist name:");

        jLabel3.setText("Describe your playlist in 5 words:");

        txtMyPlaylistName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMyPlaylistNameActionPerformed(evt);
            }
        });

        txtMyPlaylistDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMyPlaylistDescriptionActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnManagePlaylistSongs.setText("Manage Playlist Songs");
        btnManagePlaylistSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagePlaylistSongsActionPerformed(evt);
            }
        });

        btnBackToMenu.setText("Back to menu");
        btnBackToMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackToMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMyPlaylistName, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                            .addComponent(txtMyPlaylistDescription))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd)
                            .addComponent(btnDelete)
                            .addComponent(btnEdit)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(btnBackToMenu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnManagePlaylistSongs)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMyPlaylistName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMyPlaylistDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnManagePlaylistSongs)
                    .addComponent(btnBackToMenu))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
      String name = txtMyPlaylistName.getText().trim();
        String description = txtMyPlaylistDescription.getText().trim();

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos");
            return;
        }
        
        String[] words = description.split("\\s+");
        if (words.length > 5) {
            JOptionPane.showMessageDialog(this, "La descripción debe tener máximo 5 palabras");
            return;
        }

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "INSERT INTO Playlist (playlistName, description, user_id) VALUES (?, ?, ?)")) {
            
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, currentUserId);
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Playlist creada exitosamente");
            loadPlaylists();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al crear playlist: " + e.getMessage());
        }                                                                               
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
                                        
    if (selectedPlaylistId == -1) {
        JOptionPane.showMessageDialog(this, "Select a playlist to delete");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Are you sure you want to delete this playlist?", 
        "Confirm Deletion", 
        JOptionPane.YES_NO_OPTION);
    
    if (confirm != JOptionPane.YES_OPTION) return;

    try (Connection conn = ConnectionDB.getConnection()) {
        // No need to manually delete Playlist_Song records due to ON DELETE CASCADE
        try (PreparedStatement ps = conn.prepareStatement(
            "DELETE FROM Playlist WHERE playlist_id = ?")) {
            ps.setInt(1, selectedPlaylistId);
            ps.executeUpdate();
        }
        
        JOptionPane.showMessageDialog(this, "Playlist deleted successfully");
        loadPlaylists();
        clearFields();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error deleting playlist: " + e.getMessage());
        e.printStackTrace();
    }                                                                                         
                                                                              
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
         if (selectedPlaylistId == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una playlist para editar");
            return;
        }

        String name = txtMyPlaylistName.getText().trim();
        String description = txtMyPlaylistDescription.getText().trim();

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos");
            return;
        }

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "UPDATE Playlist SET playlistName = ?, description = ? WHERE playlist_id = ?")) {
            
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, selectedPlaylistId);
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "Playlist actualizada exitosamente");
            loadPlaylists();
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar playlist: " + e.getMessage());
        }         
    }//GEN-LAST:event_btnEditActionPerformed

    private void txtMyPlaylistNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMyPlaylistNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMyPlaylistNameActionPerformed

    private void txtMyPlaylistDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMyPlaylistDescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMyPlaylistDescriptionActionPerformed

    private void btnBackToMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackToMenuActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBackToMenuActionPerformed

    private void btnManagePlaylistSongsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagePlaylistSongsActionPerformed
         if (selectedPlaylistId == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona una playlist primero");
                return;
            }

            PlaylistSongForm form = new PlaylistSongForm(
                this, // Ventana padre
                true, // Modal
                selectedPlaylistId, // ID de la playlist seleccionada
                currentUserId // ID del usuario actual
            );
            form.setLocationRelativeTo(this);
            form.setVisible(true);


    }//GEN-LAST:event_btnManagePlaylistSongsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlaylistForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlaylistForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlaylistForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlaylistForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int userId = 1; // O usa el ID real del usuario
                PlaylistForm dialog = new PlaylistForm(new javax.swing.JFrame(), true, userId);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBackToMenu;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnManagePlaylistSongs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMyPlaylists;
    private javax.swing.JTextField txtMyPlaylistDescription;
    private javax.swing.JTextField txtMyPlaylistName;
    // End of variables declaration//GEN-END:variables
}
