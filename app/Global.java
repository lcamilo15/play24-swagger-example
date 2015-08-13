import com.wordnik.swagger.converter.ModelConverters;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import ignores.IgnoreConverterScala;

/**
 * Created by lcamilo on 8/8/15.
 */
public class Global extends GlobalSettings {

    @Override
    public void beforeStart(Application app) {
        Logger.info("Registering custom converter");
        ModelConverters.addConverter(new IgnoreConverterScala(), true);
    }

    @Override
    public void onStart(Application app) {
        Logger.info("Application has started");
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
}
