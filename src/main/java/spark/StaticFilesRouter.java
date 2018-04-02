
package spark;

import static spark.Spark.get;
import static spark.Spark.halt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import infraestructure.conf.Enviroment;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import com.google.common.io.Files;

import org.webjars.WebJarAssetLocator;
import spark.utils.IOUtils;

public class StaticFilesRouter implements Router {

    private final MimeTypes jettyMimeType = new MimeTypes();
    private final String appContext = Enviroment.APP_CONTEXT.getProperty();

    private WebJarAssetLocator locator;

    @Inject
    public StaticFilesRouter(WebJarAssetLocator locator) {
        this.locator = locator;
    }

    @Override
    public void routeServices() {

        configureWebJars(appContext);

        configureFolder(appContext + "/i18n/:file", "i18n");

        configureFolder(appContext + "/css/:file", "public/css");

        configureFolder(appContext + "/js/:file", "public/js");
        configureFolder(appContext + "/js/services/:file", "public/js/services");
        configureFolder(appContext + "/js/controllers/:file", "public/js/controllers");

        configureFolder(appContext + "/img/:file", "public/img");
        configureFolder(appContext + "/img/avatars/:file", "public/img/avatars");

        configureFolder(appContext + "/views/:file", "public/views");
        configureFolder(appContext + "/views/common/:file", "public/views/common");
        configureFolder(appContext + "/views/common/layouts/:file", "public/views/common/layouts");
        configureFolder(appContext + "/views/common/sidebar-nav/:file", "public/views/common/sidebar-nav");

        configureFolder(appContext + "/views/components/:file", "public/views/components");
        configureFolder(appContext + "/views/icons/:file", "public/views/icons");
        configureFolder(appContext + "/views/pages/:file", "public/views/pages");


        configureFile(appContext + "/index.html", "public/index.html");
        configureFile(appContext + "/", "public/index.html");
    }


    private void configureWebJars(String appContext) {

        String fullContext = appContext + "/webjars/:library/*";

        get(fullContext, (req, res) -> {

            String library = req.params("library");
            String relativePath = StringUtils.removePattern(req.uri(), "/" + appContext + "/webjars/" + library);

            String fullPath = locator.getFullPath(library, relativePath);

            return writeFileToOutput(fullPath, res);
        });
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
