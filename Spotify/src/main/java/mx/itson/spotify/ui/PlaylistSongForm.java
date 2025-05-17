/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package mx.itson.spotify.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mx.itson.spotify.db.ConnectionDB;

public class PlaylistSongForm extends javax.swing.JDialog {
    private int currentPlaylistId;
    private int currentUserId;
    private DefaultComboBoxModel<ComboItem> playlistsModel;

    // Constructor corregido con 4 parámetros
    public PlaylistSongForm(java.awt.Dialog parent, boolean modal, int playlistId, int userId) {
    super(parent, modal);
    // resto del código

    this.currentPlaylistId = playlistId;
    this.currentUserId = userId;
    initComponents();
    setupTables();
    loadData();
}       // Añade esta línea


    private void setupTables() {
        
    if (cmbMyPlaylists == null || tblSongsAvailable == null || tblMyPlaylist == null) {
        JOptionPane.showMessageDialog(this, "Error: Componentes no inicializados");
        return;
    }
    // Resto del código...

        // Configurar modelo para el combo box
        playlistsModel = new DefaultComboBoxModel<>();
        cmbMyPlaylists.setModel(playlistsModel);

        // Configurar modelos de tabla con columnas adecuadas
        String[] columnNames = {"ID", "Canción", "Duración", "Álbum", "Artista"};
        DefaultTableModel modelAvailable = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableModel modelInPlaylist = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSongsAvailable.setModel(modelAvailable);
        tblMyPlaylist.setModel(modelInPlaylist);
    }

    private void loadData() {
        loadPlaylists();
        loadAllSongs();
        loadPlaylistSongs();
        hideIdColumns();
        
        // Seleccionar la playlist actual en el combo
        selectCurrentPlaylist();
    }

    private void selectCurrentPlaylist() {
        for (int i = 0; i < playlistsModel.getSize(); i++) {
            ComboItem item = playlistsModel.getElementAt(i);
            if (item.getId() == currentPlaylistId) {
                cmbMyPlaylists.setSelectedIndex(i);
                break;
            }
        }
    }

    // Clase interna para manejar items del combo
    private static class ComboItem {
    private final int id;
    private final String name;

    public ComboItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    @Override
    public String toString() { return name; }
}

    private void loadPlaylists() {
    playlistsModel.removeAllElements();
    try (Connection conn = ConnectionDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(
             "SELECT playlist_id, playlistName FROM Playlist WHERE user_id = ?")) {
        
        ps.setInt(1, currentUserId);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            playlistsModel.addElement(new ComboItem(
                rs.getInt("playlist_id"), 
                rs.getString("playlistName"))
            );
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading playlists: " + e.getMessage());
        e.printStackTrace();
    }
}

private void loadAllSongs() {
    DefaultTableModel model = (DefaultTableModel) tblSongsAvailable.getModel();
    model.setRowCount(0);

    try (Connection conn = ConnectionDB.getConnection()) {
        String query = "SELECT s.song_id, s.songName, s.duration, a.albumName, ar.artistName " +
                      "FROM Song s " +
                      "LEFT JOIN Album a ON s.album_id = a.album_id " +
                      "LEFT JOIN Artist ar ON a.artist_id = ar.artist_id " +
                      "WHERE s.song_id NOT IN (SELECT song_id FROM playlist_song WHERE playlist_id = ?)";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, currentPlaylistId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("song_id"),
                rs.getString("songName"),
                formatDuration(rs.getInt("duration")),
                rs.getString("albumName"),
                rs.getString("artistName")
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading available songs: " + e.getMessage());
        e.printStackTrace();
    }
}   

