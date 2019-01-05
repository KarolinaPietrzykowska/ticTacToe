package pl.app.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

/** Klasa zawierająca logikę gry dla dwóch graczy
 * @author Marcin*/
public class Game
{
    /** Pole board jest tablicą składającą się z 9 obiektów klasy Player z początkową wartością
     *  null. W trakcie gry wartości null zostaną nadpisane przez obiekty klasy Player
     *  reprezentujące dwóch graczy.  */
    private Player[] board = { null, null, null, null, null, null, null, null, null };

    /** Pole reprezentujące aktualnego gracza */
    public Player currentPlayer;

    /** Getter */
    public Player[] getBoard()
    {
        return board;
    }

    /** Setter */
    public void setBoard(Player[] board)
    {
        this.board = board;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Game game = (Game) o;
        return Arrays.equals(board, game.board) && Objects.equals(currentPlayer, game.currentPlayer);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(currentPlayer);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }

    /** Metoda sprawdza w całej planszy trzy kolejne pola leżące w jednej linii
     * @return true gdy jeden z graczy wygra
     * @return false gdy nie ma jeszcze zwycięzcy */
    public boolean hasWinner()
    {
        return (board[0] != null && board[0] == board[1] && board[0] == board[2])
                || (board[3] != null && board[3] == board[4] && board[3] == board[5])
                || (board[6] != null && board[6] == board[7] && board[6] == board[8])
                || (board[0] != null && board[0] == board[3] && board[0] == board[6])
                || (board[1] != null && board[1] == board[4] && board[1] == board[7])
                || (board[2] != null && board[2] == board[5] && board[2] == board[8])
                || (board[0] != null && board[0] == board[4] && board[0] == board[8])
                || (board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }

    /** Metoda sprawdzająca, czy tablica board jest wypełniona
     *  @return true w momencie gdy w tablicy nie ma już więcej elementów o wartości null
     *  @return false w przeciwnym wypadku */
    public boolean boardFilledUp()
    {
        for (int i = 0; i < board.length; i++)
        {
            if (board[i] == null)
            {
                return false;
            }
        }
        return true;
    }

    /** Metoda sprawdza, czy ruch, który chce wykonać dany gracz jest dozwolony(tj. czy wartość
     * danego elementu tablicy board jest równa null oraz czy gracz jest graczem aktualnym
     * (currentPlayer). Jeśli ruch jest dozwolony, aktualizowany jest stan planszy, przeciwnik
     * staje się graczem aktualnym oraz otrzymuje informacje o ostatnim ruchu w celu aktualizacji
     * swojej planszy */
    public synchronized boolean legalMove(int location, Player player)
    {
        if (player == currentPlayer && board[location] == null)
        {
            board[location] = currentPlayer;
            currentPlayer = currentPlayer.opponent;
            currentPlayer.otherPlayerMoved(location);
            return true;
        }
        return false;
    }

    /** Klasa z zaimplementowaną obslugą wątków. Reprezentuje gracza i jego przeciwnika, którzy
     * są rozrożnialni za pomocą znaku 'X' albo 'O'. Do komunikacji został użyty socket ze
     * strumieniem wejściowym i wyjściowym. Ze względu na to, że komunikaty są tekstem, został
     * użyty reader i writer */
    public class Player extends Thread
    {
        char mark;
        Player opponent;
        Socket socket;
        BufferedReader input;
        public PrintWriter output;

        /** Konstruktor w kontekście danego socketu inicjalizuje pola strumieni wejściowego
         * i wyjściowego oraz wyświetla dwie powitalne wiadomości */
        public Player(Socket socket, char mark)
        {
            this.socket = socket;
            this.mark = mark;
            try
            {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println("WELCOME " + mark);
                output.println("MESSAGE Czekam na przeciwnika...");
            }
            catch (IOException e)
            {
                System.out.println("Gracz rozłączył się: " + e);
            }
        }

        /** Setter
         *  Metoda przypisuje przeciwnika dla danego gracza */
        public void setOpponent(Player opponent)
        {
            this.opponent = opponent;
        }

        /** Getter */
        public Player getOpponent()
        {
            return opponent;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }

            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            Player player = (Player) o;
            return mark == player.mark && Objects.equals(socket, player.socket);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(mark, socket);
        }

        /** Metoda kieruje do strumienia wyjściowego ruch przeciwnika */
        public void otherPlayerMoved(int location)
        {
            output.println("OPPONENT_MOVED " + location);
            output.println(hasWinner() ? "DEFEAT" : boardFilledUp() ? "TIE" : "");
        }

        /** Metoda wywołuje się we własnym wątku*/
        public void run()
        {
            try
            {
                /** Wątek zacznie się dopiero, gdy wszyscy gracze bedą mieli przeciwnika */
                output.println("MESSAGE Wszyscy gracze w komplecie");

                /** Przekazuje wiadomość przeciwnikowi, że teraz jest jego kolej */
                if (mark == 'X')
                {
                    output.println("MESSAGE Twój ruch");
                }

                /** Przechwytywanie wiadomości i ich przetwarzanie w kontekście gry */
                while (true)
                {
                	String command = input.readLine();

                	if (command == null)
                    {
                        throw new IOException();
                    }

                    if (command.startsWith("MOVE"))
                    {
                        int location = Integer.parseInt(command.substring(5));
                        if (legalMove(location, this))
                        {
                            output.println("VALID_MOVE");
                            output.println(hasWinner() ? "VICTORY" : boardFilledUp() ? "TIE" : "");
                        }

                        else
                        {
                            output.println("MESSAGE Proszę czekać\u0009");
                        }
                    }
                    else if (command.startsWith("QUIT"))
                    {
                        return;
                    }
                }
            }
            catch (IOException e)
            {
            	opponent.output.println("VICTORY");
            }

            finally
            {
                try
                {
                    socket.close();
                }

                catch (IOException e)
                {

                }
            }
        }
    }
}