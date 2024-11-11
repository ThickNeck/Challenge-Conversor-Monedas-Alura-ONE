package ListaCodigos;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ListaCodigos {
    private static final String URL_CODIGOS = "https://v6.exchangerate-api.com/v6/312a7d3f7199d1cd8188ac16/codes";

    public static String[] getCodigosDeMonedas() {
        try {
            URL url = new URL(URL_CODIGOS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

            String linea;
            while ((linea = reader.readLine()) != null) {
                response.append(linea);
            }
            reader.close();

            Gson gson = new Gson();
            CodigosMonedas json = gson.fromJson(response.toString(), CodigosMonedas.class);

            List<List<String>> codigos = json.getSupportedCodes();
            String[] listaCodigos = new String[codigos.size()];
            for (int i = 0; i < codigos.size(); i++) {
                listaCodigos[i] = codigos.get(i).get(0);
            }
            return listaCodigos;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return new String[0];
        }
    }
}
