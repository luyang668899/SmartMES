package com.qcadoo.mes.digitalTwin;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.plugin.api.PluginStateChangeListener;

public class DigitalTwinOnStartupService implements PluginStateChangeListener {

    private TranslationService translationService;

    @Override
    public void enabled() {
        // Initialize digital twin functionality
    }

    @Override
    public void disabled() {
        // Clean up digital twin functionality
    }

    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    }

}
