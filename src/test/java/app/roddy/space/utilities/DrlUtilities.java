package app.roddy.space.utilities;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.List;

/**
 * Utility class for deploying DRL files.
 */
public class DrlUtilities {

    /**
     * Fires rules for the given scope, using the specified input object.
     * @param scope the scope
     * @param input the input
     */
    public static void fireRulesForScope(String scope, Object input) {
        KieBase kBase = getKieBase(scope);
        KieSession session = kBase.newKieSession();
        try {
            session.insert(input);
            session.fireAllRules();
        } finally {
            session.dispose();
        }
    }

    /**
     * Fires rules for the given scope, using the specified input objects.
     * @param scope the scope
     * @param inputs the inputs
     */
    public static void fireRulesForScope(String scope, List<?> inputs) {
        KieBase kBase = getKieBase(scope);
        KieSession session = kBase.newKieSession();
        try {
            inputs.forEach(session::insert);
            session.fireAllRules();
        } finally {
            session.dispose();
        }
    }

    /**
     * Fires rules for the specified scope. Passes into the session the specified input object, and adds a global with
     * the specified name.
     * @param scope the scope
     * @param input the input object
     * @param globalName the global's name
     * @param global the global
     */
    public static void fireRulesForScope(String scope, Object input, String globalName, Object global) {
        KieBase kBase = getKieBase(scope);
        KieSession session = kBase.newKieSession();
        try {
            session.insert(input);
            session.setGlobal(globalName, global);
            session.fireAllRules();
        } finally {
            session.dispose();
        }
    }

    /**
     * Fires rules for the specified scope. Passes into the session the specified input objects, and adds a global with
     * the specified name.
     * @param scope the scope
     * @param inputs the input objects
     * @param globalName the global's name
     * @param global the global
     */
    public static void fireRulesForScope(String scope, List<?> inputs, String globalName, Object global) {
        KieBase kBase = getKieBase(scope);
        KieSession session = kBase.newKieSession();
        try {
            inputs.forEach(session::insert);
            session.setGlobal(globalName, global);
            session.fireAllRules();
        } finally {
            session.dispose();
        }
    }

    private static KieBase getKieBase(String name) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();

        return kContainer.getKieBase(name);
    }
}
