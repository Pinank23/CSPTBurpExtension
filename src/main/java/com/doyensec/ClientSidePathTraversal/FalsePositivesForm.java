package com.doyensec.ClientSidePathTraversal;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FalsePositivesForm {
    private JPanel contentPanel;
    private JTable falsePositivesTable;
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Param Name", "URL (RegExp)"}, 0);

    public FalsePositivesForm(ClientSidePathTraversal cspt) {
        $$$setupUI$$$();
        display(cspt);
    }

    public void display(ClientSidePathTraversal cspt) {
        falsePositivesTable.setModel(model);
        displayTable(cspt.getFalsePositivesList());
        createContextualMenus(cspt);
    }

    private void displayTable(Map<String, Set<String>> falsePositivesList) {
        assert falsePositivesList != null;
        for (var paramName : falsePositivesList.keySet()) {
            for (var url : falsePositivesList.get(paramName)) {
                model.addRow(new String[]{paramName, url});
            }
        }
    }

    private void createContextualMenus(ClientSidePathTraversal cspt) {
        final JPopupMenu popupMenu = new JPopupMenu();
        falsePositivesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = falsePositivesTable.rowAtPoint(e.getPoint());
                if (r >= 0 && r < falsePositivesTable.getRowCount()) {
                    falsePositivesTable.setRowSelectionInterval(r, r);
                } else {
                    falsePositivesTable.clearSelection();
                }

                int rowindex = falsePositivesTable.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }

            }
        });

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(e -> {
            Component c = (Component) e.getSource();
            JPopupMenu popup = (JPopupMenu) c.getParent();
            JTable table = (JTable) popup.getInvoker();
            cspt.removeFalsePositive(table.getValueAt(table.getSelectedRow(), 0).toString(),
                    table.getValueAt(table.getSelectedRow(), 1).toString());
        });
        popupMenu.add(deleteItem);
        falsePositivesTable.setComponentPopupMenu(popupMenu);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        falsePositivesTable = new JTable();
        scrollPane1.setViewportView(falsePositivesTable);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
