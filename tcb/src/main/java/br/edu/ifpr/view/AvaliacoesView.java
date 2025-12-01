package br.edu.ifpr.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class AvaliacoesView extends JPanel {
    private final DefaultTableModel tableModel;

    public AvaliacoesView() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        JLabel title = new JLabel("Gerenciar Avaliações", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Item (obra/expo)", "Usuário", "Nota", "Comentário"}, 0);
        JTable tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Adicionar Avaliação");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        actions.add(btnAdd);
        actions.add(btnEdit);
        actions.add(btnDelete);
        add(actions, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            String item = JOptionPane.showInputDialog(this, "Item (obra/exposição):");
            String usuario = JOptionPane.showInputDialog(this, "Usuário:");
            String notaStr = JOptionPane.showInputDialog(this, "Nota (1-5):");
            String coment = JOptionPane.showInputDialog(this, "Comentário:");

            try {
                int nota = Integer.parseInt(notaStr);
                if (nota < 1 || nota > 5) throw new NumberFormatException();
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1, item, usuario, nota, coment});
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nota inválida. Use número entre 1 e 5.");
            }
        });

        btnEdit.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel >= 0) {
                String novoComent = JOptionPane.showInputDialog(this, "Editar comentário:", tableModel.getValueAt(sel, 4));
                if (novoComent != null) tableModel.setValueAt(novoComent, sel, 4);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma avaliação para editar.");
            }
        });

        btnDelete.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel >= 0) {
                if (JOptionPane.showConfirmDialog(this, "Excluir avaliação?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(sel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma avaliação para excluir.");
            }
        });
    }
}
