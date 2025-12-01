package br.edu.ifpr.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainDashboardView extends JFrame {

    private JPanel feedPanel;

    public MainDashboardView() {
        setTitle("Galeria Digital");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // ░░░ Barra superior estilo rede social ░░░
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel logo = new JLabel("Galeria Digital");
        logo.setFont(new Font("SansSerif", Font.BOLD, 22));
        topBar.add(logo, BorderLayout.WEST);

        JPanel menu = new JPanel();
        menu.setBackground(Color.WHITE);

        String[] itens = {"Explorar", "Minhas Obras", "Exposições", "Perfil", "Ranking"};
        for (String s : itens) {
            JButton b = new JButton(s);
            b.setFocusPainted(false);
            b.setBackground(new Color(240, 240, 240));
            b.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            menu.add(b);
        }

        topBar.add(menu, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // ░░░ Feed estilo Google Arts ░░░
        feedPanel = new JPanel();
        feedPanel.setLayout(new GridLayout(0, 3, 20, 20));
        feedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        feedPanel.setBackground(new Color(245, 245, 245));

        add(new JScrollPane(feedPanel), BorderLayout.CENTER);

        carregarMockDeObras(); // trocamos depois por DAO real
    }

    // Exemplo temporário de cards para visual (depois puxamos do banco)
    private void carregarMockDeObras() {
        adicionarCard(
                "Amanhecer Neon",
                "https://i.imgur.com/AKJ5c4r.jpeg",
                "Marina K."
        );

        adicionarCard(
                "Estrutura Fragmentada",
                "https://i.imgur.com/z1xZ9Yt.jpeg",
                "Yuri S."
        );

        adicionarCard(
                "Orbe Digital",
                "https://i.imgur.com/rpN4ytR.jpeg",
                "Ana Clara"
        );
    }

    // Criar card moderno
    private void adicionarCard(String titulo, String imagemUrl, String artista) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Imagem da internet
        try {
            URL url = new URL(imagemUrl);
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            card.add(imgLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            card.add(new JLabel("Erro ao carregar imagem"));
        }

        // Infos
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel lblArtista = new JLabel("por " + artista);
        lblArtista.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblArtista.setForeground(new Color(120, 120, 120));

        info.add(lblTitulo);
        info.add(lblArtista);

        card.add(info, BorderLayout.SOUTH);

        feedPanel.add(card);
        feedPanel.revalidate();
        feedPanel.repaint();
    }
}
