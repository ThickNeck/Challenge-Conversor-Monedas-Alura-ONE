import Conversor.ConversorMonedas;
import Conversor.Moneda;
import Historial.HistorialConversor;
import ListaCodigos.ListaCodigos;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        HistorialConversor historial = new HistorialConversor();

        try {
            while (true) {

                String opcionInit;
                System.out.print("""
                        -----------------------------------
                        ¡Bienvenido a nuestro conversor de monedas!
                        Si deseás convertir el monto de una moneda presioná 1.
                        Si deseás ver tu historial de conversiones presioná 2.
                        Si deseás salir presioná 0.
                        -->\s""");
                opcionInit = entrada.nextLine();
                if (Objects.equals(opcionInit, "0")) {
                    System.out.println("¡Gracias por usar nuestro conversor! ¡Un abrazo! :D");
                    break;
                }
                switch (opcionInit) {
                    case "1" -> convertir(historial);
                    case "2" -> historial.imprimirHistorial();
                    default -> System.out.println("Dato no válido, revisá las opciones.");
                }
            }
        } catch (Exception e) {
            System.out.println("-----------------------------------");
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }

    public static String seleccionarMoneda1(Scanner entrada) {
        String[] codigos = ListaCodigos.getCodigosDeMonedas();
        String moneda1 = null;

        while (moneda1 == null || !Arrays.asList(codigos).contains(moneda1)) {
            System.out.print("""
                -----------------------------------
                ¿Qué moneda querés convertir?
                1 - Peso Argentino (ARS)
                2 - Bolívar Boliviano (BOB)
                3 - Real Brasilero (BRL)
                4 - Peso Chileno (CLP)
                5 - Peso Colombiano (COP)
                6 - Dólar (USD)
                7 - Otra Moneda
                -->\s""");
            String opcion = entrada.nextLine();
            switch (opcion) {
                case "1" -> moneda1 = "ARS";
                case "2" -> moneda1 = "BOB";
                case "3" -> moneda1 = "BRL";
                case "4" -> moneda1 = "CLP";
                case "5" -> moneda1 = "COP";
                case "6" -> moneda1 = "USD";
                case "7" -> {
                    System.out.print("Ingresá el código de la moneda (Ej. Yuan Chino = CNY, Libra Malvinense = FKP, etc.): ");
                    moneda1 = entrada.nextLine().toUpperCase();

                    if (!Arrays.asList(codigos).contains(moneda1)) {
                        System.out.println("No se pudo encontrar la moneda, revisá que el código sea válido.");
                    }
                }
                default -> {
                    System.out.println("Dato no válido, revisá las opciones.");
                    moneda1 = null;
                }
            }
        }
        return moneda1;
    }

    public static String seleccionarMoneda2(Scanner entrada) {
        String[] codigos = ListaCodigos.getCodigosDeMonedas();
        String moneda2 = null;

        while (moneda2 == null || !Arrays.asList(codigos).contains(moneda2)) {
            System.out.print("""
                -----------------------------------
                ¿A qué moneda deseás convertirla?
                1 - Peso Argentino (ARS)
                2 - Bolívar Boliviano (BOB)
                3 - Real Brasilero (BRL)
                4 - Peso Chileno (CLP)
                5 - Peso Colombiano (COP)
                6 - Dólar (USD)
                7 - Otra Moneda
                -->\s""");
            String opcion = entrada.nextLine();
            switch (opcion) {
                case "1" -> moneda2 = "ARS";
                case "2" -> moneda2 = "BOB";
                case "3" -> moneda2 = "BRL";
                case "4" -> moneda2 = "CLP";
                case "5" -> moneda2 = "COP";
                case "6" -> moneda2 = "USD";
                case "7" -> {
                    System.out.print("Ingresá el código de la moneda (Ej. Yuan Chino = CNY, Libra Malvinense = FKP, etc.): ");
                    moneda2 = entrada.nextLine().toUpperCase();
                }
                default -> {
                    System.out.println("Dato no válido, revisá las opciones.");
                    moneda2 = null;
                }
            }
        }
        return moneda2;
    }

    public static void convertir(HistorialConversor historial) {
        try {
            Scanner entrada = new Scanner(System.in);
            String moneda1;
            String moneda2;

            do {
                moneda1 = seleccionarMoneda1(entrada);
            } while (moneda1 == null);

            do {
                moneda2 = seleccionarMoneda2(entrada);
            } while (moneda2 == null);

            Double monto = null;
            do {
                System.out.println("-----------------------------------");
                System.out.print("Ingresá el monto a convertir: ");

                if (entrada.hasNextDouble()) {
                    monto = entrada.nextDouble();
                } else {
                    System.out.println("Por favor, ingresá un número válido.");
                    entrada.nextLine();
                }

                if (monto != null && monto <= 0) {
                    System.out.println("El monto debe ser mayor que 0.");
                    monto = null;
                }
            } while (monto == null);
            entrada.nextLine();

            Moneda moneda = ConversorMonedas.obtenerTasaConversion(moneda1, moneda2);
            double montoConvertido = ConversorMonedas.convertirMoneda(monto, moneda);
            System.out.println(monto + " " + moneda1 + (monto == 1 ? " es equivalente a " : " son equivalentes a ") + montoConvertido + " " + moneda2 + ".");

            historial.agregarConversion(moneda1, moneda2, monto, moneda.conversion_rate(), montoConvertido);

        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }
}
