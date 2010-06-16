package com.dominikdorn.ssft;

import java.util.Set;
import java.util.TreeSet;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class AuthorityEvaluator {

    public static Set<String> parseAuthorities(String grantedRoles) {
        Set<String> parsedAuthorities = new TreeSet<String>();
        if (grantedRoles == null || grantedRoles.isEmpty()) {
            return parsedAuthorities;
        }

        String[] parsedAuthoritiesArr;
        if(grantedRoles.contains(",")){
            parsedAuthoritiesArr = grantedRoles.split(",");
        } else {
             parsedAuthoritiesArr = new String[]{grantedRoles};
        }

        // adding authorities to set (could pssible be done better!)
        for (String auth : parsedAuthoritiesArr)
            parsedAuthorities.add(auth.trim());
        return parsedAuthorities;
    }



    public static boolean ifAnyGranted(final String roles, Set<String> authorities){
        Set<String> parsedAuthorities = parseAuthorities(roles);
		if (parsedAuthorities.isEmpty())
			return false;



		for (String authority : authorities) {
			if (parsedAuthorities.contains(authority))
				return true;
		}
		return false;
    }

    public static boolean ifAllGranted(final String requiredRoles, Set<String> authorities) {
        // parse required roles into list
		Set<String> requiredAuthorities = parseAuthorities(requiredRoles);
		if (requiredAuthorities.isEmpty())
			return false;

		// iterate over required roles,
		for(String requiredAuthority : requiredAuthorities)
		{
			// check if required role is inside granted roles
			// if not, return false
			if(!authorities.contains(requiredAuthority)) {
				return false;
			}
		}
		return true;
    }

    public static boolean ifNotGranted(final String notGrantedRoles, Set<String> authorities) {
		Set<String> parsedAuthorities = parseAuthorities(notGrantedRoles);
		if (parsedAuthorities.isEmpty())
			return true;


		for (String authority : authorities) {
			if (parsedAuthorities.contains(authority))
				return false;
		}
		return true;
	}


    
}
