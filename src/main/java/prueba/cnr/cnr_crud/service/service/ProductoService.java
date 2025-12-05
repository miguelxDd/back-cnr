package prueba.cnr.cnr_crud.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import prueba.cnr.cnr_crud.payload.request.ProductoRequest;
import prueba.cnr.cnr_crud.payload.response.ProductoResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {

    ProductoResponse crear(ProductoRequest request);

    ProductoResponse obtenerPorId(Long id);

    List<ProductoResponse> obtenerTodos();

    Page<ProductoResponse> obtenerPaginado(String nombre, BigDecimal precioMin,
                                           BigDecimal precioMax, Pageable pageable);

    ProductoResponse actualizar(Long id, ProductoRequest request);

    void eliminar(Long id);
}
