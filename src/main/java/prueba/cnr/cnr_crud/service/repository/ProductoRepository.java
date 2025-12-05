package prueba.cnr.cnr_crud.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prueba.cnr.cnr_crud.payload.entities.Producto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Buscar productos activos ordenados por ID descendente
     */
    List<Producto> findByActivoOrderByIdDesc(String activo);

    /**
     * Buscar producto activo por ID
     */
    Optional<Producto> findByIdAndActivo(Long id, String activo);

    /**
     * Búsqueda paginada con filtros opcionales
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = '1' " +
           "AND (:nombre IS NULL OR UPPER(p.nombre) LIKE UPPER(CONCAT('%', :nombre, '%'))) " +
           "AND (:precioMin IS NULL OR p.precio >= :precioMin) " +
           "AND (:precioMax IS NULL OR p.precio <= :precioMax)")
    Page<Producto> findWithFilters(
            @Param("nombre") String nombre,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            Pageable pageable
    );

    /**
     * Verificar si existe producto con nombre (solo activos)
     */
    boolean existsByNombreIgnoreCaseAndActivo(String nombre, String activo);

    /**
     * Verificar si existe producto con nombre excluyendo un ID específico
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
           "FROM Producto p WHERE UPPER(p.nombre) = UPPER(:nombre) " +
           "AND p.activo = '1' AND p.id != :id")
    boolean existsByNombreAndNotId(@Param("nombre") String nombre, @Param("id") Long id);
}
