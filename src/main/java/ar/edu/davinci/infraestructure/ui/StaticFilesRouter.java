
package ar.edu.davinci.infraestructure.ui;

import static spark.Spark.get;
import static spark.Spark.halt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import ar.edu.davinci.infraestructure.Router;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;

import com.google.common.io.Files;

import org.webjars.WebJarAssetLocator;
import spark.Response;
import spark.RouteGroup;
import spark.utils.IOUtils;

public class StaticFilesRouter extends Router {

    private final MimeTypes jettyMimeType = new MimeTypes();
    private WebJarAssetLocator locator;
    private final String contextUI = "ui/";

    @Inject
    public StaticFilesRouter(WebJarAssetLocator locator) {
        this.locator = locator;
    }

    public RouteGroup routes() {
        return () -> {
            configureWebJars();
            configureFolder(contextUI + "i18n/:file", "i18n");
            configureFolder(contextUI + "css/:file", "public/css");

            configureFolder(contextUI + "js/:file", "public/js");
            configureFolder(contextUI + "js/services/:file", "public/js/services");
            configureFolder(contextUI + "js/controllers/:file", "public/js/controllers");
            configureFolder(contextUI + "js/routes/:file", "public/js/routes");
            configureFolder(contextUI + "js/routes/external/:file", "public/js/routes/external");
            configureFolder(contextUI + "js/routes/internal/:file", "public/js/routes/internal");

            configureFolder(contextUI + "img/:file", "public/img");
            configureFolder(contextUI + "img/avatars/:file", "public/img/avatars");

            configureFolder(contextUI + "views/:file", "public/views");
            configureFolder(contextUI + "views/common/:file", "public/views/common");
            configureFolder(contextUI + "views/common/layouts/:file", "public/views/common/layouts");
            configureFolder(contextUI + "views/common/sidebar-nav/:file", "public/views/common/sidebar-nav");

            configureFolder(contextUI + "views/app/:file", "public/views/app");
            configureFolder(contextUI + "views/app/exercises/:file", "public/views/app/exercises");
            configureFolder(contextUI + "views/app/nutritions/:file", "public/views/app/nutritions");
            configureFolder(contextUI + "views/app/routines/:file", "public/views/app/routines");
            configureFolder(contextUI + "views/app/users/:file", "public/views/app/users");

            configureFolder(contextUI + "views/fitme/:file", "public/views/fitme");

            configureFolder(contextUI + "views/components/:file", "public/views/components");
            configureFolder(contextUI + "views/components/modals/routines/:file", "public/views/components/modals/routines");
            configureFolder(contextUI + "views/components/modals/exercises/:file", "public/views/components/modals/exercises");
            configureFolder(contextUI + "views/components/modals/nutritions/:file", "public/views/components/modals/nutritions");
            configureFolder(contextUI + "views/components/fragments/:file", "public/views/components/fragments");

            configureFolder(contextUI + "views/icons/:file", "public/views/icons");
            configureFolder(contextUI + "views/pages/:file", "public/views/pages");
            configureFile(contextUI + "index.html", "public/index.html");
            configureFile(contextUI + "", "public/index.html");
        };
    }

    @Override
    public String path() {
        return "/";
    }

    private void configureWebJars() {

        String fullContext = contextUI + "webjars/:library/*";

        get(fullContext, (req, res) -> {

            String library = req.params("library");
            String relativePath = StringUtils.removePattern(req.uri(), "/fitme/" + contextUI + "webjars/" + library);

            String fullPath = locator.getFullPath(library, relativePath);
            return writeFileToOutput(fullPath, res);
        });
    }

    private void configureFolder(String fullContext, String resourceFolder) {
        get(fullContext, (req, res) -> {
            String[] parts = fullContext.split("/", -1);
            String filePath = resourceFolder + "/" + Arrays.stream(parts)
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
