package es.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;


import es.cliente.servicio.ServicioProxyLibro;
import es.cliente.entidad.*;

@SpringBootApplication
public class ClienteLibreriaApplication implements CommandLineRunner{

	@Autowired
	private ServicioProxyLibro spl;
		
	@Autowired
	private ApplicationContext context;
		
	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(ClienteLibreriaApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        while (!salir) {
        	menu();
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Lógica para dar de alta un libro
                	altaLibro();
                    break;
                case 2:
                    // Lógica para dar de baja un libro por ID
                	bajaLibro();
                    break;
                case 3:
                    // Lógica para modificar un libro por ID
                	modificarLibro();
                    break;
                case 4:
                    // Lógica para obtener un libro por ID
                	obtenerLibro();
                    break;
                case 5:
                    // Lógica para listar todos los libros
                	listarLibreria();
                    break;
                case 6:
                    salir = true;
                  
                    break;
                default:
                    System.out.println("Debe elegir una opción entre la 1 y la 6");
            }
            
        }
        System.out.println("Hasta luego");
        pararAplicacion();
	}
	
	public void pararAplicacion() {
		SpringApplication.exit(context, () -> 0);
	}
	
	//menú para el cliente
	public void menu() {
		System.out.println("\n|====================================|");
        System.out.println("|         Menú de Biblioteca         |");
        System.out.println("|====================================|");
        System.out.println("| [1] Dar de alta un libro           |");
        System.out.println("| [2] Dar de baja un libro por ID    |");
        System.out.println("| [3] Modificar un libro por ID      |");
        System.out.println("| [4] Obtener un libro por ID        |");
        System.out.println("| [5] Listar todos los libros        |");
        System.out.println("| [6] Salir                          |");
        System.out.println("|====================================|");
        System.out.print("\nElige una opción: ");
	}
	
	public void altaLibro() {
		Scanner scanner = new Scanner(System.in);
		int id = 0;
		List<Libro> lista = spl.listar();
		boolean passID = true;
		do {
			System.out.println("Introduzca un ID para el libro:");
			id = scanner.nextInt();
			for (Libro libro : lista) {
				if (id == libro.getId()) {
					passID = false;
					System.out.println("El ID introducido ya existe");
					break;
				}else {
					passID = true;
				}
			}		
		}while(passID == false);
		
		scanner.nextLine();
		
		String titulo = "";
		boolean passTitulo = true;
		do {
			System.out.println("Introduzca el título del libro:");
			titulo = scanner.nextLine();
			for (Libro libro : lista) {
				if (titulo.equalsIgnoreCase(libro.getTitulo())) {
					passTitulo = false;
					System.out.println("El título introducido ya existe");
					break;
				}else {
					passTitulo = true;
				}
			}		
		}while(passTitulo == false);
		
		System.out.println("Introduzca la editorial del libro:");
		String editorial = scanner.nextLine();
		System.out.println("Introduzca una nota sobre el libro:");
		String nota = scanner.nextLine();
		
		Libro libro = new Libro(id, titulo, editorial, nota);
		
		spl.alta(libro);
	}
	
	public void bajaLibro() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Introduzca el ID del libro que desea dar de baja:");
		int id = scanner.nextInt();
		spl.borrar(id);
	}
	
	public void modificarLibro() {		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Introduzca el ID del libro que desea modificar:");
		int id = scanner.nextInt();
		scanner.nextLine();
		
		List<Libro> lista = spl.listar();
		
		boolean exist = false;
		for (Libro lb : lista) {
			if (lb.getId()==id) {
				exist = true;
			}
		}
		
		if (exist == true) {
			String titulo = "";
			boolean passTitulo = true;
			do {
				System.out.println("Introduzca el título del nuevo libro:");
				titulo = scanner.nextLine();
				for (Libro libro : lista) {
					if (titulo.equalsIgnoreCase(libro.getTitulo())) {
						passTitulo = false;
						System.out.println("El título introducido ya existe");
						break;
					}else {
						passTitulo = true;
					}
				}		
			}while(passTitulo == false);
			
			System.out.println("Introduzca la editorial del nuevo libro:");
			String editorial = scanner.nextLine();
			System.out.println("Introduzca una nota sobre el nuevo libro:");
			String nota = scanner.nextLine();
			
			Libro libro = new Libro(id, titulo, editorial, nota);
			
			spl.modificar(libro);
			
		}else {
			System.out.println("No existe ningún libro con el ID especificado");
		}

	}
	
	public void obtenerLibro() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Introduzca el ID del libro que desea obtener:");
		int id = scanner.nextInt();
		List<Libro> lista = spl.listar();
		
		boolean exist = false;
		for (Libro lb : lista) {
			if (lb.getId()==id) {
				exist = true;
			}
		}
		
		if (exist == true) {
			Libro libro = spl.obtener(id);
			System.out.println("ID: " + libro.getId());
			System.out.println("Título: " + libro.getTitulo());
			System.out.println("Editorial: " + libro.getEditorial());
			System.out.println("Nota: " + libro.getNota());
			
		}else {
			System.out.println("No existe ningún libro con el ID especificado");
		}
	}
	
	public void listarLibreria() {
		List<Libro> lista = spl.listar();
		for (Libro libro : lista) {
			System.out.println("ID: " + libro.getId());
			System.out.println("Título: " + libro.getTitulo());
			System.out.println("Editorial: " + libro.getEditorial());
			System.out.println("Nota: " + libro.getNota());
			System.out.println("");
		}
	}
	
	
}
