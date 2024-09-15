package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import service.ProductosService;

@RestController
public class ProductosController {
	@Autowired
	ProductosService productosService;
	
	@GetMapping(value="productos")
	public Flux<Producto> productos(){
		return productosService.catalogo();
	}
	
	@GetMapping(value="productos/{categoria}")
	public Flux<Producto> productosCategoria(@PathVariable("categoria") String categoria){
		return productosService.productosCategoria(categoria);
	}
	
	@GetMapping(value="producto")
	public Mono<Producto> productoCodigo(@RequestParam("cod") int cod) {
		return productosService.productoCodigo(cod);
	}
	
	@PostMapping(value="alta", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Mono<Void> altaProducto(@RequestBody Producto producto) {
		return productosService.altaProducto(producto);
	}
	
	@DeleteMapping(value="eliminar")
	public Mono<Producto> eliminarProducto(@RequestParam("cod") int cod) {
		return productosService.eliminarProducto(cod);
	}	
	
	@PutMapping(value="actualizar")
	public Mono<Producto> actualizarPrecio(@RequestParam("cod") int cod, @RequestParam("precio") double precio) {
		return productosService.actualizarPrecio(cod, precio);
	}
}
