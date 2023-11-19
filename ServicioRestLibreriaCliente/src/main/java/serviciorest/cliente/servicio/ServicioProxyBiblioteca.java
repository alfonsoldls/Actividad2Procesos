package serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Libro;

//Con esta anotación damos de alta un objeto de tipo
//ServicioProxyPersona dentro del contexto de Spring
@Service
public class ServicioProxyBiblioteca {

	//La URL base del servicio REST de libros
	public static final String URL = "http://localhost:8080/";
		
	//Inyectamos el objeto de tipo RestTemplate que nos ayudará
	//a hacer las peticiones HTTP al servicio REST
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	public Libro obtener(int id){
		try {
			ResponseEntity<Libro> re = restTemplate.getForEntity(URL + id, Libro.class);
			HttpStatus hs= re.getStatusCode();
			if(hs == HttpStatus.OK) {	
				return re.getBody();
			}else {
				System.out.println("obtener -> Respuesta no contemplada");
				return null;
			}
		}catch (HttpClientErrorException e) {
			System.out.println("obtener -> El libro no se ha encontrado, id: " + id);
		    System.out.println("obtener -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	
	 // Método que da de alta un nuevo libro 
	
	public Libro alta(Libro l){
		try {
			ResponseEntity<Libro> re = restTemplate.postForEntity(URL, l, Libro.class);
			System.out.println("alta -> Codigo de respuesta " + re.getStatusCode());
			return re.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("alta -> El libro no se ha dado de alta, id: " + l);
		    System.out.println("alta -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	
	 // Modifica un libro en el servicio REST
	 
	public boolean modificar(Libro l){
		try {
			restTemplate.put(URL + l.getId(), l, Libro.class);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("modificar -> El libro no se ha modificado, id: " + l.getId());
		    System.out.println("modificar -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	
	 //Borra un libro en el servicio REST
	
	public boolean borrar(int id){
		try {
			restTemplate.delete(URL + id);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("borrar -> El libro no se ha borrado, id: " + id);
		    System.out.println("borrar -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	
	public List<Libro> listar(){
		try {
			ResponseEntity<Libro[]> response =
					  restTemplate.getForEntity(URL, Libro[].class);
			Libro[] arrayLibros = response.getBody();
			// Convierte el array a una lista y la devuelve
			return Arrays.asList(arrayLibros);
		} catch (HttpClientErrorException e) {
			System.out.println("listar -> Error al obtener la lista de libros");
		    System.out.println("listar -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}

}
	
