package service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductosServiceImpl implements ProductosService {
	private static List<Producto> productos=new ArrayList<>(List.of(new Producto(100,"Azucar","Alimentación",1.10,20),
			new Producto(101,"Leche","Alimentación",1.20,15),
			new Producto(102,"Jabón","Limpieza",0.89,30),
			new Producto(103,"Mesa","Hogar",125,4),
			new Producto(104,"Televisión","Hogar",650,10),
			new Producto(105,"Huevos","Alimentación",2.20,30),
			new Producto(106,"Fregona","Limpieza",3.40,6),
			new Producto(107,"Detergente","Limpieza",8.7,12)));
	@Override
	public Flux<Producto> catalogo() {
		return Flux.fromIterable(productos)//Flux<Producto>
				.delayElements(Duration.ofSeconds(2)); //Flux<Producto>
	}	

	@Override
	public Flux<Producto> productosCategoria(String categoria) {
		return catalogo() //Flux<Producto>
				.filter(p->p.getCategoria().equals(categoria));
	}

	@Override
	public Mono<Producto> productoCodigo(int cod) {
		return catalogo()//Flux<Producto>
				.filter(p->p.getCodProducto()==cod)//Flux<Producto>
				.next();//Mono<Producto>
				//.switchIfEmpty(Mono.just(new Producto()));
	}

	@Override
	public Mono<Void> altaProducto(Producto producto) {
		return productoCodigo(producto.getCodProducto())//Mono<Producto>
				.switchIfEmpty(Mono.just(producto).map(p->{//Si no existe el 
					//producto, se crea un Mono<Producto> con el nuevo producto.
					productos.add(producto);// Acción: Agrega el producto a la 
					//lista productos, como un efecto secundario, en lugar de
					//transformar el producto.
					return p;// Retorna el mismo producto sin transformación
					//.map() en este caso, no es para transformar el flujo en
					//el sentido tradicional, sino para ejecutar una acción
					//(agregar el producto a la lista) cuando el flujo llega a ese punto
				})) //Mono<Producto>
				.then();//Mono<Void>: Completa la operación sin emitir valor
	}

	@Override
	public Mono<Producto> eliminarProducto(int cod) {
		return productoCodigo(cod)//Mono<Producto>
				.map(p->{
					productos.removeIf(r->r.getCodProducto()==cod);
					return p;
				});//Mono<Producto>
				//.switchIfEmpty(null);//Mono<Producto>
	}

	@Override
	public Mono<Producto> actualizarPrecio(int cod, double precio) {
		return productoCodigo(cod)//Mono<Producto>
				.map(p->{
					p.setPrecioUnitario(precio);
					return p;
				});
	}

}
