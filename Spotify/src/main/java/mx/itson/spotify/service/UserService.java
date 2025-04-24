/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.service;

import mx.itson.spotify.dao.UserDAO;
import mx.itson.spotify.model.MessageResponse;
import mx.itson.spotify.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @author Emiliano
 */
public class UserService {
    
    public static MessageResponse register(User user) {
        User newUser = UserDAO.register(user);
        if(newUser != null) {
            return new MessageResponse(true, "Usuario registrado con éxito");
        }else{
            return new MessageResponse(false, "Ocurrió un error al registrar el usuario");
        }
    }
    
    public static MessageResponse login(String username, String password) {
        User userFind = UserDAO.findByUsername(username);
        if(userFind == null) {
            return new MessageResponse(false, "Usuario y/o contraseña incorrectos");
        }
        
        if(!UserDAO.validatePassoword(userFind, password)) {
            return new MessageResponse(false, "Usuario y/o contraseña incorrectos");
        }
        
        MessageResponse<User> responseSuccess = new MessageResponse<User>();
        responseSuccess.setSuccess(true);
        responseSuccess.setMessage("Inicio de sesión éxitoso");
        responseSuccess.setResponse(userFind);
        return responseSuccess;
    }
    
}
