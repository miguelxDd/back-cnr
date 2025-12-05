package prueba.cnr.cnr_crud.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.cnr.cnr_crud.payload.request.ProductoRequest;
import prueba.cnr.cnr_crud.payload.response.ApiResponse;
import prueba.cnr.cnr_crud.payload.response.ProductoResponse;
import prueba.cnr.cnr_crud.service.service.ProductoService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {

        private final ProductoService productoService;

        public ProductoController(ProductoService productoService) {
                this.productoService = productoService;
        }

        @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el sistema")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
        })
        @PostMapping
        public ResponseEntity<ApiResponse<ProductoResponse>> crear(
                        @Valid @RequestBody ProductoRequest request) {

                ProductoResponse producto = productoService.crear(request);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(ApiResponse.success("Producto creado exitosamente", producto));
        }

        @Operation(summary = "Listar productos paginados", description = "Obtiene una lista paginada de productos con filtros opcionales")
        @GetMapping
        public ResponseEntity<ApiResponse<Page<ProductoResponse>>> listar(
                        @Parameter(description = "Filtrar por nombre") @RequestParam(required = false) String nombre,
                        @Parameter(description = "Precio mínimo") @RequestParam(required = false) BigDecimal precioMin,
                        @Parameter(description = "Precio máximo") @RequestParam(required = false) BigDecimal precioMax,
                        @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
                        @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size,
                        @Parameter(description = "Campo para ordenar") @RequestParam(defaultValue = "id") String sortBy,
                        @Parameter(description = "Dirección de ordenamiento (asc/desc)") @RequestParam(defaultValue = "desc") String sortDir) {

                Sort sort = sortDir.equalsIgnoreCase("asc")
                                ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                Pageable pageable = PageRequest.of(page, size, sort);
                Page<ProductoResponse> productos = productoService.obtenerPaginado(
                                nombre, precioMin, precioMax, pageable);

                return ResponseEntity.ok(ApiResponse.success(productos));
        }

        @Operation(summary = "Listar todos los productos", description = "Obtiene la lista completa de productos activos")
        @GetMapping("/todos")
        public ResponseEntity<ApiResponse<List<ProductoResponse>>> listarTodos() {
                List<ProductoResponse> productos = productoService.obtenerTodos();
                return ResponseEntity.ok(ApiResponse.success(productos));
        }

        @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto encontrado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado")
        })
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductoResponse>> obtenerPorId(
                        @Parameter(description = "ID del producto") @PathVariable Long id) {

                ProductoResponse producto = productoService.obtenerPorId(id);
                return ResponseEntity.ok(ApiResponse.success(producto));
        }

        @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
        })
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductoResponse>> actualizar(
                        @Parameter(description = "ID del producto") @PathVariable Long id,
                        @Valid @RequestBody ProductoRequest request) {

                ProductoResponse producto = productoService.actualizar(id, request);
                return ResponseEntity.ok(ApiResponse.success("Producto actualizado exitosamente", producto));
        }

        @Operation(summary = "Eliminar producto", description = "Elimina (soft delete) un producto por su ID")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> eliminar(
                        @Parameter(description = "ID del producto") @PathVariable Long id) {
                productoService.eliminar(id);
                return ResponseEntity.ok(ApiResponse.success("Producto eliminado exitosamente"));
        }
}
