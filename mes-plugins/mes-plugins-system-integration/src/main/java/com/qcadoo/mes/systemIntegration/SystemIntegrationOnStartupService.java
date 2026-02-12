package com.qcadoo.mes.systemIntegration;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.plugin.api.PluginStateChangeListener;

public class SystemIntegrationOnStartupService implements PluginStateChangeListener {

    private TranslationService translationService;

    @Override
    public void enabled() {
        // Initialize system integration functionality
    }

    @Override
    public void disabled() {
        // Clean up system integration functionality
    }

    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    }

}
