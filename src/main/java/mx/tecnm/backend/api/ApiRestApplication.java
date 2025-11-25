package mx.tecnm.backend.api;
//no lo dejes publico wey
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ApiRestApplication {

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load(); 
            System.setProperty("DB_URL", dotenv.get("DB_URL"));
            System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
            System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
            
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudo cargar el archivo .env o faltan variables. Se usará la configuración por defecto o las variables del sistema.");
        }

        SpringApplication.run(ApiRestApplication.class, args);
    }
}