    private void loadPlaylistSongs() {
    
    DefaultTableModel model = (DefaultTableModel) tblMyPlaylist.getModel();
    model.setRowCount(0);

    try (Connection conn = ConnectionDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(
             "SELECT s.song_id, s.songName, s.duration, al.albumName, ar.artistName " +
             "FROM Playlist_Song ps " +
             "JOIN Song s ON ps.song_id = s.song_id " +
             "LEFT JOIN Album al ON s.album_id = al.album_id " +
             "LEFT JOIN Artist ar ON al.artist_id = ar.artist_id " +
             "WHERE ps.playlist_id = ? " +
             "ORDER BY s.songName")) {
        
        ps.setInt(1, currentPlaylistId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("song_id"),
                rs.getString("songName"),
                formatDuration(rs.getInt("duration")),
                rs.getString("albumName"),
                rs.getString("artistName")
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading playlist songs: " + e.getMessage());
        e.printStackTrace();
    }
}
    
private String formatDuration(int seconds) {
    int minutes = seconds / 60;
    int remainingSeconds = seconds % 60;
    return String.format("%d:%02d", minutes, remainingSeconds);
}
    
    private void hideIdColumns() {
        // Ocultar columna ID (posición 0) en ambas tablas
        tblSongsAvailable.getColumnModel().getColumn(0).setMinWidth(0);
        tblSongsAvailable.getColumnModel().getColumn(0).setMaxWidth(0);
        
        tblMyPlaylist.getColumnModel().getColumn(0).setMinWidth(0);
        tblMyPlaylist.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbMyPlaylists = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMyPlaylist = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDone = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSongsAvailable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Get fun with your playlist!");

        jLabel2.setText("Select a playlist:");

        cmbMyPlaylists.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMyPlaylistsActionPerformed(evt);
            }
        });

        tblMyPlaylist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Song Name", "Duration", "Album Name", "Artist Name"
            }
        ));
        jScrollPane1.setViewportView(tblMyPlaylist);

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

        btnDone.setText("Done");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        tblSongsAvailable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Song Name", "Duration", "Album Name", "Artist Name"
            }
        ));
        jScrollPane2.setViewportView(tblSongsAvailable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(335, 335, 335)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addGap(87, 87, 87)
                                .addComponent(btnDelete)
                                .addGap(399, 399, 399)
                                .addComponent(btnDone))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cmbMyPlaylists, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(40, 40, 40)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbMyPlaylists, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDone)
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnDelete))
                        .addGap(38, 38, 38))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbMyPlaylistsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMyPlaylistsActionPerformed
        ComboItem selectedItem = (ComboItem) cmbMyPlaylists.getSelectedItem();
    if (selectedItem != null) {
        currentPlaylistId = selectedItem.getId();
        loadPlaylistSongs();
        loadAllSongs();
    }   
    }//GEN-LAST:event_cmbMyPlaylistsActionPerformed
    
    
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
    addSongToPlaylist();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        removeSongFromPlaylist();
    }//GEN-LAST:event_btnDeleteActionPerformed
   private void addSongToPlaylist() {
    int selectedRow = tblSongsAvailable.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a song to add");
        return;
    }

    // Obtener el ID de la canción seleccionada
    int songId = (int) tblSongsAvailable.getValueAt(selectedRow, 0);
    
    // Verificar si la playlist está seleccionada
    if (currentPlaylistId == -1) {
        JOptionPane.showMessageDialog(this, "No playlist selected");
        return;
    }

    try (Connection conn = ConnectionDB.getConnection()) {
        // Verificar si la canción ya existe en la playlist
        String checkQuery = "SELECT COUNT(*) FROM playlist_song WHERE playlist_id = ? AND song_id = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setInt(1, currentPlaylistId);
        checkStmt.setInt(2, songId);
        ResultSet rs = checkStmt.executeQuery();
        
        if (rs.next() && rs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(this, "This song is already in the playlist");
            return;
        }

        // Insertar la canción en la playlist
        String insertQuery = "INSERT INTO playlist_song (playlist_id, song_id) VALUES (?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        insertStmt.setInt(1, currentPlaylistId);
        insertStmt.setInt(2, songId);
        insertStmt.executeUpdate();

        // Actualizar ambas tablas
        loadAllSongs();
        loadPlaylistSongs();
        
        JOptionPane.showMessageDialog(this, "Song added to playlist successfully");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error adding song to playlist: " + e.getMessage());
        e.printStackTrace();
    }

    }

    private void removeSongFromPlaylist() {
        int selectedRow = tblMyPlaylist.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una canción para quitar");
            return;
        }

        int songId = (int) tblMyPlaylist.getValueAt(selectedRow, 0);

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "DELETE FROM playlist_song WHERE playlist_id = ? AND song_id = ?")) {
            
            ps.setInt(1, currentPlaylistId);
            ps.setInt(2, songId);
            ps.executeUpdate();

            // Actualizar ambas tablas
            loadAllSongs();
            loadPlaylistSongs();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error quitando canción: " + e.getMessage());
        }
    }
    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        dispose();
    }//GEN-LAST:event_btnDoneActionPerformed

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
            java.util.logging.Logger.getLogger(PlaylistSongForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlaylistSongForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlaylistSongForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlaylistSongForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                    javax.swing.JDialog dummyParent = new javax.swing.JDialog();
                    PlaylistSongForm dialog = new PlaylistSongForm(
                    dummyParent, 
                    true, 
                    1,  // playlistId (ejemplo)
                    1   // userId (ejemplo)
                );
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDone;
    private javax.swing.JComboBox<ComboItem> cmbMyPlaylists;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblMyPlaylist;
    private javax.swing.JTable tblSongsAvailable;
    // End of variables declaration//GEN-END:variables
}
