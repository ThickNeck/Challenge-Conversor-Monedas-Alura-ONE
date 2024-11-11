package Conversor;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorMonedas {

    public static Moneda obtenerTasaConversion(String moneda1, String moneda2) throws Exception {

        String URL = "https://v6.exchangerate-api.com/v6/312a7d3f7199d1cd8188ac16/pair/" + moneda1 + "/" + moneda2;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            Gson gson = new Gson();
            Moneda moneda = gson.fromJson(json, Moneda.class);

            double tasaConversion = moneda.conversion_rate();
            System.out.println("La tasa de conversión de las monedas seleccionadas es la siguiente:\n" +
                    "1 " + moneda1 + " = " + tasaConversion + " " + moneda2 + ".");
            return new Moneda(moneda1, moneda2, tasaConversion);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static double convertirMoneda(double monto, Moneda moneda) {
        return monto * moneda.conversion_rate();
    }
}
