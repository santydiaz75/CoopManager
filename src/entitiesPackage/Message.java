package entitiesPackage;

import java.awt.Component;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.hibernate.HibernateException;

public class Message {

	public static void ShowInformationMessage(Object parentFrame, String msg) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				msg, "",
				JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}
	
	public static void ShowSaveSuccesfull(Object parentFrame) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				"El registro se ha grabado con �xito.", "",
				JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}
	
	public static void ShowValidateMessage(Object parentFrame, String msg) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				msg, "",
				JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}
	
	public static void ShowHibernateError(Object parentFrame, String procname, HibernateException e) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				"Se ha producido un error en " + procname + ".\n" + e.getMessage(), "",
				JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);
	}
	public static void ShowRuntimeError(Object parentFrame, String procname, RuntimeException e) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				"Se ha producido el siguiente error de ejecuci�n en " + procname + ". \n" +  e.getMessage() + "\nPor favor p�ngase en contacto con su administrador de sistemas.", "",
				JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);
	}
	public static void ShowParseError(Object parentFrame, String procname, ParseException e) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				"Se ha producido el siguiente error de ejecuci�n en " + procname + ". \n" +  e.getMessage() + "\nPor favor p�ngase en contacto con su administrador de sistemas.", "",
				JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);
	}
	public static void ShowIOError(Object parentFrame, String procname, IOException e) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				"Se ha producido el siguiente error de entrada/salida en " + procname + ". \n" +  e.getMessage() + "\nPor favor p�ngase en contacto con su administrador de sistemas.", "",
				JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);
	}
	public static void ShowErrorMessage(Object parentFrame, String procname, Exception e) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				"Se ha producido el siguiente error de ejecuci�n en " + procname + ". \n" +  e.getMessage() + "\nPor favor p�ngase en contacto con su administrador de sistemas.", "",
				JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);
	}
	public static void ShowErrorMessage(Object parentFrame, String procname, String msgErr) {
		Object[] options = { "Aceptar" };
		JOptionPane.showOptionDialog(SwingUtilities
				.getWindowAncestor((Component)parentFrame),
				"Se ha producido el siguiente error de ejecuci�n en " + procname + ". \n" + msgErr + "\nPor favor p�ngase en contacto con su administrador de sistemas.", "",
				JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null,
				options, options[0]);
	}
		
}
