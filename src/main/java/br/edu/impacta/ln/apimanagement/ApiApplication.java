package br.edu.impacta.ln.apimanagement;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title="Quarkus OpenAPI | Impacta API Management",
        version = "0.1.0"
    ),
	servers = {
	    @Server(url = "http://localhost:8080")
	}
)
public class ApiApplication extends Application {

}
