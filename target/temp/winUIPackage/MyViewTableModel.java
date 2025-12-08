package winUIPackage;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;


public class MyViewTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyViewTableModel(Vector<Vector<Object>> tableData, Vector<String> headerData) {
		super(tableData, headerData);
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}
}
