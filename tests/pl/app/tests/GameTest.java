package pl.app.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.app.server.Game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Klasa testująca korzystająca z platformy JUnit. Służy do testowania klasy serwera Game.
 * @author Karolina
 */
public class GameTest
{
    private static Game game;
    private static int port;
    private static char markx;
    private static char marko;
    private static Socket socketx;
    private static Socket socketo;
    private static Game.Player playerx;
    private static Game.Player playero;

    /**
     * Metoda uruchamiana przed pozostałymi metodami testującymi w tej klasie
     * @throws IOException*/
    @BeforeClass
    public static void initialization() throws IOException
    {
        port = 8911;
        ServerSocket serverSocket = new ServerSocket(port);
        game = new Game();
        markx = 'x';
        marko = 'o';
        socketx = new Socket(InetAddress.getLocalHost(), port);
        socketo = new Socket(InetAddress.getLocalHost(), port);
        playerx = game.new Player(socketx, markx);
        playero = game.new Player(socketo, marko);
    }

    /**
     * Metoda sprawdza czy metoda hasWinner() z klasy Game zwróci wartość false, kiedy pole Board będzie wypełnione
     * wartością null
     */
    @Test
    public void hasWinnerReturnFalseWhenArrayIsNullTest()
    {
        assertFalse(game.hasWinner());
    }

    /**
     * Metoda sprawdza czy metoda hasWinner() z klasy Game zwróci wartość false, kiedy pole Board będzie wypełnione
     * ale nie będzie zwycięzcy
     *
     * @throws IOException
     */
    @Test
    public void hasWinnerReturnFalseWhenArrayIsNotNullButThereIsNoWinner() throws IOException
    {
        Game.Player[] board = {playero, playerx, playerx, playerx, playerx, playero, playero, playero, playerx};
        game.setBoard(board);
        assertFalse(game.hasWinner());
    }

    /**
     * Metoda sprawdza czy metoda hasWinner() z klasy Game zwróci wartość true, kiedy pole Board będzie wypełnione
     * i będzie zwycięzca
     */
    @Test
    public void hasWinnerReturnTrueWhenThereIsAWinner()
    {
        Game.Player[] board = {playero, playerx, playero, playerx, playero, playerx, playero, playerx, playero};
        game.setBoard(board);
        assertTrue(game.hasWinner());
    }

    /**
     * Metoda sprawdza czy metoda boardFilledUp() z klasy Game zwróci wartość false gdy pole Board będzie wypełnione
     * wartością null
     */
    @Test
    public void isBoardFilledUpWhenArrayIsNull()
    {
        assertFalse(game.boardFilledUp());
    }

    /**
     * Metoda sprawdza czy metoda boardFilledUp() z klasy Game zwróci wartość true gdy pole Board będzie wypełnione
     *  @throws IOException
     * */
    @Test
    public void isBoardFilledUpWhenArrayIsNotNull() throws IOException
    {
        Game.Player[] board = {playero, playerx, playero, playerx, playero, playerx, playero, playerx, playero};
        game.setBoard(board);
        assertTrue(game.boardFilledUp());
    }

    /**
     * Metoda sprawdza czy metoda boardFilledUp() z klasy Game zwróci wartość false gdy pole Board będzie wypełnione
     * tylko po części
     * @throws IOException
     */
    @Test
    public void isBoardFilledUpWhenArrayIsNotFullCompletely() throws IOException
    {
        Game.Player[] board = {playero, playerx, playero, playerx, playero, playerx, null, null, null};
        game.setBoard(board);
        assertFalse(game.boardFilledUp());
    }

    /**
     * Metoda sprawdza, czy metoda legalMove() z klasy Game zwróci wartość false, gdy nie będzie możliwy ruch ze
     * względu na położenie
     * @throws IOException
     */
    @Test
    public void isLegalMoveWhenThereIsNotALegalMoveBecauseOfLocation() throws IOException
    {
        //given
        Game.Player[] board = {playero, playerx, playero, playerx, playero, playerx, null, null, null};
        game.setBoard(board);

        //when
        game.currentPlayer = playero;
        int location = 1;

        //then
        assertFalse(game.legalMove(location, playero));
    }

    /**
     * Metoda sprawdza, czy metoda legalMove() z klasy Game zwróci wartość false, gdy nie będzie możliwy ruch ze
     * względu na aktualnego gracza
     * @throws IOException
     */
    @Test
    public void isLegalMoveWhenThereIsNotALegalMoveBecauseOfCurrentPlayer() throws IOException
    {
        //given
        Game.Player[] board = {playero, playerx, playero, playerx, playero, playerx, null, null, null};
        game.setBoard(board);

        //when
        game.currentPlayer = playero;
        int location = 6;

        //then
        assertFalse(game.legalMove(location, playerx));
    }

    /**
     * Metoda sprawdza, czy metoda legalMove() z klasy Game zwróci wartość false, gdy nie będzie możliwy ruch ze
     * względu na położenie i  aktualnego gracza
     * @throws IOException
     */
    @Test
    public void isLegalMoveWhenThereIsNotALegalMoveBecauseOfCurrentPlayerAndLocation() throws IOException
    {
        //given
        Game.Player[] board = {playero, playerx, playero, playerx, playero, playerx, null, null, null};
        game.setBoard(board);

        //when
        game.currentPlayer = playero;
        int location = 0;

        //then
        assertFalse(game.legalMove(location, playerx));
    }

    /**
     * Metoda sprawdza poprawność działania settera setBoard z klasy Game
     * @throws IOException
     */
    @Test
    public void setterBoardTest() throws IOException
    {
        //Given
        Game.Player playerx = game.new Player(socketx, markx); //tworzenie obiektu Playera
        Game.Player[] board = {playerx, playerx, playerx, playerx, playerx, playerx, playerx, playerx, playerx};

        //When
        game.setBoard(board);

        //Then
        assertTrue(game.getBoard().equals(board));
    }

    /**
     * Metoda sprawdza poprawność działania konstruktora z klasy Game.Player
     * @throws IOException
     */
    @Test
    public void playerConstructorTest() throws IOException
    {
        Game.Player playerExpected = game.new Player(socketx, markx);
        Game.Player playerx = game.new Player(socketx, markx);
        assertTrue(playerExpected.equals(playerx));
    }

    /**
     * Metoda sprawdza poprawność działania settera setOpponent() z klasy Game.Player
     * @throws IOException
     */
    @Test
    public void setOpponentTest() throws IOException
    {
        playerx.setOpponent(playero);
        assertTrue(playerx.getOpponent().equals(playero));
    }

    /** Metoda wywołana po każdej metodzie testującej w tej klasie */
    @After
    public void setBoardNullAgain()
    {
        Game.Player[] board = {null, null, null, null, null, null, null, null, null};
        game.setBoard(board);
    }

    /** Metoda wywołana po wszystkich metodach testujących w tej klasie */
    @AfterClass
    public static void closeSockets() throws IOException
    {
        socketo.close();
        socketx.close();
    }
}
