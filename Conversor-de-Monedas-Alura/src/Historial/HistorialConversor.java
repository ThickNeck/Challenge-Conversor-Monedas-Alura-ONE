package Historial;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialConversor {
    private static final String FILE_PATH = "historial_conversiones.json";
    private static List<Conversion> historial;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new AdaptadorFechaHora())
            .setPrettyPrinting()
            .create();

    public static class Conversion {
        private final String moneda1;
        private final String moneda2;
        private final double monto;
        private final double tasaConversion;
        private final double resultado;
        private final LocalDateTime fechaHora;

        public Conversion(String moneda1, String moneda2, double monto, double tasaConversion, double resultado) {
            this.moneda1 = moneda1;
            this.moneda2 = moneda2;
            this.monto = monto;
            this.tasaConversion = tasaConversion;
            this.resultado = resultado;
            this.fechaHora = LocalDateTime.now();
        }
    }

    private List<Conversion> cargarHistorial() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Conversion>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el historial, creando un historial nuevo.");
            return new ArrayList<>();
        }
    }

    public HistorialConversor() {
        historial = cargarHistorial();
        if (historial == null) {
            historial = new ArrayList<>();
        }
    }

    private void guardarHistorial() {
        Path tempFile = Paths.get(FILE_PATH + ".tmp");
        try {
            String json = gson.toJson(historial);
            Files.writeString(tempFile, json);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        try {
            Files.move(tempFile, Paths.get(FILE_PATH), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void agregarConversion(String monedaOrigen, String monedaDestino, double cantidad, double tasaConversion, double resultado) {
        Conversion conversion = new Conversion(monedaOrigen, monedaDestino, cantidad, tasaConversion, resultado);
        historial.add(conversion);
        guardarHistorial();
    }

    public List<Conversion> obtenerHistorial() {
        return historial;
    }

    public void imprimirHistorial() {
        if (historial.isEmpty()) {
            System.out.println("No hay conversiones en el historial.");
        } else {
            System.out.println("Tu historial de Conversiones:");
            for (Conversion conversion : historial) {
                System.out.println("-----------------------------------");
                System.out.println("Fecha y Hora: " + conversion.fechaHora);
                System.out.println("Conversor.Moneda Origen: " + conversion.moneda1);
                System.out.println("Conversor.Moneda Destino: " + conversion.moneda2);
                System.out.println("Monto: " + conversion.monto);
                System.out.println("Tasa de Conversión: " + conversion.tasaConversion);
                System.out.println("Resultado: " + conversion.resultado);
            }
        }
    }
}