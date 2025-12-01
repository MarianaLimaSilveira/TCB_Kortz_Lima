package br.edu.ifpr.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class CategoriasView extends JPanel {
    private final DefaultTableModel tableModel;

    public CategoriasView() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        JLabel title = new JLabel("CRUD - Categorias", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Aprovada"}, 0);
        JTable tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRequest = new JButton("Solicitar Nova Categoria");
        JButton btnApprove = new JButton("Aprovar Selecionada (moderador)");

        actions.add(btnRequest);
        actions.add(btnApprove);
        add(actions, BorderLayout.SOUTH);

        btnRequest.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome da nova categoria:");
            if (nome != null && !nome.trim().isEmpty()) {
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1, nome, "Solicitada por usuário", "Pendente"});
                JOptionPane.showMessageDialog(this, "Categoria solicitada. Aguardar aprovação dos moderadores.");
            }
        });

        btnApprove.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel >= 0) {
                tableModel.setValueAt("Aprovada", sel, 3);
                JOptionPane.showMessageDialog(this, "Categoria aprovada (simulação).");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria pendente para aprovar.");
            }
        });
    }
}
