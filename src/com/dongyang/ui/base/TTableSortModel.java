package com.dongyang.ui.base;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TTableSortModel extends DefaultTableModel{
	
	 /**
     *  Constructs a default <code>TTableModel</code>
     *  which is a table of zero columns and zero rows.
     */
    public TTableSortModel() {
        this(0, 0);
    }

    /**
     *  Constructs a <code>TTableModel</code> with
     *  <code>rowCount</code> and <code>columnCount</code> of
     *  <code>null</code> object values.
     *
     * @param rowCount           the number of rows the table holds
     * @param columnCount        the number of columns the table holds
     *
     * @see #setValueAt
     */
    public TTableSortModel(int rowCount, int columnCount) {
        super(rowCount,columnCount);
    }

    /**
     *  Constructs a <code>TTableModel</code> with as many columns
     *  as there are elements in <code>columnNames</code>
     *  and <code>rowCount</code> of <code>null</code>
     *  object values.  Each column's name will be taken from
     *  the <code>columnNames</code> vector.
     *
     * @param columnNames       <code>vector</code> containing the names
     *				of the new columns; if this is
     *                          <code>null</code> then the model has no columns
     * @param rowCount           the number of rows the table holds
     * @see #setDataVector
     * @see #setValueAt
     */
    public TTableSortModel(Vector columnNames, int rowCount) {
        super(columnNames,rowCount);
    }

    /**
     *  Constructs a <code>TTableModel</code> with as many
     *  columns as there are elements in <code>columnNames</code>
     *  and <code>rowCount</code> of <code>null</code>
     *  object values.  Each column's name will be taken from
     *  the <code>columnNames</code> array.
     *
     * @param columnNames       <code>array</code> containing the names
     *				of the new columns; if this is
     *                          <code>null</code> then the model has no columns
     * @param rowCount           the number of rows the table holds
     * @see #setDataVector
     * @see #setValueAt
     */
    public TTableSortModel(Object[] columnNames, int rowCount) {
        this(convertToVector(columnNames), rowCount);
    }

    /**
     *  Constructs a <code>TTableModel</code> and initializes the table
     *  by passing <code>data</code> and <code>columnNames</code>
     *  to the <code>setDataVector</code> method.
     *
     * @param data              the data of the table, a <code>Vector</code>
     *                          of <code>Vector</code>s of <code>Object</code>
     *                          values
     * @param columnNames       <code>vector</code> containing the names
     *				of the new columns
     * @see #getDataVector
     * @see #setDataVector
     */
    public TTableSortModel(Vector data, Vector columnNames) {
        super(data,columnNames);
    }

    /**
     *  Constructs a <code>TTableModel</code> and initializes the table
     *  by passing <code>data</code> and <code>columnNames</code>
     *  to the <code>setDataVector</code>
     *  method. The first index in the <code>Object[][]</code> array is
     *  the row index and the second is the column index.
     *
     * @param data              the data of the table
     * @param columnNames       the names of the columns
     * @see #getDataVector
     * @see #setDataVector
     */
    public TTableSortModel(Object[][] data, Object[] columnNames) {
        super(data,columnNames);
    }
    /**
     * 设置标题
     * @param columnIdentifiers String[]
     */
    public void setHeader(String columnIdentifiers[])
    {
        //this.columnIdentifiers = convertToVector(columnIdentifiers);
        setDataVector(null,columnIdentifiers);
    }
    /**
     * 设置数据
     * @param dataVector Vector
     */
    public void setData(Vector dataVector) {
        if(dataVector == null)
            return;
        this.dataVector = dataVector;
    }
    /**
     * 插入一行数据
     * @param row int 插入行号
     * @param data Vector 数据包
     * @return int
     */
    public int insertRowValue(int row,Vector data)
    {
        if(dataVector == null)
            return -1;
        if(row < 0 || row >= getRowCount())
        {
            dataVector.add(data);
            return getRowCount() - 1;
        }
        dataVector.add(row,data);
        return row;
    }

}
