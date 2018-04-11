package gui.view.childview.cell;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class GenderCellEditor extends DefaultCellEditor implements TableCellEditor, TableCellRenderer {

    public GenderCellEditor() {
        super(new JCheckBox("", true));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JCheckBox checkbox = (JCheckBox)getComponent();
        checkbox.setSelected((boolean)value);
        return checkbox;
    }

    @Override
    public Object getCellEditorValue() {
        return  ((JCheckBox)getComponent()).isSelected();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JCheckBox checkbox = (JCheckBox)getComponent();
        checkbox.setSelected((boolean)value);
        return checkbox;
    }
}
