package br.edu.impacta.ln.apimanagement;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/api/customers")
public class CustomerResource {
	private Set<Customer> customers = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public CustomerResource() {
    	customers.add(new Customer(1, "João", LocalDate.of(1985, 01, 20)));
    	customers.add(new Customer(2, "Vinícius", LocalDate.of(1980, 02, 20)));
    }


    @Operation(summary = "Retorna todos os clientes cadastrados")
    @APIResponse(responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(
                implementation = Customer.class,
                type = SchemaType.ARRAY)))
    @GET
    public Set<Customer> list() {
        return customers;
    }
    
    
    @Operation(summary = "Retorna o cliente cadastrado a partir do ID recebido via parâmetro")
    @APIResponses(
		value = {
			@APIResponse(
				responseCode = "200",
				description = "Cliente encontrado com sucesso",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.OBJECT))
			),
			@APIResponse(
				responseCode = "400",
		        description = "Retorna um erro caso o payload seja inválido",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.ARRAY))
			),
			@APIResponse(
				responseCode = "404",
				description = "Cliente não encontrado.",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.OBJECT))
			),
		}
    )    	
    @GET
    @Path(value = "/{id}")
    public Customer getById(@PathParam("id") String id) {
    	if (id == null) {
    		throw new BadRequestException();
    	}

    	Customer customer = null;

    	for (Customer singleCustomer : customers) {
    		if (singleCustomer.getId() == Integer.parseInt(id)) {
    			customer = singleCustomer;
    			break;
    		}
    	}
    	
    	if (customer == null) {
    		throw new NotFoundException("Customer not found.");
    	}
    	
    	return customer;
    }

    
    @Operation(summary = "Retorna o cliente cadastrado a partir do nome recebido via parâmetro")
    @APIResponses(
		value = {
			@APIResponse(
				responseCode = "200",
				description = "Cliente encontrado com sucesso",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.OBJECT))
			),
			@APIResponse(
				responseCode = "400",
		        description = "Retorna um erro caso o payload seja inválido",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.ARRAY))
			),
			@APIResponse(
				responseCode = "404",
				description = "Cliente não encontrado.",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.OBJECT))
			),
		}
    )
    @GET
    @Path(value = "/name/{name}")
    public Customer getByName(@PathParam("name") String name) {
    	if (name == null) {
    		throw new BadRequestException();
    	}
    	
    	Customer customer = null;

    	for (Customer singleCustomer : customers) {
    		if (singleCustomer.getName().equals(name)) {
    			customer = singleCustomer;
    			break;
    		}    		
    	}
    	
    	if (customer == null) {
    		throw new NotFoundException("Customer not found.");
    	}
    	
    	return customer;
    }


    @Operation(summary = "Cadastra um cliente")
    @APIResponses(
		value = {
			@APIResponse(
				responseCode = "200",
		        description = "Retorna todos os todos os clientes cadastrados, incluindo o novo cliente",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.ARRAY))
			),
			@APIResponse(
				responseCode = "400",
		        description = "Retorna um erro caso o payload seja inválido",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.ARRAY))
			),
		}
    )
    @POST
    public Set<Customer> add(Customer customer) {
    	if (customer == null) {
    		throw new BadRequestException();
    	}

    	customers.add(customer);
        return customers;
    }
    
    
    @Operation(summary = "Atualiza um cliente")
    @APIResponses(
    		value = {
				@APIResponse(
					responseCode = "200",
			        description = "Retorna todos os todos os clientes cadastrados, já com o cliente atualizado",
			        content = @Content(
			            mediaType = MediaType.APPLICATION_JSON,
			            schema = @Schema(
			                implementation = Customer.class,
			                type = SchemaType.ARRAY))
				),
    			@APIResponse(
    				responseCode = "400",
    		        description = "Retorna um erro caso o payload seja inválido",
    		        content = @Content(
    		            mediaType = MediaType.APPLICATION_JSON,
    		            schema = @Schema(
    		                implementation = Customer.class,
    		                type = SchemaType.ARRAY))
    			),
    			@APIResponse(
					responseCode = "404",
					description = "Cliente não encontrado.",
			        content = @Content(
			            mediaType = MediaType.APPLICATION_JSON,
			            schema = @Schema(
			                implementation = Customer.class,
			                type = SchemaType.OBJECT))
				),
    		}
        )
    @PUT
    public Set<Customer> update(Customer customer) {
    	if (customer == null) {
    		throw new BadRequestException();
    	}
    	
    	boolean customerFound = false;

    	for (Customer singleCustomer : customers) {
    		if (singleCustomer.getId() == customer.getId()) {
    			customerFound = true;
    			customers.remove(singleCustomer);
    			customers.add(customer);
    			break;
    		}
    	}
    	
    	if (!customerFound) {
    		throw new NotFoundException("Customer not found.");
    	}

        return customers;
    }
    

    @Operation(summary = "Deleta um cliente a partir do objeto no body da requisição")
    @APIResponses(
		value = {
			@APIResponse(
				responseCode = "200",
		        description = "Retorna a lista atualizada de todos os clientes cadastrados (com exceção do cliente removido)",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.ARRAY))
			),
			@APIResponse(
				responseCode = "400",
		        description = "Retorna um erro caso o payload seja inválido",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.ARRAY))
			),
			@APIResponse(
				responseCode = "404",
				description = "Cliente não encontrado.",
		        content = @Content(
		            mediaType = MediaType.APPLICATION_JSON,
		            schema = @Schema(
		                implementation = Customer.class,
		                type = SchemaType.OBJECT))
			),
		}
    )
    @DELETE
    public Set<Customer> delete(Customer customer) {
    	if (customer == null) {
    		throw new BadRequestException();
    	}
    	

    	boolean customerRemoved = customers.removeIf(existingCustomer -> existingCustomer.getName().contentEquals(customer.getName()));

    	if (!customerRemoved) {
    		throw new NotFoundException("Customer not found.");
    	}
    	
        return customers;
    }
}