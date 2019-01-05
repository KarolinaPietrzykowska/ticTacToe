package pl.app.client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Klasa odpowiedzialna za stworzenie wyglądu oraz działanie aplikacji klienckiej
 *
 * @author Marcin
 */
public class Client
{
    private JFrame frame = new JFrame("Kółko i Krzyżyk");
    private JLabel messageLabel = new JLabel("");

    private Square[] board = new Square[9];
    private Square currentSquare;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private static Player player;
    private static Player oponent;
    private ImageIcon playerImage;
    private String titleOfFrame;

    private final String msgMove = "Twój Ruch";

    private static String serverAddress;
    private static int port = 0;

    public String getServerAddress()
    {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress)
    {
        this.serverAddress = serverAddress;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    /**
     * Konstruktor, tworzy plansze do gry, przypisuje słuchaczy myszy do każdego z pól
     */
    public Client(String serverAddress, int port)
    {
        this.serverAddress = serverAddress;
        this.port = port;

        try
        {
            socket = new Socket(serverAddress, port);
        }

        catch (ConnectException e)
        {
            errorConnHandle(e);
        }

        catch (UnknownHostException e)
        {
            errorConnHandle(e);
        }

        catch (IOException e)
        {
            errorConnHandle(e);
        }

        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        catch (IOException e)
        {
            errorConnHandle(e);
        }

        try
        {
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        catch (IOException e1)
        {
            e1.printStackTrace();
        }

        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, "South");

        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(3, 3, 2, 2));
        for (int i = 0; i < board.length; i++)
        {
            final int j = i;
            board[i] = new Square();
            board[i].addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    currentSquare = board[j];
                    out.println("MOVE " + j);

                    try
                    {
                        if (messageLabel.getText() == msgMove)
                        {
                            frame.requestFocus();
                            currentSquare.requestFocus();
                            currentSquare.setMove(player);
                            currentSquare.repaint();
                            frame.repaint();
                        }
                    }

                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            });
            boardPanel.add(board[i]);
        }

        frame.getContentPane().add(boardPanel, "Center");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Metoda zwraca obrazek przypisany do gracza
     */
    private ImageIcon getPlayerImage(Player player) throws IOException
    {
        if (player.getPlayerName() == "X")
        {
            playerImage = new ImageIcon(ImageIO.read(getClass().getResource("/resources/ps4.png")));
        }

        else
        {
            playerImage = new ImageIcon(ImageIO.read(getClass().getResource("/resources/xbox.png")));
        }

        return playerImage;
    }

    /**
     * Metoda wywoływana w przypadku braku połączenia z serwerem
     */
    private void errorConnHandle(Exception e)
    {
        JOptionPane.showMessageDialog(null, "Wygląda, że nie mamy połączenia z Serwerem :( \n" + e.getMessage());
        System.exit(1);
    }

    /**
     * Metoda zarządzająca grą, ustawia odpowiednie obrazki na planszy i komunikaty w zależności od ruchu
     */
    public void play() throws Exception
    {
        String response;
        try
        {
            response = in.readLine();
            if (response.startsWith("WELCOME"))
            {
                char mark = response.charAt(8);

                if (mark == 'X')
                {
                    player = new Player("X");
                    oponent = new Player("O");
                    titleOfFrame = "playstation4";
                    frame.setIconImage(ImageIO.read(getClass().getResource("/resources/ps4.png")));
                }

                else
                {
                    player = new Player("O");
                    oponent = new Player("X");
                    titleOfFrame = "xbox";
                    frame.setIconImage(ImageIO.read(getClass().getResource("/resources/xbox.png")));
                }

                getPlayerImage(player);
                frame.setTitle("Gracz " + titleOfFrame);
            }

            while (true)
            {
                response = in.readLine();
                if (response.startsWith("VALID_MOVE"))
                {
                    messageLabel.setText("Proszę czekać");
                    currentSquare.setMove(player);
                }

                else if (response.startsWith("OPPONENT_MOVED"))
                {
                    int loc = Integer.parseInt(response.substring(15));
                    board[loc].setMove(oponent);
                    messageLabel.setText(msgMove);
                    board[loc].repaint();
                }
                else if (response.startsWith("VICTORY"))
                {
                    messageLabel.setText("Wygrałeś!");
                    break;
                }
                else if (response.startsWith("DEFEAT"))
                {
                    messageLabel.setText("Przegrałeś :(");
                    break;
                }
                else if (response.startsWith("TIE"))
                {
                    messageLabel.setText("Remis!");
                    break;
                }
                else if (response.startsWith("MESSAGE"))
                {
                    messageLabel.setText(response.substring(8));
                }
            }
            out.println("QUIT");
        }
        finally
        {
            socket.close();
        }
    }

    /**
     * Metoda wywołująca okno dialogowe określające udział w ponownej rozgrywce
     */
    boolean wantsToPlayAgain()
    {
        int response = JOptionPane.showConfirmDialog(frame, "Koniec rozgrywki. Chcesz zagrać ponownie?",
                "Kółko i Krzyżyk", JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }

    /**
     * Klasa dodająca obrazki oraz ustawiająca kolor tła, reprezentuje pojedynczy kwadrat planszy do gry, który dziedziczy
     * po JButtonie
     */
    class Square extends JButton
    {
        JLabel label = new JLabel();

        public Square()
        {
            setBackground(Color.GRAY);
            add(label);
        }

        public void setMove(Player player) throws IOException
        {
            label.setIcon(getPlayerImage(player));
            add(label);
        }
    }

    /**
     * Klasa prywatna umożliwiająca ustawienie nazwy Gracza, zapewnia również dostęp do pola klasy poprzez getter
     */
    private class Player
    {
        private String playerName;

        Player(String playerName)
        {
            this.playerName = playerName;
        }

        public String getPlayerName()
        {
            return playerName;
        }

        public void setPlayerName(String playerName)
        {
            this.playerName = playerName;
        }
    }
}