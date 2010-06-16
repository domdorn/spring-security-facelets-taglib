package com.dominikdorn.ssft;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class AuthorityEvaluatorTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testParseAuthorities_twoRoles_with_space() throws Exception {
        String input = "ROLE_VISITOR ROLE_MODERATOR";

        Set<String> result = AuthorityEvaluator.parseAuthorities(input);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("ROLE_VISITOR"));
        assertTrue(result.contains("ROLE_MODERATOR"));
    }

    @Test
    public void testParseAuthorities_twoRoles_with_semicolon() throws Exception {
        String input = "ROLE_VISITOR;ROLE_MODERATOR";

        Set<String> result = AuthorityEvaluator.parseAuthorities(input);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("ROLE_VISITOR"));
        assertTrue(result.contains("ROLE_MODERATOR"));
    }

    @Test
    public void testParseAuthorities_twoRoles() throws Exception {
        String input = "ROLE_VISITOR,ROLE_MODERATOR";

        Set<String> result = AuthorityEvaluator.parseAuthorities(input);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("ROLE_VISITOR"));
        assertTrue(result.contains("ROLE_MODERATOR"));
    }

    @Test
    public void testParseAuthorities_nullRoles() throws Exception {
        String input = null;

        Set<String> result = AuthorityEvaluator.parseAuthorities(input);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testParseAuthorities_emptyRoles() throws Exception {
        String input = "";

        Set<String> result = AuthorityEvaluator.parseAuthorities(input);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
	public void testIfAnyGranted_noMatches()
	{
        Set<String> grantedAuthorities = AuthorityEvaluator.parseAuthorities("ROLE_ADMIN, ROLE_USER");
		assertFalse(AuthorityEvaluator.ifAnyGranted("ROLE_VISITOR,ROLE_MODERATOR", grantedAuthorities));
	}


	@Test
	public void testIfAnyGranted_oneMatches()
	{
        Set<String> grantedAuthorities = AuthorityEvaluator.parseAuthorities("ROLE_ADMIN, ROLE_USER");
		assertTrue(AuthorityEvaluator.ifAnyGranted("ROLE_VISITOR,ROLE_MODERATOR,ROLE_ADMIN", grantedAuthorities));
	}


	/*
		test if ROLE_ADMIN is not granted, as it is by our test data, this should return false
	 */

	@Test
	public void testIfNotGranted_roleAdmin() {
        Set<String> grantedAuthorities = AuthorityEvaluator.parseAuthorities("ROLE_ADMIN, ROLE_USER");
	    assertFalse(AuthorityEvaluator.ifNotGranted("ROLE_ADMIN", grantedAuthorities));
	}

	/*
		test if ROLE_VISITOR is not granted, as this is not within our default test data, this should return true
	 */
	@Test
	public void testIfNotGranted_roleVisitor()
	{
        Set<String> grantedAuthorities = AuthorityEvaluator.parseAuthorities("ROLE_ADMIN, ROLE_USER");
		assertTrue(AuthorityEvaluator.ifNotGranted("ROLE_VISITOR", grantedAuthorities));
	}

	@Test
	public void testIfAllGranted_notAllRolesGranted() {
        Set<String> grantedAuthorities = AuthorityEvaluator.parseAuthorities("ROLE_ADMIN, ROLE_USER");
		assertFalse(AuthorityEvaluator.ifAllGranted("ROLE_ADMIN,ROLE_USER,ROLE_MODERATOR", grantedAuthorities));
	}

	@Test
	public void testIfAllGranted_allRolesGranted() {
        Set<String> grantedAuthorities = AuthorityEvaluator.parseAuthorities("ROLE_ADMIN, ROLE_USER");
        assertTrue(AuthorityEvaluator.ifAllGranted("ROLE_ADMIN,ROLE_USER", grantedAuthorities));
	}


	@Test
	public void testIfAllGranted_noRolesSpecified(){
        Set<String> grantedAuthorities = AuthorityEvaluator.parseAuthorities("ROLE_ADMIN, ROLE_USER");
        assertFalse(AuthorityEvaluator.ifAllGranted("", grantedAuthorities));
	}

	@Test
	public void testIfAllGranted_nullGiven(){
        Set<String> grantedAuthorities = AuthorityEvaluator.parseAuthorities("ROLE_ADMIN, ROLE_USER");
        assertFalse(AuthorityEvaluator.ifAllGranted(null, grantedAuthorities));
	}
}
