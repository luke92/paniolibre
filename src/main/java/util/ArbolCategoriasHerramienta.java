package util;

import java.util.List;

import javax.swing.JOptionPane;

import org.controlsfx.control.CheckTreeView;

import domain.model.CategoriaHerramienta;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import persistencia.dao.mysql.DAOSQLFactory;
import services.CategoriaHerramientaService;

public class ArbolCategoriasHerramienta extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 500, 450);

		primaryStage.setTitle("Categorias Herramienta");
		primaryStage.setScene(scene);
		primaryStage.show();

		// creacion arbol
		CheckBoxTreeItem<CategoriaHerramienta> nodoRaiz = new CheckBoxTreeItem<>(
				new CategoriaHerramienta(0, "Herramientas", null, 0));
		nodoRaiz.setExpanded(true);
		CategoriaHerramientaService categoria = new CategoriaHerramientaService(new DAOSQLFactory());
		List<CategoriaHerramienta> listacategorias = categoria.obtenerCategoriaHerramienta();
		for (CategoriaHerramienta categoriaHerramienta : listacategorias) {
			CheckBoxTreeItem<CategoriaHerramienta> nodoHijo = new CheckBoxTreeItem<>(categoriaHerramienta);
			agregarHijo(nodoRaiz, nodoHijo);
		}
		CheckTreeView<CategoriaHerramienta> treeView = new CheckTreeView<>(nodoRaiz);
		root.getChildren().add(treeView);

		// Creacion del boton
		Button boton = new Button("Agregar Subcategoria");
		boton.setDefaultButton(true);
		boton.setPrefSize(300, 50);
		root.getChildren().add(boton);

		boton.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() > 0) {
				ReadOnlyObjectProperty<TreeItem<CategoriaHerramienta>> padre = treeView.getSelectionModel()
						.selectedItemProperty();
				String input = JOptionPane.showInputDialog("Escriba el nombre de la subcategoria: ");
				agregarHijo(padre.get(), new CheckBoxTreeItem<>(new CategoriaHerramienta(0, input, null, 0)));
			}
		});
	}

	void agregarHijo(TreeItem<CategoriaHerramienta> treeItem, CheckBoxTreeItem<CategoriaHerramienta> hijo) {
		treeItem.getChildren().add(hijo);
		hijo.setExpanded(true);
	}
}