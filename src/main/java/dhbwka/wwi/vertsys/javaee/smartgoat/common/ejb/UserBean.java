/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.smartgoat.common.ejb;


import dhbwka.wwi.vertsys.javaee.smartgoat.common.jpa.User;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



/**
 * Spezielle EJB zum Anlegen eines Benutzers und Aktualisierung des Passworts.
 */
@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager em;
    
    @Resource
    EJBContext ctx;

    /**
     * Gibt das Datenbankobjekt des aktuell eingeloggten Benutzers zurück,
     *
     * @return Eingeloggter Benutzer oder null
     */
    public User getCurrentUser() {
        return this.em.find(User.class, this.ctx.getCallerPrincipal().getName());
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.username").getResultList();
    }
    
    public User findByUsername(String username) {
        return em.find(User.class, username);
    }
    
    /**
     *
     * @param username
     * @param firstname
     * @param lastname
     * @param password
     * @param email
     * @throws UserBean.UserAlreadyExistsException
     */
    public void signup(String username, String password, String firstname,String lastname, String email) throws UserAlreadyExistsException {
        if (em.find(User.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", username));
        }

        User user = new User(username, password, firstname, lastname, email);
        user.addToGroup("app-user");  
        
        em.persist(user);
    }
    
    
    public void validateUser(String username, String password)
            throws InvalidCredentialsException, AccessRestrictedException, InvalidCredentialsException {

        // Benutzer suchen und Passwort prüfen
        User user = em.find(User.class, username);
     

        if (user == null || !user.checkPassword(password)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort falsch.");
        }

        // Zugeordnete Benutzergruppen prüfen, mindestens eine muss vorhanden sein
          
        // Alles okay!
     
    }


    /**
     * Passwort ändern (ohne zu speichern)
     * @param user
     * @param oldPassword
     * @param newPassword
     * @throws UserBean.InvalidCredentialsException
     */
    @RolesAllowed({"app-user"})

    public void changePassword(User user, String oldPassword, String newPassword) throws InvalidCredentialsException {
        if (user == null || !user.checkPassword(oldPassword)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }

        user.setPassword(newPassword);
    }
    
    

    /**
     * Benutzer löschen
     * @param user Zu löschender Benutzer
     */
    @RolesAllowed({"app-user","admin"})
    public void delete(User user) {
        this.em.remove(user);
    }
    
    
    /**
     * Benutzer aktualisieren
     * @param user Zu aktualisierender Benutzer
     * @return Gespeicherter Benutzer
     */
    @RolesAllowed({"app-user"})
    public User update(User user) {
        return em.merge(user);
    }

    public static class AccessRestrictedException extends Exception {

        public AccessRestrictedException() {
        }
    }

    /**
     * Fehler: Der Benutzername ist bereits vergeben
     */
    public class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    /**
     * Fehler: Das übergebene Passwort stimmt nicht mit dem des Benutzers
     * überein
     */
    public class InvalidCredentialsException extends Exception {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

}