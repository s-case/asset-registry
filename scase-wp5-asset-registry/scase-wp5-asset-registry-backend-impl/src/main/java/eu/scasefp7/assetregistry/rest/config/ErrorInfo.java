package eu.scasefp7.assetregistry.rest.config;


import java.util.HashMap;
import java.util.Map;

/**
 * Martin Moeller.
 * akquinet tech@spree GmbH
 */
public class ErrorInfo {

    private String globalError;
    private String detailedError;
    private Map<String, String> errors = new HashMap<>();

    public ErrorInfo() {
    }

    public ErrorInfo( final String globalError ) {
        this.globalError = excapeIllegalChars(globalError);
    }

    public void addError( String field, String message ) {
        errors.put( field, excapeIllegalChars(message) );
    }

    public void setError( String message ) {
        globalError = excapeIllegalChars(message);
    }

    public String getGlobalError() {
        return globalError;
    }
    
    public void setGlobalError(String error) {
        globalError = error;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getDetailedError() {
        return detailedError;
    }

    public void setDetailedError(String detailedError) {
        this.detailedError = excapeIllegalChars(detailedError);
    }
    
    public String toString() {
        return "{ \"globalError\": \"" + this.globalError + "\"}";
    }
    
    private String excapeIllegalChars(String s) {
        return s == null ? null : s.replace("\"", "\\\"");
    }
}
