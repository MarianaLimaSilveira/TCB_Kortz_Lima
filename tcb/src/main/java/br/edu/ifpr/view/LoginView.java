package br.edu.ifpr.view;

import br.edu.ifpr.controller.UserController;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private UserController controller;

    public LoginView() {

        controller = new UserController();

        setTitle("Entrar - Galeria Digital");
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel central estilo card
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Título
        JLabel title = new JLabel("Galeria Digital", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Aprecie • Crie • Exponha", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(new Color(100, 100, 100));

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(20));

        // Campo email
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(250, 30));

        // Campo senha
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 30));

        card.add(lblEmail);
        card.add(emailField);
        card.add(Box.createVerticalStrut(10));
        card.add(lblSenha);
        card.add(passwordField);
        card.add(Box.createVerticalStrut(20));

        // Botão login estilizado
        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(50, 120, 220));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(e -> fazerLogin());
        card.add(btnLogin);

        add(card, BorderLayout.CENTER);
    }

    private void fazerLogin() {
        String email = emailField.getText().trim();
        String senha = new String(passwordField.getPassword());

        if (controller.login(email, senha)) {
            new MainDashboardView().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciais inválidas.");
        }
    }

    public static void main(String[] args) {
        new LoginView().setVisible(true);
    }
}

