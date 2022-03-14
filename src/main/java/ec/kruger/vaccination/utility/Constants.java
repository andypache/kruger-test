package ec.kruger.vaccination.utility;

/**
 * @author andres.pacheco
 * <p>
 * Keep values not variables into system
 */
public final class Constants {
    public static final String REGEX_MAIL_VALIDATION = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_NAMES_VALIDATION = "^[a-zA-ZäáàëéèíìöóòúùñçÑÁÉÍÓÚ(){} ]*$";
    public static final String DATE_FORMAT= "yyyy/MM/dd";
}
