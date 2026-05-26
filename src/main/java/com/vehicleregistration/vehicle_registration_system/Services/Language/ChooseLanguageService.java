package com.vehicleregistration.vehicle_registration_system.Services.Language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ChooseLanguageService {
    private final MessageSource messageSource;

    @Autowired
    public ChooseLanguageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // For messages without parameters
    // Example: getMessage("vehicle.not.approved")
    public String getAcceptedLanguageMessage(String code){
        return messageSource.getMessage(code, null, code, LocaleContextHolder.getLocale());
    }

    // For messages with parameters — {0}, {1} etc.
    // Example: getMessage("vehicle.not.found", 5)
    // Returns: "Vehicle not found with id: 5"
    public String getAcceptedLanguageMessage(String code, Object... args) {
        return messageSource.getMessage(code, (args != null && args.length > 0) ? args : null,
                code ,LocaleContextHolder.getLocale());
    }







    }
