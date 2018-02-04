
package spark;

import static spark.Spark.get;
import static spark.Spark.halt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import conf.Enviroment;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import com.google.common.io.Files;
import com.google.common.reflect.ClassPath;

import spark.utils.IOUtils;

public class StaticFilesRouter implements Router {

    private final MimeTypes jettyMimeType = new MimeTypes();
    private final String appContext = Enviroment.APP_CONTEXT.getProperty();

    @Override
    public void routeServices() {

        configureFolder(appContext + "/partials/:file", "public/partials");

        configureFolder(appContext + "/icons/:file", "public/icons");
        configureFolder(appContext + "/js/:file", "public/js");
        configureFolder(appContext + "/js/services/:file", "public/js/services");
        configureFolder(appContext + "/js/controllers/:file", "public/js/controllers");

        configureFolder(appContext + "/images/:file", "public/images");
        configureFolder(appContext + "/css/:file", "public/css");

        configureFile(appContext + "/index.html", "public/index.html");
        configureFile(appContext + "/", "public/index.html");
    }

    private void configureFolder(String fullContext, String resoureFolder) {
        get(fullContext, (req, res) -> {
            String[] parts = fullContext.split("/", -1);
            String filePath = resoureFolder + "/" + Arrays.stream(parts)
                    .filter(s -> ':' == s.charAt(0))
                    .map(s -> s.substring(1)).map(req::params)
                    .collect(Collectors.joining("/"));

            return writeFileToOutput(filePath, res);
        });
    }

    private void configureFile(String fullContext, String filePath) {
        get(fullContext, (req, res) -> writeFileToOutput(filePath, res));
    }

    private Object writeFileToOutput(String filePath, Response res) throws IOException {
        try (InputStream inputStream = StaticFilesRouter.class.getClassLoader().getResourceAsStream(filePath)) {

            if (inputStream == null) {
                halt(HttpStatus.NOT_FOUND_404, "Can't find file: '" + filePath + "'");
            }
            String mimeType = jettyMimeType.getMimeByExtension("." + Files.getFileExtension(filePath));
            res.type(mimeType);

            return IOUtils.toByteArray(inputStream);
        }
    }

}
