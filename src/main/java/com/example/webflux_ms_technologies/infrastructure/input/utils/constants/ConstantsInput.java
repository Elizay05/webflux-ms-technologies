package com.example.webflux_ms_technologies.infrastructure.input.utils.constants;

public class ConstantsInput {
    public static final String ERROR = "error";
    public static final String TECHNOLOGY_IDS_EMPTY = "La lista de tecnologías esta vacia";

    // Paths
    public static final String PATH_TECHNOLOGIES = "/technologies";
    public static final String PATH_TECHNOLOGIES_BY_IDS = "/technologies/by-ids";

    // Métodos
    public static final String METHOD_CREATE = "createTechnology";
    public static final String METHOD_GET = "getTechnologies";
    public static final String METHOD_GET_BY_IDS = "getTechnologiesByIds";

    // Operaciones
    public static final String OP_CREATE_TECHNOLOGY = "createTechnology";
    public static final String OP_GET_TECHNOLOGIES = "getTechnologies";
    public static final String OP_GET_BY_IDS = "getTechnologiesByIds";

    // Resumen y descripciones
    public static final String SUMMARY_CREATE_TECHNOLOGY = "Crear una nueva tecnología";
    public static final String DESC_CREATE_TECHNOLOGY = "Registra una nueva tecnología en el sistema.";
    public static final String SUMMARY_GET_TECHNOLOGIES = "Recuperar una lista paginada de tecnologías";
    public static final String DESC_GET_TECHNOLOGIES = "Obtiene una lista paginada de tecnologías, lo que permite ordenar por nombre en orden ascendente o descendente.";
    public static final String SUMMARY_GET_BY_IDS = "Obtener una lista de tecnologías por sus IDs";
    public static final String DESC_GET_BY_IDS = "Recupera una lista de tecnologías basadas en los IDs proporcionados.";

    // Parámetros de consulta
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_ASC = "asc";

    public static final String DESC_PAGE = "Número de la página (por defecto 0)";
    public static final String DESC_SIZE = "Cantidad de tecnologías por página (por defecto 10)";
    public static final String DESC_ASC = "Orden ascendente (true) o descendente (false)";

    public static final String EXAMPLE_PAGE = "0";
    public static final String EXAMPLE_SIZE = "10";
    public static final String EXAMPLE_ASC = "true";

    // Códigos de respuesta
    public static final String CODE_201 = "201";
    public static final String CODE_400 = "400";
    public static final String CODE_200 = "200";
    public static final String CODE_500 = "500";
    public static final String CODE_404 = "404";

    // Mensajes de respuesta
    public static final String RESP_TECHNOLOGY_CREATED = "Tecnología creada exitosamente";
    public static final String RESP_ERROR_VALIDATION = "Error en la validación de datos";
    public static final String RESP_TECHNOLOGY_LIST = "Lista paginada de tecnologías";
    public static final String RESP_ERROR_INVALID_PARAMS = "Parámetros de consulta no válidos";
    public static final String RESP_ERROR_INTERNAL_SERVER = "Error Interno del Servidor";
    public static final String RESP_TECHNOLOGIES_FOUND = "Lista de tecnologías solicitadas";
    public static final String RESP_ERROR_INVALID_BODY = "Cuerpo de la solicitud inválido";
    public static final String RESP_ERROR_NOT_FOUND = "Algunas o todas las tecnologías no fueron encontradas";

    // Ejemplos JSON
    public static final String EXAMPLE_NAME_CREATE = "Ejemplo de creación de tecnología";
    public static final String EXAMPLE_TECHNOLOGY_CREATE = "{ \"name\": \"IA\", \"description\": \"Inteligencia Artificial\" }";
    public static final String EXAMPLE_ERROR_VALIDATION = "{ \"error\": \"El nombre es requerido\" }";
    public static final String EXAMPLE_NAME_GET = "Ejemplo de obtención de tecnologías";
    public static final String EXAMPLE_TECHNOLOGY_GET = "{ \"technologies\": [{ \"id\": \"1\", \"name\": \"IA\", \"description\": \"Inteligencia Artificial\" }], \"totalPages\": 1, \"totalItems\": 1 }";
    public static final String EXAMPLE_ERROR_INVALID_PARAMS = "{ \"error\": \"Valor de página o tamaño inválido\" }";
    public static final String EXAMPLE_NAME_GET_BY_IDS = "Ejemplo de obtención de tecnologías por IDs";
    public static final String EXAMPLE_TECHNOLOGY_GET_BY_IDS = "{ \"technologyIds\": [1, 2, 3] }";
    public static final String EXAMPLE_TECHNOLOGY_GET_BY_IDS_SUCCESS = "[ { \"id\": 1, \"name\": \"IA\", \"description\": \"Inteligencia Artificial\" }, { \"id\": 2, \"name\": \"Blockchain\", \"description\": \"Libro mayor descentralizado\" } ]";
    public static final String EXAMPLE_ERROR_EMPTY_IDS = "{ \"error\": \"Los IDs de tecnologías no pueden estar vacíos\" }";
    public static final String EXAMPLE_ERROR_NOT_FOUND = "{ \"error\": \"Algunos IDs de tecnología no fueron encontrados\" }";
}
