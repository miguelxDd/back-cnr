package prueba.cnr.cnr_crud.payload.mapper;

import org.springframework.stereotype.Component;
import prueba.cnr.cnr_crud.payload.entities.Producto;
import prueba.cnr.cnr_crud.payload.request.ProductoRequest;
import prueba.cnr.cnr_crud.payload.response.ProductoResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductoMapper {

    /**
     * Convierte entidad a response
     */
    public ProductoResponse toResponse(Producto entity) {
        if (entity == null) {
            return null;
        }

        ProductoResponse response = new ProductoResponse();
        response.setId(entity.getId());
        response.setNombre(entity.getNombre());
        response.setDescripcion(entity.getDescripcion());
        response.setPrecio(entity.getPrecio());
        response.setStock(entity.getStock());
        response.setFechaCreacion(entity.getFechaCreacion());
        response.setUsuarioCreacion(entity.getUsuarioCreacion());
        response.setFechaModificacion(entity.getFechaModificacion());
        response.setUsuarioModificacion(entity.getUsuarioModificacion());

        return response;
    }

    /**
     * Convierte lista de entidades a lista de responses
     */
    public List<ProductoResponse> toResponseList(List<Producto> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte request a entidad (para crear)
     */
    public Producto toEntity(ProductoRequest request) {
        if (request == null) {
            return null;
        }

        Producto entity = new Producto();
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecio(request.getPrecio());
        entity.setStock(request.getStock());

        return entity;
    }

    /**
     * Actualiza entidad existente con datos del request
     */
    public void updateEntity(Producto entity, ProductoRequest request) {
        if (entity == null || request == null) {
            return;
        }

        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecio(request.getPrecio());
        entity.setStock(request.getStock());
    }
}
