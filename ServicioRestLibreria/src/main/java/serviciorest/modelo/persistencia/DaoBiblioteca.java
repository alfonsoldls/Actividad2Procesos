package serviciorest.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import serviciorest.modelo.entidad.Libro;

@Component
public class DaoBiblioteca {

	
	
	public List<Libro> listaLibros;
	
	
	public DaoBiblioteca() {
		System.out.println("DaoBiblioteca -> Creando lista de libros...");
		listaLibros = new ArrayList<Libro>();
		Libro l1 = new Libro(1, "Harry Potter y la piedra filosofal", "Salamandra", 10);
		Libro l2 = new Libro(2, "Harry Potter y la camara secreta", "Salamandra", 8);
		Libro l3 = new Libro(3, "Harry Potter y el prisionero de Azkaban", "Salamandra", 7);
		Libro l4 = new Libro(4, "Harry Potter y el caliz de fuego", "Salamandra", 9);
		Libro l5 = new Libro(5, "Harry Potter y la orden del fenix", "Salamandra", 7);
		listaLibros.add(l1);
		listaLibros.add(l2);
		listaLibros.add(l3);
		listaLibros.add(l4);
		listaLibros.add(l5);
	}
	
	
	
	//AÑADIR UN LIBRO
	public boolean altaLibro(Libro l) {
			for (Libro lb: listaLibros) {
				if (lb.getId() == lb.getId() || lb.getTitulo().equalsIgnoreCase(lb.getTitulo())) {
					return true;
				}
			}
			listaLibros.add(l);
			return false;
	}

	//DAR DE BAJA UN LIBRO POR ID
	public Libro borrarLibro(int id) {
		try {
			return listaLibros.remove(id);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("No se ha encontrado ningun libro con ese ID");
			return null;
		}
	}
	
	//MODIFICAR UN LIBRO POR ID
	public Libro modificarLibro (Libro l) {
		try {
				Libro libroDuplicado = new Libro(0, "duplicado", null, 0);
				for (Libro lb: listaLibros) {
					if (lb.getTitulo().equalsIgnoreCase(lb.getTitulo())) {
						return libroDuplicado;
					}
				}	
				Libro libroAux = listaLibros.get(l.getId());
				libroAux.setTitulo(l.getTitulo());
				libroAux.setEditorial(l.getEditorial());
				libroAux.setNota(l.getNota());
				
				return libroAux;
				
			}catch(IndexOutOfBoundsException iobe) {
				System.out.println("No se ha encontrado ningún libro con ese ID.");
				return null;
			}
		}
	
		
		//OBTENER UN LIBRO POR ID
				public Libro findById (int id){
				try {	
				Libro encontrado = null;
				for(Libro l : listaLibros) {
					if(l.getId() == id) {
						encontrado = listaLibros.get(id);
					}
				}
				return encontrado;
			} catch (IndexOutOfBoundsException iobe) {
			System.out.println("No se ha encontrado ningun libro con ese ID");
			return null;
			}
		
		}
				
		//LISTAR TODOS LOS LIBROS
		public List<Libro> listarLibros() {
			return listaLibros;
		}
}
