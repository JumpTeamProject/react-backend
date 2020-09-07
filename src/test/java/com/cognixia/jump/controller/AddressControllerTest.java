package com.cognixia.jump.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognixia.jump.model.Address;

class AddressControllerTest extends AbstractTest{

	private final String STARTING_URI = "http://localhost:8080/api";
	
//	@MockBean
//	private AddressRepository repo;
//	@MockBean
//	private MyUserDetailService userDetailsService;
//	
//	AddressController controller;
	
	@Override
	@Before
	public void setUp() {
	   super.setUp();
	}
	
	@WithMockUser
	@Test
	void getAddressById() throws Exception {
		String uri = STARTING_URI + "/address/{id}";
		
		Address address = new Address("127 test street", "Asheboro", "NC", "27205");
		address.setId(1L);
		Long id = 1L;
		
		MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON_VALUE) ).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    
	    String content = mvcResult.getResponse().getContentAsString();
	    Address output = super.mapFromJson(content, Address.class);
	    assertEquals(address.getId(), output.getId());
	    assertTrue(address.getStreet().equals(output.getStreet()));
	    assertTrue(address.getCity().equals(output.getCity()));
	    assertTrue(address.getState().equals(output.getState()));
	    assertTrue(address.getZip().equals(output.getZip()));
//			.andDo(print())
//			.andExpect( status().isOk() );
//			.andExpect( content().contentType( MediaType.APPLICATION_JSON ) )
//			.andExpect( jsonPath("$.id").value(address.getId()) )
//			.andExpect( jsonPath("$.street").value(address.getStreet()))
//			.andExpect( jsonPath("$.city").value(address.getCity()))
//			.andExpect( jsonPath("$.state").value(address.getState()))
//			.andExpect( jsonPath("$.zip").value(address.getZip()));
//		
//		verify(repo, times(1)).findById(id);
//		verifyNoMoreInteractions(repo);	
	}

}
