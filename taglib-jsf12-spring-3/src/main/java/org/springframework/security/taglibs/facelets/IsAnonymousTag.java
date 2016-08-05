package org.springframework.security.taglibs.facelets;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentConfig;

/**
 * Taglib to combine the Spring-Security Project with Facelets <p>
 *
 * This is the Class responsible for making the <p>
 * <code>
 *     &lt;sec:isAnonymous;&gt;
 *         The components you want to show only when the user is anonymous
 *     lt;/sec:isAnonymous&gt;
 * </code>
 * work.
 *
 *
 * @author Grzegorz Blaszczyk - http://www.blaszczyk-consulting.com/
 * @version %I%, %G%
 * @since 0.5
 */
public class IsAnonymousTag extends TagHandler
{

	public void apply(FaceletContext faceletContext, UIComponent uiComponent)
			throws IOException, FacesException, FaceletException, ELException {

		if(SpringSecurityELLibrary.isAnonymous()) {
			this.nextHandler.apply(faceletContext, uiComponent);
		}
	}

	public IsAnonymousTag(ComponentConfig componentConfig) {
		super(componentConfig);	
	}

}