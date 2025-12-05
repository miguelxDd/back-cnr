package prueba.cnr.cnr_crud.utils;

public final class Constants {

    private Constants() {
        // Evitar instanciación
    }

    // Estados de registro
    public static final String ACTIVO = "1";
    public static final String INACTIVO = "0";

    // Paginación por defecto
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    // Mensajes comunes
    public static final String MSG_CREATED = "Registro creado exitosamente";
    public static final String MSG_UPDATED = "Registro actualizado exitosamente";
    public static final String MSG_DELETED = "Registro eliminado exitosamente";
    public static final String MSG_NOT_FOUND = "Registro no encontrado";

    // Roles
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
}
