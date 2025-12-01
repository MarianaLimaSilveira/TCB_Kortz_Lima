package br.edu.ifpr.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class ExposicoesView extends JPanel {
    private final DefaultTableModel tableModel;

    public ExposicoesView() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        JLabel title = new JLabel("CRUD - Exposições", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Tema", "Data Início", "Data Fim"}, 0);
        JTable tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnViewArtworks = new JButton("Ver Obras Vinculadas");

        actions.add(btnAdd);
        actions.add(btnEdit);
        actions.add(btnDelete);
        actions.add(btnViewArtworks);
        add(actions, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome da exposição:");
            if (nome != null && !nome.trim().isEmpty()) {
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1, nome, "Tema X", "2025-01-01", "2025-02-01"});
            }
        });

        btnViewArtworks.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrir lista de obras vinculadas (implementar).");
        });
    }
}
