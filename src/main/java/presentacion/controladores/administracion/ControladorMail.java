package presentacion.controladores.administracion;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import domain.model.Mail;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import persistencia.dao.mysql.DAOSQLFactory;
import services.MailService;
import util.Dialogos;
import util.ExpReg;

public class ControladorMail implements Initializable{

    @FXML
    private TextField txtMail;

    @FXML
    private Text lblModificacionMail;

    @FXML
    private PasswordField txtClave;

    @FXML
    private Button btnModificar;

    @FXML
    private Text lblTitulo;
    
    private MailService mailService = new MailService(new DAOSQLFactory());
    
    private ValidationSupport validationSupport = new ValidationSupport();
    
    private Mail mailAmodificar;
    
    @FXML
    void modificarMail(MouseEvent event) {
    	String mensajeError= "";
    	if (!ExpReg.mailValido(txtMail.getText().trim())) { 
    		mensajeError+= "- Ingrese Mail v\u00e1lido \n";
    	}
    	
        if (txtClave.getText().isEmpty()) {
        	mensajeError+= "- La clave no puede estar vacia";
        }
        
        if (mensajeError == "")
    	{
			mailAmodificar.setMail(txtMail.getText().trim());
			mailAmodificar.setClave(txtClave.getText());

			mailService.editarMail(mailAmodificar);
			
			lblModificacionMail.setText("Se modifico el mail para alertas correctamente!");
    	}
    	else
    	{
    		Dialogos.error("Error", "Error al modificar mail", mensajeError);
    		lblModificacionMail.setText("");
    	}

    	validationSupport.registerValidator(txtMail, Validator.createEmptyValidator("Campo requerido"));
		validationSupport.registerValidator(txtClave, Validator.createEmptyValidator("Campo requerido"));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mailAmodificar = mailService.obtenerMail(1);
		cargarMailAmodificar(mailAmodificar);
	}
	
	private void cargarMailAmodificar(Mail mail) {
		txtMail.setText(mail.getMail());
		txtClave.setText(mail.getClave());
	}

}
