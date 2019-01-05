package pl.app.client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/** Klasa tworząca okno dialogowe, które umożliwia wprowadzenie adresu i portu a następnie podłączenie do
 *  serwera aplikacji klienckiej
 *  @author Marcin */
public class SetUp extends JDialog
{
    private JPanel jPanel;
    private JButton btnOK;
    private JLabel lblHost;
    private JLabel lblPort;
    private JTextField txtHost;
    private Image ico;

    private String serverAddress;
    private int port;
    private JTextField txtPort;

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

    /** Konstruktor, tworzy wygląd okna, dodaje słuchacza i obsługuje zdarzenie kliknięcia */
    public SetUp() throws IOException
    {
        ico = ImageIO.read(getClass().getResource("/resources/mainIcon.png"));
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        jPanel = new JPanel(gridBagLayout);

        btnOK = new JButton("OK");
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel.add(btnOK, gridBagConstraints);

        lblHost = new JLabel("Adres:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel.add(lblHost, gridBagConstraints);

        lblPort = new JLabel("Port:");
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel.add(lblPort, gridBagConstraints);

        txtHost = new JTextField();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel.add(txtHost, gridBagConstraints);

        txtPort = new JTextField();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel.add(txtPort, gridBagConstraints);

        lblHost.setPreferredSize(new Dimension(50, 30));
        lblHost.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPort.setPreferredSize(new Dimension(40, 30));
        lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
        txtHost.setPreferredSize(new Dimension(100, 30));
        txtPort.setPreferredSize(new Dimension(80, 30));

        this.setTitle("Podłączenie do serwera gier");
        this.setModal(true);
        this.getRootPane().setDefaultButton(btnOK);
        this.setPreferredSize(new Dimension(390, 90));
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.add(jPanel);

        btnOK.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (txtHost.getText().isEmpty() | txtPort.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Adres serwera i port musi być podany");
                }

                else if (!(Integer.parseInt(txtPort.getText()) >= 2000 && Integer.parseInt(txtPort.getText()) <= 9000))
                {
                    JOptionPane.showMessageDialog(null, "Port musi być z zakresu 2000-9000");
                }

                else
                {
                    setServerAddress(txtHost.getText());
                    setPort(Integer.parseInt(txtPort.getText()));
                    dispose();
                }
            }
        });

        this.setIconImage(ico);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}