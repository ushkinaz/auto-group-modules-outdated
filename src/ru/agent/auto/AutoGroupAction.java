package ru.agent.auto;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Really dumb and dirty
 * todo: add configuration screen
 */
public class AutoGroupAction extends AnAction {

    /**
     * RegExp => group
     */
    private static Map<String, String[]> groupMappings;


    private static final String[] APPLICATION_GROUP  = new String[]{"application"};
    private static final String[] API_GROUP          = new String[]{"libs", "api"};
    private static final String[] SERVICES_GROUP     = new String[]{"libs", "services"};
    private static final String[] BEANS_GROUP        = new String[]{"libs", "beans"};
    private static final String[] EXT_GROUP          = new String[]{"libs", "ext"};
    private static final String[] SUPPLEMENTAL_GROUP = new String[]{"supplemental"};
    private static final String[] ASPECT_GROUP       = new String[]{"supplemental", "aspect"};
    private static final String[] DEFAULT_GROUP      = new String[]{"libs"};

    static {
        groupMappings = new HashMap<String, String[]>();
        groupMappings.put("abra", APPLICATION_GROUP);
        groupMappings.put("gamayun", APPLICATION_GROUP);
        groupMappings.put("kadabra", APPLICATION_GROUP);
        groupMappings.put("numel", APPLICATION_GROUP);
        groupMappings.put("psyduck", APPLICATION_GROUP);
        groupMappings.put("saiku", APPLICATION_GROUP);
        groupMappings.put("simurgh", APPLICATION_GROUP);
        groupMappings.put("swinub", APPLICATION_GROUP);
        groupMappings.put("watchdog", APPLICATION_GROUP);
        groupMappings.put("zubat", APPLICATION_GROUP);


        groupMappings.put(".*-api", API_GROUP);
        groupMappings.put(".*-services", SERVICES_GROUP);
        groupMappings.put(".*-beans", BEANS_GROUP);
        groupMappings.put(".*-ext", EXT_GROUP);

        groupMappings.put(".*\\.pom", SUPPLEMENTAL_GROUP);
        groupMappings.put("reports", SUPPLEMENTAL_GROUP);
        groupMappings.put("app\\.aggregator", SUPPLEMENTAL_GROUP);

        groupMappings.put(".*-aspect", ASPECT_GROUP);
    }

    public void actionPerformed(AnActionEvent e) {
        final ModuleManager mm = ModuleManager.getInstance(getEventProject(e));

        Application app = ApplicationManager.getApplication();
        app.runWriteAction(new Runnable() {
            @Override
            public void run() {
                ModifiableModuleModel modifiableModel = mm.getModifiableModel();
                Module[] modules = modifiableModel.getModules();
                for (Module module : modules) {
                    for (Map.Entry<String, String[]> mapping : groupMappings.entrySet()) {
                        String moduleName = module.getName();
                        if (moduleName.matches(mapping.getKey())) {
                            modifiableModel.setModuleGroupPath(module, mapping.getValue());
                            break;
                        }
                    }
                    modifiableModel.setModuleGroupPath(module, DEFAULT_GROUP);
                }
                modifiableModel.commit();
            }
        });

    }
}
