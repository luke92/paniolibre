package presentacion.controladores.categorias;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import dto.ArbolCategoriaDTO;
import dto.EstadoDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import persistencia.dao.mysql.DAOSQLFactory;
import services.ArbolCategoriaService;
import util.CadenasTexto;
import util.Dialogos;
import util.TipoCategoria;

public class ControladorCategoriasABM {

	private TipoCategoria tipoCategoria;
	private ArbolCategoriaService service = new ArbolCategoriaService(new DAOSQLFactory());
	private ObservableList<ArbolCategoriaDTO> per = FXCollections.observableArrayList();
	private TreeItem<ArbolCategoriaDTO> rootArbol;

	@FXML
	private TreeView<ArbolCategoriaDTO> treeViewCategorias;

	@FXML
	private Button btnAgregarCategoria;

	@FXML
	private Button btnModificarCategoria;

	@FXML
	private Button btnEliminarCategoria;

	@FXML
	private Text lblTitulo;

	@FXML
	void agregarCategoria(MouseEvent event) {
		this.agregar();
	}

	@FXML
	void eliminarCategoria(MouseEvent event) {
		this.borrar();
	}

	@FXML
	void modificarCategoria(MouseEvent event) {
		this.modificar();
	}

	@FXML
	void categoriaSeleccionada(MouseEvent event) {

		if (obtenerCategoriaSeleccionada() == null)
			this.habilitarBotones(true);
		else
			this.habilitarBotones(false);
	}

	public void cargarVista(TipoCategoria tipoCategoria) {
		this.tipoCategoria = tipoCategoria;
		String titulo = "Insumos";
		if (tipoCategoria.equals(TipoCategoria.HERRAMIENTAS))
			titulo = "Herramientas";
		this.lblTitulo.setText("Categor\u00edas" + " de " + titulo);
		this.llenarTreeView();
	}

	private void llenarTreeView() {
		per.clear();
		ArbolCategoriaDTO arbol = null;
		if (TipoCategoria.INSUMOS == this.tipoCategoria)
			arbol = service.obtenerArbolCategoriasInsumoDTO();
		else
			arbol = service.obtenerArbolCategoriasHerramientaDTO();

		per.add(arbol);

		rootArbol = new RecursiveTreeItem<>(per, RecursiveTreeObject::getChildren);
		treeViewCategorias.setRoot(rootArbol.getChildren().get(0));
		this.expandTreeView(rootArbol.getChildren().get(0));

	}

	private void habilitarBotones(boolean estado) {
		this.btnAgregarCategoria.setDisable(estado);
		this.btnModificarCategoria.setDisable(estado);
		this.btnEliminarCategoria.setDisable(estado);
	}

	private void agregar() {
		ArbolCategoriaDTO categoriaPadre = this.obtenerCategoriaSeleccionada();
		String nombreCategoriaNueva = Dialogos.ingresarTexto("Agregar Categoria",
				"Agregar Categoria Hija en " + categoriaPadre, "Ingrese un nombre para la categoria nueva");
		if (nombreCategoriaNueva.isEmpty()) {
			Dialogos.advertencia("Error en Agregar Categoria", "No se puede agregar categoria",
					"No se ha ingresado ningun nombre para la categoria nueva");
		} else {
			ArbolCategoriaDTO categoriaHija = new ArbolCategoriaDTO(0, nombreCategoriaNueva, EstadoDTO.ALTA, null,
					categoriaPadre, tipoCategoria);
			if (this.service.existeCategoria(categoriaHija)) {
				Dialogos.advertencia("Error en Agregar Categoria", "No se puede agregar categoria",
						"Ya existe una categoria con el mismo nombre");
			} else {
				if (categoriaPadre != null)
					categoriaPadre.agregarHija(categoriaHija);
				categoriaHija.setPadre(categoriaPadre);
				this.service.agregarCategoria(categoriaHija);
				this.expandTreeView(rootArbol.getChildren().get(0));
			}
		}
	}

	private ArbolCategoriaDTO obtenerCategoriaSeleccionada() {
		ReadOnlyObjectProperty<TreeItem<ArbolCategoriaDTO>> padre = treeViewCategorias.getSelectionModel()
				.selectedItemProperty();
		if (padre.get() != null && padre.get().getValue() != null) {
			return padre.get().getValue();
		}
		return null;
	}

	private void modificar() {
		String tituloError = "Error en Modificar Categoria";
		String tituloCabecera = "No se puede modificar la categoria";
		ArbolCategoriaDTO categoria = this.obtenerCategoriaSeleccionada();
		if (categoria != null) {
			if (categoria.getId() == 1) {
				Dialogos.advertencia(tituloError, tituloCabecera, "No se puede editar la categoria raiz");
				return;
			}
			String nombreCategoriaViejo = categoria.getNombre();
			String nombreCategoria = Dialogos.ingresarTexto("Modificar Categoria", "Modificar Categoria " + categoria,
					"Ingrese el nuevo nombre para la categoria seleccionada");
			if (nombreCategoria.isEmpty()) {
				Dialogos.advertencia(tituloError, tituloCabecera, "No se ha ingresado ningun nombre para la categoria");
			} else {
				categoria.setNombre(nombreCategoria);
				if (CadenasTexto.sonIguales(nombreCategoriaViejo, nombreCategoria)
						|| !(this.service.existeCategoria(categoria))) {
					this.service.editarCategoria(categoria);
					this.llenarTreeView();
				} else if (this.service.existeCategoria(categoria)) {
					Dialogos.advertencia("Error en Modificar Categoria", "No se puede modificar la categoria",
							"Ya existe una categoria con el mismo nombre");
					categoria.setNombre(nombreCategoriaViejo);
				}
			}
		}
	}

	private void borrar() {
		ArbolCategoriaDTO categoria = this.obtenerCategoriaSeleccionada();
		ArbolCategoriaDTO categoriaPadre = null;
		if (categoria != null && Dialogos.confirmacion("Borrar Categoria", "Borrando Categoria " + categoria,
				"Desea borrar la categoria seleccionada?", "SI", "NO")) {
			categoriaPadre = categoria.getPadre();
			if (!categoria.tieneHijas() && !this.service.existeCategoriaEnOtraTabla(categoria)) {
				this.service.borrarCategoria(categoria);
				categoriaPadre.borrarHija(categoria);
			} else {
				Dialogos.error("Error Borrando Categoria", "No se puede borrar la categoria " + categoria,
						"Existen partes del sistema que dependen de esa categoria");
			}
		}
	}

	private void expandTreeView(TreeItem<ArbolCategoriaDTO> item) {
		if (item != null && !item.isLeaf()) {
			item.setExpanded(true);
			for (TreeItem<ArbolCategoriaDTO> child : item.getChildren()) {
				expandTreeView(child);
			}
		}
	}

}
