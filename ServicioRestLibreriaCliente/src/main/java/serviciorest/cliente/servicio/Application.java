package serviciorest.cliente.servicio;


import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;

import serviciorest.cliente.entidad.Libro;
import serviciorest.cliente.servicio.ServicioProxyBiblioteca;



@SpringBootApplication
public class Application implements CommandLineRunner{
	
		
	@Autowired
	private ServicioProxyBiblioteca spb;
	
	
	@Autowired
	private ApplicationContext context;
		
		
		public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Application.class, args);

		}
		
		@Override
		public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		int opcion;
		
			while (!exit){
				menu();
				opcion = scanner.nextInt();
				
				switch (opcion) {
				case 1:
					altaLibro();
					break;
				case 2:
					bajaLibro();
					break;
				case 3:
					modificarLibro();
					break;
				case 4:
					obtenerLibro();
					break;				
				case 5:
					listarLibros();
					break;
				case 6:
					exit = true;
					break;
				default: 
					System.out.println("Opcion no valida, elige de nuevo");
			}
			
		}	
		System.out.println("******************************************");		
		System.out.println("******** Parando el cliente REST *********");	
		pararAplicacion();
		}
		
		public void pararAplicacion() {
			SpringApplication.exit(context, () -> 0);
			
		}
		public void menu(){
			System.out.println();
			System.out.println("********* MENU *********");
			System.out.println("------------------------");				
			System.out.println("1. Dar de alta un libro");
			System.out.println("2. Dar de baja un libro por ID");
			System.out.println("3. Modificar un libro por ID");
			System.out.println("4. Obtener un libro por ID");
			System.out.println("5. Listar todos los libros");
			System.out.println("6. Salir");
			System.out.println("Elige una opcion");
			}
		
		public void altaLibro() {
			Scanner scanner = new Scanner(System.in);
			int id = 0;
			List<Libro> lista = spb.listar();
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
			int nota = scanner.nextInt();
			
			Libro l = new Libro(id, titulo, editorial, nota);
			
			spb.alta(l);
		}
		
		public void bajaLibro() {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Introduzca el ID del libro que desea dar de baja:");
			int id = scanner.nextInt();
			spb.borrar(id);
		}
		
		public void modificarLibro() {		
			Scanner scanner = new Scanner(System.in);
			System.out.println("Introduzca el ID del libro que desea modificar:");
			int id = scanner.nextInt();
			scanner.nextLine();
			
			List<Libro> lista = spb.listar();
			
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
					System.out.println("Introduzca el titulo del nuevo libro:");
					titulo = scanner.nextLine();
					for (Libro libro : lista) {
						if (titulo.equalsIgnoreCase(libro.getTitulo())) {
							passTitulo = false;
							System.out.println("El titulo introducido ya existe");
							break;
						}else {
							passTitulo = true;
						}
					}		
				}while(passTitulo == false);
				
				System.out.println("Introduzca la editorial del nuevo libro:");
				String editorial = scanner.nextLine();
				System.out.println("Introduzca una nota para el nuevo libro:");
				int nota = scanner.nextInt();
				
				Libro l = new Libro(id, titulo, editorial, nota);
				
				spb.modificar(l);
				
			}else {
				System.out.println("No existe ningún libro con el ID especificado");
			}

		}
		
		public void obtenerLibro() {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Introduzca el ID del libro que desea obtener:");
			int id = scanner.nextInt();
			List<Libro> lista = spb.listar();
			
			boolean exist = false;
			for (Libro lb : lista) {
				if (lb.getId()==id) {
					exist = true;
				}
			}
			
			if (exist == true) {
				Libro libro = spb.obtener(id);
				System.out.println("ID: " + libro.getId());
				System.out.println("Título: " + libro.getTitulo());
				System.out.println("Editorial: " + libro.getEditorial());
				System.out.println("Nota: " + libro.getNota());
				
			}else {
				System.out.println("No existe ningún libro con el ID especificado");
			}
		}
		
		public void listarLibros() {
			List<Libro> lista = spb.listar();
			for (Libro libro : lista) {
				System.out.println("ID: " + libro.getId());
				System.out.println("Título: " + libro.getTitulo());
				System.out.println("Editorial: " + libro.getEditorial());
				System.out.println("Nota: " + libro.getNota());
				System.out.println("");
			}
		}
		
		
	}
									
					   
		
			
