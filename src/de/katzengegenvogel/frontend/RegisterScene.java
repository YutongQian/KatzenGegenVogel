package de.katzengegenvogel.frontend;

import de.katzengegenvogel.backend.Dateizugriff;
import de.katzengegenvogel.dto.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.Map;

import static asset.Bild.HINTERGRUND;

/**
 * <pre>Stellt die Registrierungs- und Anmeldeszene des Spiels dar.
 *
 * In dieser Szene kann der Spieler ein neues Benutzerkonto
 * erstellen oder sich mit einem bereits vorhandenen Konto anmelden.</p>
 *
 * <p>Die eingegebenen Benutzerdaten werden überprüft und bei einer
 * erfolgreichen Registrierung bzw. Anmeldung wird zur nächsten
 * Szene gewechselt.</pre>
 *
 */
public class RegisterScene implements SceneInter {

    private Scene scene;
    private boolean istAngemeldet = false;
    private Label meldung = new Label();

    public RegisterScene() {
        init();

//        root = new BorderPane();
//        hauptBereiche = new StackPane() ;


    }
    /**
     * Initialisiert die Benutzeroberfläche der Registrierungs- und Anmeldeszene.
     *
     * <p>Hier werden die Textfelder, Buttons und weitere Elemente
     * für die Registrierung und Anmeldung erstellt.</p>
     */
    @Override
    public void init() {
//        VBox vBox = new VBox();
        StackPane root = new StackPane();
        ImageView hintergrund = new ImageView(HINTERGRUND.getImage());
        hintergrund.setFitHeight(1000);
        hintergrund.setFitWidth(800);
        hintergrund.setPreserveRatio(true);

        GridPane grid = new GridPane();
        Label title = new Label("Willkommen in der verrückten Welt der Spiele ");
        title.setStyle(
                "-fx-text-fill: #e1c1c1;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, #24152F, 2, 1.0, 0, 0);");


        title.setAlignment(Pos.TOP_CENTER);
        Label userName = new Label("Username");
        userName.setStyle("-fx-text-fill: #e1c1c1;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, #24152F, 2, 1.0, 0, 0);");
        TextField userNameField = new TextField();
        userNameField.setPromptText("Bitte geben Sie Ihren Benutzernamen ein");
        Label passwort = new Label("Password");
        passwort.setStyle("-fx-text-fill: #e1c1c1;" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, #24152F, 2, 1.0, 0, 0);");
        PasswordField passwortField = new PasswordField();
        passwortField.setPromptText("Bitte geben Sie Ihre Password ein");
        Button anmelden = new Button("Anmelden");
        Button registrieren = new Button("Registrieren");
        meldung.setStyle("-fx-text-fill: rgba(255,105,172,0.91); -fx-font-size: 12px;");

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(title, 0, 0, 2, 1);
        grid.add(userName, 0, 1);
        grid.add(userNameField, 1, 1);
        grid.add(passwort, 0, 2);
        grid.add(passwortField, 1, 2);
        grid.add(anmelden, 0, 3);
        grid.add(registrieren, 1, 3);
        grid.add(meldung, 0, 4, 2, 1);
        root.getChildren().addAll(hintergrund, grid);


        this.scene = new Scene(root, 1000, 800);
        anmelden.setOnAction(event -> {
                    String user = userNameField.getText();
                    String pin = passwortField.getText();
                    if (onAnmeldenButtonClicked(user, pin)) {
                        SceneManager.changeScene("Menu");

                    }
                    passwortField.clear();

                }
        );

        registrieren.setOnAction(event -> {
            String user = userNameField.getText();
            String pin = passwortField.getText();
            if (onRegisterButtonClicked(user, pin)) {
                SceneManager.changeScene("Menu");
            }
            passwortField.clear();
        });


    }

    /**
     *
     *  Verarbeitet den Klick auf den Registrierungsbutton.
     *
     *  <p>Die eingegebenen Benutzerdaten werden überprüft.
     *  Wenn der Benutzername noch nicht vorhanden ist, wird ein
     *  neues Benutzerkonto erstellt und gespeichert.</p>
     *
     * @param username Eingegebener Benutzername
     * @param passwort Eingegebenes Passwort
     * @return Wurde die Registrierung erfolgreich durchgeführt?
     */
    public boolean onRegisterButtonClicked(String username, String passwort) {
        String rueckgabe;
        File personFile = new File("src/asset/person.txt");
        Dateizugriff james = new Dateizugriff();
        Map<String, Person> personMapAusDatei = james.lesenVielerPerson(personFile);
        if (username == null || username.isBlank()
                || passwort == null || passwort.isBlank()) {
            rueckgabe = "Benutzername und Passwort dürfen nicht leer sein.";
            meldung.setText(rueckgabe);
            return false;


        } else {
            Person neuePerson = new Person(username, passwort);
            if (personMapAusDatei.containsKey(username)) {
                rueckgabe = "Der Benutzer existiert bereits. Bitte wählen Sie einen neuen Benutzernamen.";
                meldung.setText(rueckgabe);
                return false;
            } else {
                james.speicherPerson(personFile, neuePerson);
                rueckgabe = "Die Registrierung war erfolgreich";
                meldung.setText(rueckgabe);
                istAngemeldet = true;
                return true;
            }


        }


    }

    /**
     *  Verarbeitet den Klick auf den Anmeldebutton.
     *  Die eingegebenen Benutzerdaten werden mit den gespeicherten
     *  Benutzerdaten verglichen. Bei erfolgreichen Zugangsdaten wird
     *  der Benutzer zum Hauptmenü weitergeleitet.
     * @param username Eingegebener Benutzername
     * @param passwort Eingegebenes Passwort
     * @return Wurde die Anmeldung erfolgreich durchgeführt?
     */
    public boolean onAnmeldenButtonClicked(String username, String passwort) {
        String rueckgabe;

        File personFile = new File("src/asset/person.txt");
        Dateizugriff james = new Dateizugriff();
        Map<String, Person> personMapAusDatei = james.lesenVielerPerson(personFile);
        if (username == null || username.isBlank()
                || passwort == null || passwort.isBlank()) {
            rueckgabe = "Benutzername und Passwort dürfen nicht leer sein.";
            meldung.setText(rueckgabe);
            return false;


        } else {
            Person neuePerson = new Person(username, passwort);
            if (personMapAusDatei.containsKey(username)) {
                Person ausMap = personMapAusDatei.get(username);
                if (neuePerson.getPassword().equals(ausMap.getPassword())) {
                    rueckgabe = "Die Eingabe ist korrekt.";
                    meldung.setText(rueckgabe);

                    istAngemeldet = true;
                    return true;
                } else {
                    rueckgabe = "Das Passwort ist falsch.";
                    meldung.setText(rueckgabe);

                    istAngemeldet = false;
                    return false;

                }

            } else {
                rueckgabe = "Der Benutzer existiert nicht. Registrieren Sie sich einfach.";
                meldung.setText(rueckgabe);
                return false;

            }


        }


    }

    /**
     * Gibt die JavaFX dieser Gewinnen Szene zurueck
     * @return Scene
     */
    public Scene getScene() {
        return scene;
    }


    @Override
    public void destroy() {

    }

    @Override
    public void render() {

    }

    public boolean istAngemeldet() {
        return istAngemeldet;
    }


}
