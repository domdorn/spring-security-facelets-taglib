package org.springframework.security.taglibs.facelets;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.*;
import java.io.IOException;

/**
 * Taglib to combine the Spring-Security Project with Facelets <br />
 *
 * This is the Class responsible for making the <br />
 * <code><br />
 *     &lt;sec:ifNotGranted roles=&quot;ROLE_USER,ROLE_EXAMPLE&quot;&gt;<br />
 *         The components you want to show only when the condition holds <br />
 *     lt;/sec:ifNotGranted&gt;<br />
 * </code>
 * work.
 *
 *
 * @author Dominik Dorn - http://www.dominikdorn.com/
 * @date 2009-04-30
 */
public class IfNotGrantedTag extends TagHandler {

	private final TagAttribute roles;

	public void apply(FaceletContext faceletContext, UIComponent uiComponent)
			throws IOException, FacesException, FaceletException, ELException {
		if (this.roles == null)
			throw new FaceletException("roles must be given, but is null");

		String roles = this.roles.getValue(faceletContext);
		if (roles == null || roles.isEmpty())
			throw new FaceletException("roles must be given");

		if (SpringSecurityELLibrary.ifNotGranted(roles))
			this.nextHandler.apply(faceletContext, uiComponent);
	}

	public IfNotGrantedTag(ComponentConfig componentConfig) {
		super(componentConfig);
		this.roles = this.getRequiredAttribute("roles");
		if (this.roles == null)
			throw new TagAttributeException(this.roles,
					"The `roles` attribute has to be specified!");
	}
}