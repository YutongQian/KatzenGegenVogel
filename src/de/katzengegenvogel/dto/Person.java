package de.katzengegenvogel.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Repräsentiert eine Person bzw. einen Benutzer der Anwendung.
 * Die Klasse ist als einfache POJO-Klasse aufgebaut und enthält
 * den Benutzernamen sowie das Passwort eines Benutzers.
 *
 */
public class Person implements Serializable {

    private String username;
    private String password;

    public Person() {

    }

    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(username, person.username) && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
