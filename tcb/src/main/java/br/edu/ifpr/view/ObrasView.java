package br.edu.ifpr.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

class ObrasView extends JPanel {
    private final DefaultTableModel tableModel;

    public ObrasView() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        JLabel title = new JLabel("CRUD - Obras Digitais", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(18f));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Título", "Artista", "Categoria"}, 0);
        JTable tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnLinkExhibition = new JButton("Vincular a Exposição");

        actions.add(btnAdd);
        actions.add(btnEdit);
        actions.add(btnDelete);
        actions.add(btnLinkExhibition);
        add(actions, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> {
            String titulo = JOptionPane.showInputDialog(this, "Título da obra:");
            if (titulo != null && !titulo.trim().isEmpty()) {
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1, titulo, "Artista A", "Categoria X"});
            }
        });

        btnLinkExhibition.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Vincular obra a exposição (implementar diálogo/select de exposições).");
        });

        btnEdit.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel >= 0) {
                String novo = JOptionPane.showInputDialog(this, "Novo título:", tableModel.getValueAt(sel, 1));
                if (novo != null) tableModel.setValueAt(novo, sel, 1);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma obra para editar.");
            }
        });

        btnDelete.addActionListener(e -> {
            int sel = tabela.getSelectedRow();
            if (sel >= 0) {
                if (JOptionPane.showConfirmDialog(this, "Excluir obra?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(sel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma obra para excluir.");
            }
        });
    }
}
