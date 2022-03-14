package ec.kruger.vaccination.utility;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author andres.pacheco
 * <p>
 * Service for get data message
 */
@Service
public final class InternationalizedMessages {

    private static MessageSource messageSource;

    private InternationalizedMessages(MessageSource messageSource) {
        InternationalizedMessages.messageSource = messageSource;
    }

    /**
     * Get messages from properties
     *
     * @param code Code message
     * @param args Params to message
     * @return Message
     */
    public static String getMessage(String code, Object args) {
        Object[] params = null;
        if (args instanceof Object[]) {
            params = (Object[]) args;
        } else {
            if (args != null) {
                params = new Object[]{args};
            }
        }
        return messageSource.getMessage(code, params, "Not found message", LocaleContextHolder.getLocale());
    }
}