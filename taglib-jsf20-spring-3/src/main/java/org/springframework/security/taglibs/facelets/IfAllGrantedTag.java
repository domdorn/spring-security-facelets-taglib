package org.springframework.security.taglibs.facelets;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagHandler;

/**
 * Taglib to combine the Spring-Security Project with Facelets <p>
 *
 * This is the Class responsible for making the
 * <code>
 *     &lt;sec:ifAllGranted roles=&quot;ROLE_USER,ROLE_EXAMPLE&quot;&gt;
 *         The components you want to show only when the condition holds
 *     lt;/sec:ifAllGranted&gt;
 * </code>
 * work.
 *
 *
 * @author Dominik Dorn - http://www.dominikdorn.com/
 * @version %I%, %G%
 * @since 0.1
 */
public class IfAllGrantedTag extends TagHandler {

	private final TagAttribute roles;

	public void apply(FaceletContext faceletContext, UIComponent uiComponent)
			throws IOException, FacesException, FaceletException, ELException {
		if (this.roles == null)
			throw new FaceletException("roles must be given, but is null");

		String roles = this.roles.getValue(faceletContext);
		if (roles == null || "".equals(roles.trim()))
			throw new FaceletException("roles must be given");

		if (SpringSecurityELLibrary.ifAllGranted(roles))
			this.nextHandler.apply(faceletContext, uiComponent);
	}

	public IfAllGrantedTag(ComponentConfig componentConfig) {
		super(componentConfig);
		this.roles = this.getRequiredAttribute("roles");
		if (this.roles == null)
			throw new TagAttributeException(this.roles,
					"The `roles` attribute has to be specified!");
	}
}

