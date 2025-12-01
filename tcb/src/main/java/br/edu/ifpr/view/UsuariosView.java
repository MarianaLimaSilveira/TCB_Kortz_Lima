package br.edu.ifpr.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class UsuariosView extends JPanel {
    private final DefaultTableModel tableModel;

    public UsuariosView() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        JLabel title = new JLabel("CRUD - Usuários", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        add(title, BorderLayout.NORTH);

        // tabela simples com cabeçalho
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Email", "Tipo"}, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // painel de botões
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnRefresh = new JButton("Refresh (carregar do BD)");

        actions.add(btnAdd);
        actions.add(btnEdit);
        actions.add(btnDelete);
        actions.add(btnRefresh);
        add(actions, BorderLayout.SOUTH);

        // ações (provisórias — troque para chamar controller/dao)
        btnAdd.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome do usuário:");
            if (nome != null && !nome.trim().isEmpty()) {
                // aqui você chamaria o controller -> dao para persistir
                // por ora adiciona na tabela temporariamente
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1, nome, nome.toLowerCase().replace(" ", ".")+"@exemplo.com", "user"});
            }
        });

        btnEdit.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel >= 0) {
                String novo = JOptionPane.showInputDialog(this, "Novo nome:", tableModel.getValueAt(sel, 1));
                if (novo != null) tableModel.setValueAt(novo, sel, 1);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
            }
        });

        btnDelete.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel >= 0) {
                int ok = JOptionPane.showConfirmDialog(this, "Excluir usuário selecionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (ok == JOptionPane.YES_OPTION) tableModel.removeRow(sel);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.");
            }
        });

        btnRefresh.addActionListener(e -> {
            // Aqui -> controller.buscarTodosUsuarios() e popular a tableModel
            JOptionPane.showMessageDialog(this, "Refresh chamado (implemente a chamada ao DAO).");
        });
    }
}
