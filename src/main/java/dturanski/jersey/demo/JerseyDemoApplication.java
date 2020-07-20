package dturanski.jersey.demo;

import brave.Tracer;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class JerseyDemoApplication {
	private static Logger logger = LoggerFactory.getLogger(JerseyDemoApplication.class);

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "true");
		SpringApplication.run(JerseyDemoApplication.class, args);
	}


	@Component
	@ApplicationPath("/hello")
	class JerseyConfig extends ResourceConfig {
		public JerseyConfig() {
			register(HelloResource.class);
		}
	}


	@Path("/")
	@Component
	public static class HelloResource {

		private final Tracer tracer;

		public HelloResource(Tracer tracer) {
			this.tracer = tracer;
		}

		@GET
		@Produces("application/json")
		@Consumes("text/plain")
		public Map hello(@QueryParam("name") String name) {
			logger.debug("invoking hello");
			Map<String, String> map = new HashMap<>();
			map.put("greetings", "hello");
			map.put("to", name);
			return map;
		}
	}

}
