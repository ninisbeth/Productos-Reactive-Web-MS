package service;

import model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductosService {
	Flux<Producto> catalogo();
	Flux<Producto> productosCategoria(String categoria);
	Mono<Producto> productoCodigo(int cod);
	Mono<Void> altaProducto(Producto producto);
	Mono<Producto> eliminarProducto(int cod);
	Mono<Producto> actualizarPrecio(int cod, double precio);
}