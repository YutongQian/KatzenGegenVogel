package de.katzengegenvogel.backend;

import de.katzengegenvogel.dto.Person;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 * Verwaltet den Zugriff auf gespeicherte Personendaten.
 *
 * <p>Die Klasse ermöglicht das Lesen und Speichern von Personen
 * in Dateien. Dadurch können Benutzerdaten dauerhaft gespeichert
 * und bei einem späteren Start der Anwendung wieder geladen werden.</p>
 *
 */
public class Dateizugriff {
    /**
     * Liest mehrere Personen aus einer Datei ein.
     *
     * <p>Die gespeicherten Personendaten werden aus der angegebenen
     * Datei gelesen und als Map zurückgegeben. Dabei wird der
     * Benutzername als Schlüssel verwendet.</p>
     *
     * @param ziel Datei, aus der die Personendaten gelesen werden
     * @return eine Map mit den gelesenen Personen oder {@code null},
     *         wenn die Datei nicht gelesen werden konnte
     */
    public Map<String,Person> lesenVielerPerson(File ziel){
        Map<String, Person> rueckgabe=new HashMap<>();
        if (!ziel.exists() || ziel.length() == 0) {
            return rueckgabe;}
        try(
                ObjectInputStream leser = new ObjectInputStream(new FileInputStream(ziel))
                ){
            Object gelesenesObjekt=leser.readObject();
            rueckgabe=(Map<String,Person>)gelesenesObjekt;
        }catch(IOException|ClassNotFoundException ausnahme){
            ausnahme.printStackTrace();
        }
        return rueckgabe;

    }
    /**
     * Speichert eine Person in einer Datei.
     *
     * Die angegebene Person wird zusammen mit den bereits
     * gespeicherten Personendaten in der Zieldatei gespeichert.
     *
     * @param ziel Datei, in der die Person gespeichert werden soll
     * @param person Person, die gespeichert werden soll
     * @return eine Meldung über den Erfolg oder Fehler beim Speichern
     */
    public String speicherPerson(File ziel, Person person){
        String rueckgabe="";
        Map<String,Person> personListVonFile= lesenVielerPerson(ziel);
        personListVonFile.put(person.getUsername(),person);

            try(
                    OutputStream schreiber = new FileOutputStream(ziel);
                    ObjectOutputStream dekoSchreiber=new ObjectOutputStream(schreiber)
                    ){
                dekoSchreiber.writeObject(personListVonFile);
            }catch(IOException ausnahme){
                ausnahme.printStackTrace();
            }
            rueckgabe+="Die Registrierung für das Spiel war erfolgreich !";
            return rueckgabe;


    }



}
