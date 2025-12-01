package br.edu.ifpr.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Sistema de Exposição de Arte Digital");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // menu lateral
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(0, 1, 6, 6));
        menu.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton btnUsuarios = new JButton("Gerenciar Usuários");
        JButton btnObras = new JButton("Gerenciar Obras");
        JButton btnExposicoes = new JButton("Gerenciar Exposições");
        JButton btnCategorias = new JButton("Gerenciar Categorias");
        JButton btnAvaliacoes = new JButton("Gerenciar Avaliações");

        menu.add(btnUsuarios);
        menu.add(btnObras);
        menu.add(btnExposicoes);
        menu.add(btnCategorias);
        menu.add(btnAvaliacoes);

        // painel de conteúdo (onde as views são trocadas)
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        add(menu, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        // ações dos botões: trocam o painel central
        btnUsuarios.addActionListener(e -> {
            content.removeAll();
            content.add(new UsuariosView(), BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
        });

        btnObras.addActionListener(e -> {
            content.removeAll();
            content.add(new ObrasView(), BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
        });

        btnExposicoes.addActionListener(e -> {
            content.removeAll();
            content.add(new ExposicoesView(), BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
        });

        btnCategorias.addActionListener(e -> {
            content.removeAll();
            content.add(new CategoriasView(), BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
        });

        btnAvaliacoes.addActionListener(e -> {
            content.removeAll();
            content.add(new AvaliacoesView(), BorderLayout.CENTER);
            content.revalidate();
            content.repaint();
        });

        // tela inicial
        content.add(new JLabel("<html><h1 style='text-align:center'>Bem-vinda(o) — Sistema de Exposição de Arte Digital</h1><p style='text-align:center'>Escolha uma opção no menu à esquerda.</p></html>", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
