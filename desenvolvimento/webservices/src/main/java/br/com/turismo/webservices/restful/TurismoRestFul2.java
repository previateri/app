package br.com.turismo.webservices.restful;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.turismo.core.model.Usuario;
import br.com.turismo.core.service.UsuariosService;

@Path("/usuario")
@RequestScoped
public class TurismoRestFul {

	@Inject
	private UsuariosService cadastroUsuario;

	ObjectMapper mapper = new ObjectMapper();

	@GET
	@Path("/controleacesso/{token}/{email}/{senha}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response controleAcesso(@PathParam("token") String token,
			@PathParam("email") String email, @PathParam("senha") String senha) {
		Response response = null;
		try {
			Usuario usuario = cadastroUsuario.controleacesso(email, senha);
			if (usuario != null) {
				response = Response.ok()
						.entity(mapper.writeValueAsString(usuario)).build();
			} else {
				response = Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		return response;
	}

	//@PUT
	@GET
	@Path("/cadastrar/{token}/{nome}/{email}/{senha}/{alias}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrar(@PathParam("token") String token,
			@PathParam("nome") String nome, @PathParam("email") String email,
			@PathParam("senha") String senha, @PathParam("alias") String alias) {
		Response response = null;
		try {
			Usuario usuario = cadastroUsuario.cadastrar(new Usuario(nome,
					email, senha, alias));
			if (usuario != null) {
				response = Response.ok()
						.entity(mapper.writeValueAsString(usuario)).build();
			} else {
				response = Response.status(Status.PRECONDITION_FAILED).build();
			}
		} catch (Exception e) {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		return response;
	}

	//@POST
	@GET
	@Path("/atualizar/{token}/{nome}/{email}/{senha}/{alias}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizar(@PathParam("token") String token,
			@PathParam("nome") String nome, @PathParam("email") String email,
			@PathParam("senha") String senha, @PathParam("alias") String alias) {
		Response response = null;
		try {
			response = Response
					.ok()
					.entity(mapper.writeValueAsString(new Usuario(nome, email,
							senha, alias))).build();
		} catch (Exception e) {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		return response;
	}
	
	//@DELETE
	@GET
	@Path("/apagar/{token}/{nome}/{email}/{senha}/{alias}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response apagar(@PathParam("token") String token,
			@PathParam("nome") String nome, @PathParam("email") String email,
			@PathParam("senha") String senha, @PathParam("alias") String alias) {
		Response response = null;
		try {
			response = Response
					.ok()
					.entity(mapper.writeValueAsString(new Usuario(nome, email,
							senha, alias))).build();
		} catch (Exception e) {
			response = Response.status(Status.BAD_REQUEST).build();
		}
		return response;
	}
}
