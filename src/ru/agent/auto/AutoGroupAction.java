package ru.agent.auto;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;

public class AutoGroupAction extends AnAction {


    public void actionPerformed(AnActionEvent e) {
        final ModuleManager mm = ModuleManager.getInstance(getEventProject(e));

        Application app = ApplicationManager.getApplication();
        app.runWriteAction(new Runnable() {
            @Override
            public void run() {
                ModifiableModuleModel modifiableModel = mm.getModifiableModel();
                Module[] modules = modifiableModel.getModules();
                for (Module module : modules) {
                    modifiableModel.setModuleGroupPath(module, new String[]{"libs"});
                }

                modifiableModel.commit();
            }
        });

    }
}
