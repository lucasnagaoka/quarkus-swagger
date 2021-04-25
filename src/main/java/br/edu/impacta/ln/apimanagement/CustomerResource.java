package br.edu.impacta.ln.apimanagement;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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

@Path("/customers")
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
    @APIResponse(responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(
                implementation = Customer.class,
                type = SchemaType.OBJECT)))
    @GET
    @Path(value = "/{id}")
    public Customer getById(@PathParam("id") String id) {
    	Customer customer = null;

    	for (Customer singleCustomer : customers) {
    		if (singleCustomer.getId() == Integer.parseInt(id)) {
    			customer = singleCustomer;
    			break;
    		}    		
    	}
    	return customer;
    }
    
    @Operation(summary = "Retorna o cliente cadastrado a partir do nome recebido via parâmetro")
    @APIResponse(responseCode = "200",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(
                implementation = Customer.class,
                type = SchemaType.OBJECT)))
    @GET
    @Path(value = "/{name}")
    public Customer getByName(@PathParam("name") String name) {
    	Customer customer = null;

    	for (Customer singleCustomer : customers) {
    		if (singleCustomer.getName().equals(name)) {
    			customer = singleCustomer;
    			break;
    		}    		
    	}
    	return customer;
    }

    @Operation(summary = "Cadastra um cliente")
    @APIResponse(responseCode = "200",
        description = "Retorna todos os todos os clientes cadastrados, incluindo o novo cliente",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(
                implementation = Customer.class,
                type = SchemaType.ARRAY)))
    @POST
    public Set<Customer> add(Customer customer) {
    	customers.add(customer);
        return customers;
    }
    
    @Operation(summary = "Atualiza um cliente")
    @APIResponse(responseCode = "200",
        description = "Retorna todos os todos os clientes cadastrados, já com o cliente atualizado",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(
                implementation = Customer.class,
                type = SchemaType.ARRAY)))
    @PUT
    public Set<Customer> update(Customer customer) {
    	for (Customer singleCustomer : customers) {
    		if (singleCustomer.getId() == customer.getId()) {
    			customers.remove(singleCustomer);
    			customers.add(customer);
    			break;
    		}
    	}
        return customers;
    }

    @Operation(summary = "Deleta um cliente a partir do objeto no body da requisição")
    @APIResponse(responseCode = "200", //
        description = "Retorna a lista atualizada de todos os clientes cadastrados (com exceção do cliente removido)",
        content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = Customer.class,
                type = SchemaType.ARRAY)))
    @DELETE
    public Set<Customer> delete(Customer customer) {
    	customers.removeIf(existingCustomer -> existingCustomer.getName().contentEquals(customer.getName()));
        return customers;
    }
}