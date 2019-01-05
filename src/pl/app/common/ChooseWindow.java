package pl.app.common;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/** Klasa konstruująca okno wyboru pomiędzy trybem klienta a trybem serwera
 * @author Marcin*/
public class ChooseWindow extends JDialog
{
    private JFrame frame;
    private JPanel jPane;
    private JButton btnClient;
    private JButton btnServer;
    private JLabel info;
    /** Pole pozwalające okreslić, czy uruchamiamy aplikację w trybie klienta czy serwera */
    private String mode;
    private Image background;
    private Image ico;

    /** getter
     * @return  mode*/
    public String getMode()
    {
        return mode;
    }

    /** setter
     * @param mode*/
    public void setMode(String mode)
    {
        this.mode = mode;
    }

    /** Metoda konstruująca okno aplikacji
     * @throws  IOException */
    public ChooseWindow() throws IOException
    {
        background = ImageIO.read(getClass().getResource("/resources/background.jpg"));
        ico = ImageIO.read(getClass().getResource("/resources/mainIcon.png"));

        frame = new JFrame();
        setTitle("Kółko i krzyżyk");

        jPane = new JPanel()
        {
            public void paintComponent(Graphics g)
            {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jPane.setLayout(gridBagLayout);

        Font fontLabel = new Font("Verdana", Font.BOLD, 30);
        Font fontButton = new Font("Verdana", Font.PLAIN, 20);
        info = new JLabel("Wybierz tryb gry: ");
        info.setFont(fontLabel);
        info.setForeground(Color.DARK_GRAY);

        btnClient = new JButton("Klient");
        btnClient.setFont(fontButton);
        btnServer = new JButton("Serwer");
        btnServer.setFont(fontButton);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPane.add(info, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 50, 5, 5);
        jPane.add(btnClient, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 50);
        jPane.add(btnServer, gridBagConstraints);

        btnClient.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setMode("Client");
                dispose();
            }
        });

        btnServer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setMode("Server");
                dispose();
            }
        });

        setIconImage(ico);
        getContentPane().add(jPane, "Center");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 300));
        setLocation(500,200);
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
