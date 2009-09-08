package org.springframework.security.taglibs.facelets;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.adapters.PrincipalSpringSecurityUserToken;
import org.springframework.security.util.AuthorityUtils;
import org.springframework.security.userdetails.User;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * @author Dominik Dorn
 */
public class SpringSecurityELLibraryTest {
	private SecurityContext context;


	private final GrantedAuthority ROLE_ADMIN = new GrantedAuthorityImpl("ROLE_ADMIN");
	private final GrantedAuthority ROLE_USER = new GrantedAuthorityImpl("ROLE_USER");
	private final GrantedAuthority ROLE_MODERATOR = new GrantedAuthorityImpl("ROLE_MODERATOR");






	@org.junit.Before
	public void setUp() {

		context = mock(SecurityContext.class);


		SecurityContextHolder.setContext(context);

		User principal = new User("username",
				"password",
				/* enabled */ true,
				/* accountNonExpired */ true,
				/* credentialsNonExpired */ true,
				/* accountNonLocked */ true,
				new GrantedAuthority[]{ROLE_ADMIN, ROLE_USER});
		Authentication auth = new PrincipalSpringSecurityUserToken("someKey", "username", "password", AuthorityUtils.commaSeparatedStringToAuthorityArray("ROLE_ADMIN,ROLE_USER"), principal);

		when(context.getAuthentication()).thenReturn(auth);
	}

	@org.junit.After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testIfAnyGranted_noMatches()
	{
		assertFalse(SpringSecurityELLibrary.ifAnyGranted("ROLE_VISITOR,ROLE_MODERATOR"));
	}


	@Test
	public void testIfAnyGranted_oneMatches()
	{
		assertTrue(SpringSecurityELLibrary.ifAnyGranted("ROLE_VISITOR,ROLE_MODERATOR,ROLE_ADMIN"));
	}


	/*
		test if ROLE_ADMIN is not granted, as it is by our test data, this should return false
	 */

	@Test
	public void testIfNotGranted_roleAdmin() {
	    assertFalse(SpringSecurityELLibrary.ifNotGranted("ROLE_ADMIN"));
	}

	/*
		test if ROLE_VISITOR is not granted, as this is not within our default test data, this should return true
	 */
	@Test
	public void testIfNotGranted_roleVisitor()
	{
		assertTrue(SpringSecurityELLibrary.ifNotGranted("ROLE_VISITOR"));
	}

	@Test
	public void testIfAllGranted_notAllRolesGranted() {
		assertFalse(SpringSecurityELLibrary.ifAllGranted("ROLE_ADMIN,ROLE_USER,ROLE_MODERATOR"));
	}

	@Test
	public void testIfAllGranted_allRolesGranted() {
		assertTrue(SpringSecurityELLibrary.ifAllGranted("ROLE_ADMIN,ROLE_USER"));
	}


	@Test
	public void testIfAllGranted_noRolesSpecified(){
		assertFalse(SpringSecurityELLibrary.ifAllGranted(""));
	}

	@Test
	public void testIfAllGranted_nullGiven(){
		assertFalse(SpringSecurityELLibrary.ifAllGranted(null));
	}
}
