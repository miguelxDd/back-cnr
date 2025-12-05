package prueba.cnr.cnr_crud.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.cnr.cnr_crud.context.UserContext;
import prueba.cnr.cnr_crud.payload.entities.Producto;
import prueba.cnr.cnr_crud.payload.mapper.ProductoMapper;
import prueba.cnr.cnr_crud.payload.request.ProductoRequest;
import prueba.cnr.cnr_crud.payload.response.ProductoResponse;
import prueba.cnr.cnr_crud.service.repository.ProductoRepository;
import prueba.cnr.cnr_crud.utils.Constants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final UserContext userContext;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                                ProductoMapper productoMapper,
                                UserContext userContext) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.userContext = userContext;
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        // Validar nombre único
        if (productoRepository.existsByNombreIgnoreCaseAndActivo(request.getNombre(), Constants.ACTIVO)) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + request.getNombre());
        }

        Producto producto = productoMapper.toEntity(request);
        producto.setUsuarioCreacion(userContext.getCurrentUsername());
        producto.setFechaCreacion(LocalDateTime.now());
        producto.setActivo(Constants.ACTIVO);

        Producto guardado = productoRepository.save(producto);
        return productoMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        Producto producto = productoRepository.findByIdAndActivo(id, Constants.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return productoMapper.toResponse(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> obtenerTodos() {
        List<Producto> productos = productoRepository.findByActivoOrderByIdDesc(Constants.ACTIVO);
        return productoMapper.toResponseList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponse> obtenerPaginado(String nombre, BigDecimal precioMin,
                                                   BigDecimal precioMax, Pageable pageable) {
        return productoRepository.findWithFilters(nombre, precioMin, precioMax, pageable)
                .map(productoMapper::toResponse);
    }

    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findByIdAndActivo(id, Constants.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Validar nombre único (excluyendo el actual)
        if (productoRepository.existsByNombreAndNotId(request.getNombre(), id)) {
            throw new IllegalArgumentException("Ya existe otro producto con el nombre: " + request.getNombre());
        }

        productoMapper.updateEntity(producto, request);
        producto.setUsuarioModificacion(userContext.getCurrentUsername());
        producto.setFechaModificacion(LocalDateTime.now());

        Producto actualizado = productoRepository.save(producto);
        return productoMapper.toResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = productoRepository.findByIdAndActivo(id, Constants.ACTIVO)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Soft delete
        producto.setActivo(Constants.INACTIVO);
        producto.setUsuarioEliminacion(userContext.getCurrentUsername());
        producto.setFechaEliminacion(LocalDateTime.now());

        productoRepository.save(producto);
    }

    // Excepción interna
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
