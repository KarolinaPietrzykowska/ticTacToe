package pl.app.tests;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import pl.app.server.ServerStage;

import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;

/** Klasa testująca korzystająca z platformy JUnit. Służy do testowania klasy serwera ServerStage.
 *  @author Karolina */
public class ServerStageTest extends ApplicationTest
{
    private ServerStage serverStage;

    /** Nadpisana metoda z klasy ApplicationTest pozwalająca na stworzenie instancji klasy serverStage
     *  a tym samym na stworzenie aplikacji
     *  @param stage */
    @Override
    public void start(Stage stage)
    {
        serverStage = new ServerStage();
    }

    //Testy zainicjalizowanych pól przez metodę initializeControls wywołaną w konstruktorze

    //bpane
    /** Metoda sprawdza czy bpane ma padding zgodny z wartością zainicjowaną w konstruktorze klasy ServerStage */
    @Test
    public void hasBorderPaneCorrectPadding()
    {
        assertTrue(serverStage.getBpane().getPadding().equals(new Insets(10, 10, 10, 10)));
    }

    /** Metoda sprawdza czy bpane ma preferowaną wysokość zgodną z wartością zainicjowaną w konstruktorze klasy ServerStage */
    @Test
    public void hasBorderPaneCorrectPreferredHeight()
    {
        assertTrue(serverStage.getBpane().getPrefHeight() == 500);
    }

    /** Metoda sprawdza czy bpane ma preferowaną szerokość zgodną z wartością zainicjowaną w konstruktorze klasy
     * ServerStage */
    @Test
    public void hasBorderPaneCorrectPreferredWidth()
    {
        assertTrue(serverStage.getBpane().getPrefWidth() == 720);
    }

    /** Metoda sprawdza czy bpane ma ustawiony hbox zgodnie z wartością zainicjowaną w konstruktorze klasy
     *  ServerStage */
    @Test
    public void hasBorderPaneHBoxCentered()
    {
        assertSame(serverStage.getBpane().getCenter(), serverStage.getHbox());
    }

    /** Metoda sprawdza czy bpane ma ustawiony lblStatus zgodnie z wartością zainicjowaną w konstruktorze klasy
     *  ServerStage */
    @Test
    public void hasBorderPaneLblStatusBottom()
    {
        assertSame(serverStage.getBpane().getBottom(), serverStage.getLblStatus());
    }

    //hbox
    /** Metoda sprawdza czy hbox ma ustawiony spacing zgodnie z wartością zainicjowaną w konstruktorze klasy
     *  ServerStage */
    @Test
    public void hasHBoxCorrectSpacing()
    {
        assertTrue(serverStage.getHbox().getSpacing() == 10);
    }

    //txtPort
    /** Metoda sprawdza czy txtPort ma ustawioną etykietę zgodnie z wartością zainicjowaną w konstruktorze klasy
     *  ServerStage */
    @Test
    public void hasTxtPortCorrectText()
    {
        assertThat(serverStage.getTxtPort()).hasText("8911");
    }

    /** Metoda sprawdza czy txtPort ma ustawioną maksymalną wysokość zgodną z wartoącią zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasTxtPortCorrectMaxHeight()
    {
        assertTrue(serverStage.getTxtPort().getMaxHeight() == 40);
    }

    /** Metoda sprawdza czy txtPort ma ustawioną maksymalną szerokość zgodną z wartością zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasTxtPortCorrectMaxWidth()
    {
        assertTrue(serverStage.getTxtPort().getMaxWidth() == 70);
    }

    /** Metoda sprawdza czy txtPort ma ustawiony właściwy padding zgodny z wartością zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasTxtPortCorrectPadding()
    {
        assertTrue(serverStage.getTxtPort().getPadding().equals(new Insets(10, 10, 10, 10)));
    }

    //lblPort
    /** Metoda sprawdza czy lblPort ma ustawiony właściwą etykietę zgodną z wartością zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasLblPortCorrectText()
    {
       assertThat(serverStage.getLblPort()).hasText("Port");
    }

    /** Metoda sprawdza czy lblPort ma ustawiony właściwy kolor czcionki zgodny z wartością zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasLblPortCorrectTextFill()
    {
        assertTrue(serverStage.getLblPort().getTextFill().equals(Color.RED));
    }

    //lblStatus
    /** Metoda sprawdza czy lblStatus ma ustawiony właściwy kolor czcionki zgodny z wartością zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasLblStatusCorrectTextFill()
    {
        assertTrue(serverStage.getLblStatus().getTextFill().equals(Color.RED));
    }

    //btnStart
    /** Metoda sprawdza czy btnStart ma ustawioną właściwą etykietę zgodną z wartością zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasBtnStartCorrectText()
    {
        assertTrue(serverStage.getBtnStart().getText().contentEquals("Start"));
    }

    /** Metoda sprawdza czy btnStart ma ustawionś właściwą maksymalną wysokość zgodną z wartością zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasBtnStartCorrectMaxHeight()
    {
        assertTrue(serverStage.getBtnStart().getMaxHeight() == 40);
    }

    /** Metoda sprawdza czy btnStart ma ustawioną właściwą maksymalną szerokość zgodną z wartością zainicjowaną w konstruktorze
     *  klasy ServerStage */
    @Test
    public void hasBtnStartCorrectMaxWidth()
    {
        assertTrue(serverStage.getBtnStart().getMaxWidth() == 100);
    }
}
