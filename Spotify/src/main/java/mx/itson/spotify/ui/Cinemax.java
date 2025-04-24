/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mx.itson.spotify.ui;

import mx.itson.spotify.model.MessageResponse;
import mx.itson.spotify.model.User;
import mx.itson.spotify.service.UserService;
import javax.swing.JOptionPane;

/**
 *
 * @author Emiliano
 */
public class Cinemax {

    public static void main(String[] args) {
        
        //Pruebas de registro
//        User user = new User();
//        user.setUsername("JINC06");
//        user.setPassword("admin123");
//        user.setActive(true);
//        user.setNombres("Julio Isaac");
//        user.setApellidos("Nava Cordero");
//        user.setCorreo("julio.nava92307@potros.itson.edu.mx");
//        user.setCelular("+526221002760");
//        
//        MessageResponse msg = UserService.register(user);
//        JOptionPane.showMessageDialog(null, msg.getMessage());

        //No existe ni usuario
        //MessageResponse<User> response = UserService.login("jose", "123456");
        //Usuario exista mal la contraseña
        //MessageResponse<User> response = UserService.login("JINC06", "123456");
        //Último escenario todo correcto
        MessageResponse<User> response = UserService.login("JINC06", "admin123");
        if(response.isSuccess()) {
            User user = response.getResponse();
            JOptionPane.showMessageDialog(null, "Bienvenido " + user.getNombres() + " " + user.getApellidos());
        }else{
            JOptionPane.showMessageDialog(null, response.getMessage());
        }
        
        
    }
}
