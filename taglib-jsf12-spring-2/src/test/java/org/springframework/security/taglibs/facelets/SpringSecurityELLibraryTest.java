package org.springframework.security.taglibs.facelets;




import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.security.providers.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.userdetails.User;
import org.springframework.security.util.AuthorityUtils;


/**
 * @author Dominik Dorn
 */
public class SpringSecurityELLibraryTest {
  private static final String KEY = "123456789";
  
	private SecurityContext context;


	private final GrantedAuthority ROLE_ADMIN = new GrantedAuthorityImpl("ROLE_ADMIN");
	private final GrantedAuthority ROLE_USER = new GrantedAuthorityImpl("ROLE_USER");






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
		Authentication auth = new PreAuthenticatedAuthenticationToken(principal, new Object(), AuthorityUtils.commaSeparatedStringToAuthorityArray("ROLE_ADMIN,ROLE_USER"));

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
	
  @Test
  public void testIsAuthenticated_authenticated() {
    assertTrue(SpringSecurityELLibrary.isAuthenticated());
  }

  @Test
  public void testIsAuthenticated_annonymous() {
    Authentication anonymousAuth = new AnonymousAuthenticationToken(KEY, "anonymousUser", getAnonymousAuthorities());
    when(context.getAuthentication()).thenReturn(anonymousAuth);
    assertFalse(SpringSecurityELLibrary.isAuthenticated());
  }

  @Test
  public void testIsAnonymous_anonymous() {
    Authentication anonymousAuth = new AnonymousAuthenticationToken(KEY, "anonymousUser", getAnonymousAuthorities());
    when(context.getAuthentication()).thenReturn(anonymousAuth);
    assertTrue(SpringSecurityELLibrary.isAnonymous());
  }

  @Test
  public void testIsAnonymous_authenticated() {
    assertFalse(SpringSecurityELLibrary.isAnonymous());
  }

  private GrantedAuthority[] getAnonymousAuthorities() {
    GrantedAuthority[] authorities = new GrantedAuthority[1];
    authorities[0] = new GrantedAuthorityImpl("ROLE_ANONYMOUS");
    return authorities;
  }
}
