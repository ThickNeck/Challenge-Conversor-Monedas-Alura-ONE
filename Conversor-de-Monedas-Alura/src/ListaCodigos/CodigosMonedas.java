package ListaCodigos;

import java.util.List;

public class CodigosMonedas {
    private List<List<String>> supported_codes;

    public List<List<String>> getSupportedCodes() {
        return supported_codes;
    }

    public void setSupportedCodes(List<List<String>> supportedCodes) {
        this.supported_codes = supportedCodes;
    }
}
