package com.webobjects.directtoweb._xhtml;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORequest;
import com.webobjects.directtoweb.D2W;
import com.webobjects.directtoweb.D2WEditToManyFault;
import com.webobjects.directtoweb.InspectPageInterface;
import com.webobjects.eocontrol.EOEnterpriseObject;

import er.extensions.eof.ERXEOControlUtilities;
import er.extensions.foundation.ERXStringUtilities;

// Generated by the WOLips Templateengine Plug-in at Jun 17, 2008 2:40:20 PM
public class D2WEditToManyFault2 extends D2WEditToManyFault {
	public static float effectDuration = 0.8F;	// FIXME: turn into property
	
    public D2WEditToManyFault2(WOContext context) {
        super(context);
    }
    
    // accessors
    public String addBoxID() {
    	return id() + "_add";
    }
    
    public String removeBoxID() {
        String primaryKeyString = ERXEOControlUtilities.primaryKeyStringForObject(browserItem);
        return ERXStringUtilities.safeIdentifierName(browserItem.entityName() + primaryKeyString);
    }
    
    public String onSuccess() {
    	return "function(e){ new Effect.Fade('" + removeBoxID() + "', {duration: " + effectDuration + "}); }";
    }
    
    public Object toOneDescription() {
        EOEnterpriseObject anEO = browserItem;
        if(anEO != null) {
            String keyWhenRelationship = keyWhenRelationship();
            if(keyWhenRelationship == null || keyWhenRelationship.equals("userPresentableDescription"))
                return anEO.userPresentableDescription();
            else
                return anEO.valueForKeyPath(keyWhenRelationship);
        } else {
            return null;
        }
    }
    
    public String id() {
    	return (String) d2wContext().valueForKey("id");
    }

    // actions
    public WOComponent toOneAction() {
        EOEnterpriseObject anEO = browserItem;
        if(anEO == null) {
            return null;
        } else {
            InspectPageInterface inspectPage = D2W.factory().inspectPageForEntityNamed(anEO.entityName(), session());
            inspectPage.setObject(anEO);
            inspectPage.setNextPage(context().page());
            return (WOComponent)inspectPage;
        }
    }
    
    public WOComponent removeFromToManyRelationshipAction() {
        EOEnterpriseObject anEO = browserItem;
        object().removeObjectFromBothSidesOfRelationshipWithKey(anEO, propertyKey());
        return null;
    }
    
    // R/R
    // FIXME: RM: quick fix just catching the index out of bounds exception
    @Override
    public WOActionResults invokeAction(WORequest request, WOContext context) {
    	WOActionResults result = null;
        try {
            result = super.invokeAction(request, context);
        } catch (IllegalArgumentException exception) {
        	String msg = exception.getMessage();
        	if (!msg.startsWith("Index") || !msg.contains("out of bounds")) throw exception;
        }
        return result;
    }
}