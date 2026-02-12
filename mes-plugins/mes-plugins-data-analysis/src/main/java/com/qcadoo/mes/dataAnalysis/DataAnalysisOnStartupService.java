package com.qcadoo.mes.dataAnalysis;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.plugin.api.PluginStateChangeListener;

public class DataAnalysisOnStartupService implements PluginStateChangeListener {

    private TranslationService translationService;

    @Override
    public void enabled() {
        // Initialize data analysis functionality
    }

    @Override
    public void disabled() {
        // Clean up data analysis functionality
    }

    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    }

}
