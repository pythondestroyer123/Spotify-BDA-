/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.model;

/**
 *
 * @author Emiliano
 */
public class MessageResponse<T> {
    
    private boolean success;
    private String message;
    private T response;

    public MessageResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public MessageResponse(boolean success, String message, T response) {
        this.success = success;
        this.message = message;
        this.response = response;
    }

    public MessageResponse() {
        
    }
    
    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
    
}
