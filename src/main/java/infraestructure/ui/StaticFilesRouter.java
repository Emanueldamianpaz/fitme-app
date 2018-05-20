
package infraestructure.ui;

import static spark.Spark.get;
import static spark.Spark.halt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.github.racc.tscg.TypesafeConfig;
import com.google.inject.Inject;
import infraestructure.Router;
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

    @Inject
    public StaticFilesRouter(WebJarAssetLocator locator) {
        this.locator = locator;
    }

    public RouteGroup routes() {
        return () -> {
            configureWebJars();
            configureFolder("i18n/:file", "i18n");
            configureFolder("css/:file", "public/css");
            configureFolder("js/:file", "public/js");
            configureFolder("js/services/:file", "public/js/services");
            configureFolder("js/controllers/:file", "public/js/controllers");
            configureFolder("js/routes/:file", "public/js/routes");
            configureFolder("js/routes/external/:file", "public/js/routes/external");
            configureFolder("js/routes/internal/:file", "public/js/routes/internal");
            configureFolder("img/:file", "public/img");
            configureFolder("img/avatars/:file", "public/img/avatars");
            configureFolder("views/:file", "public/views");
            configureFolder("views/common/:file", "public/views/common");
            configureFolder("views/common/layouts/:file", "public/views/common/layouts");
            configureFolder("views/common/sidebar-nav/:file", "public/views/common/sidebar-nav");
            configureFolder("views/components/:file", "public/views/components");
            configureFolder("views/icons/:file", "public/views/icons");
            configureFolder("views/pages/:file", "public/views/pages");
            configureFile("index.html", "public/index.html");
            configureFile("", "public/index.html");
        };
    }

    @Override
    public String path() {
        return "/";
    }

    private void configureWebJars() {

        String fullContext = "webjars/:library/*";

        get(fullContext, (req, res) -> {

            String library = req.params("library");
            String relativePath = StringUtils.removePattern(req.uri(), "/fitme/webjars/" + library);

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
