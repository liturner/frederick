package turnertech.frederick.gui.etb;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

    public MultiLineCellRenderer() {
      setLineWrap(true);
      setWrapStyleWord(true);
      setOpaque(true);
      setAlignmentY(TOP_ALIGNMENT);
      setMargin(new java.awt.Insets(0, 0, 0, 0));
      
    }
  
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if (table == null) {
        return this;
      }

      Color fg = null;
      Color bg = null;

      JTable.DropLocation dropLocation = table.getDropLocation();
      if (dropLocation != null
              && !dropLocation.isInsertRow()
              && !dropLocation.isInsertColumn()
              && dropLocation.getRow() == row
              && dropLocation.getColumn() == column) {

          fg = UIManager.getColor("Table.dropCellForeground");
          bg = UIManager.getColor("Table.dropCellBackground");

          isSelected = true;
      }

      if (isSelected) {
          super.setForeground(fg == null ? table.getSelectionForeground() : fg);
          super.setBackground(bg == null ? table.getSelectionBackground() : bg);
      } else {
          Color background =  table.getBackground();
          if (background == null || background instanceof javax.swing.plaf.UIResource) {
              Color alternateColor = UIManager.getColor("Table.alternateRowColor");
              if (alternateColor != null && row % 2 != 0) {
                  background = alternateColor;
              }
          }
          super.setForeground(table.getForeground());
          super.setBackground(background);
      }

      setFont(table.getFont());

      if (hasFocus) {
          Border border = null;
          if (isSelected) {
              border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
          }
          if (border == null) {
              border = UIManager.getBorder("Table.focusCellHighlightBorder");
          }
          setBorder(border);

          if (!isSelected && table.isCellEditable(row, column)) {
              Color col;
              col = UIManager.getColor("Table.focusCellForeground");
              if (col != null) {
                  super.setForeground(col);
              }
              col = UIManager.getColor("Table.focusCellBackground");
              if (col != null) {
                  super.setBackground(col);
              }
          }
      } else {
          setBorder(new EmptyBorder(0, 1, 0, 1));
      }

      setText((value == null) ? "" : value.toString());

      if(table.getModel() instanceof MultiLineTableModel) {
        table.setRowHeight(row, table.getRowHeight() * ((MultiLineTableModel)table.getModel()).getRowLineCount(row));
      }

      return this;
    }
  }