package sofia.toolbox.net;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * This class contains methods to work with the ldap protocol.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class LDAP {
	private String initialContext;

	private Hashtable<Object, Object> environment = new Hashtable<Object, Object>(4);
	private DirContext context;

	private String user;
	private String url;
	private String password;
	
	private String message;
	
	public LDAP(String url) {
		this.url = url;
	}

	public LDAP(String url, String user, String password) {
		this(url);	   
		this.url = url;
		this.user = user;
		this.password = password;

	}

	public LDAP(String initialContext, String url, String user, String password) {
		this(url, user, password);	   
		this.initialContext = initialContext;

	}
	
	public String getMessage() {
		return this.message;
	}		

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUser(String user) {
		if (user == null) user = "undefined";
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setPassword(String password) {
		if (password == null) password = "undefined";
		this.password = password;
	}

	public void setInitialContext(String initialContext) {
		this.initialContext = initialContext;
	}

	public String getInitialContext() {
		return initialContext;
	}

	public HashMap<String, String> searchUser(String user) {

		try {

			String filter;
			HashMap<String, String> hashmap = new HashMap<String, String>();

			filter = "(&(objectClass=person)(uid=" + user + "))";

			SearchControls ctls = new SearchControls();

			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<SearchResult> answer;
			answer = context.search("", filter, ctls);

			if (answer.hasMore()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attribs = sr.getAttributes();

				for (NamingEnumeration<? extends Attribute> ae = attribs.getAll(); ae.hasMoreElements();) 
				{
					Attribute attr = (Attribute) ae.next();
					String attrId = attr.getID();

					for (Enumeration<?> vals = attr.getAll(); 
					vals.hasMoreElements();
					hashmap.put(attrId, vals.nextElement().toString()));
				}				

			}
			return hashmap;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;
		}

	}

	public ArrayList<String> search(String filter) {

		try {

			ArrayList<String> result = new ArrayList<String>();

			SearchControls ctls = new SearchControls();

			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<SearchResult> answer;
			answer = context.search("", filter, ctls);

			while (answer.hasMore()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attribs = sr.getAttributes();

				for (NamingEnumeration<? extends Attribute> ae = attribs.getAll(); ae.hasMoreElements();) 
				{
					Attribute attr = (Attribute) ae.next();
					String attrId = attr.getID();

					for (Enumeration<?> vals = attr.getAll(); vals.hasMoreElements();) {
						String val = vals.nextElement().toString(); 

						if (attrId.equals("uid")) result.add(val);

					}

				}
			}
			return result;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;
		}

	}


	public String getAttribute(String attribute) {	
		return getAttribute(this.user, attribute);
	}

	public String getAttribute(String user, String attribute) {

		HashMap<String, String> hm = new HashMap<String, String>();

		hm = searchUser(user);
		return getAttribute(hm, attribute);
	}

	public String getCommonName(String user) {

		try {

			String filter = "(&(objectClass=person)(uid=" + user + "))";

			SearchControls ctls = new SearchControls();

			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<SearchResult> answer;
			answer = context.search("", filter, ctls);

			if (answer.hasMore()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attribs = sr.getAttributes();

				for (NamingEnumeration<? extends Attribute> ae = attribs.getAll(); ae.hasMoreElements();) 
				{
					Attribute attr = (Attribute) ae.next();
					String attrId = attr.getID();

					if (attrId.equalsIgnoreCase("cn")) 
						return attr.getAll().nextElement().toString();
				}				

			}

			this.close();

			return user;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;
		}

	}


	public String getAttribute(HashMap<String, String> hm, String attribute) {

		if (hm == null) return null;

		return hm.get(attribute);
	}

	public boolean open() {

		try {

			if (initialContext == null) 
				initialContext = "com.sun.jndi.ldap.LdapCtxFactory";

			environment.put(Context.INITIAL_CONTEXT_FACTORY, initialContext);

			if (url != null)
				environment.put(Context.PROVIDER_URL, url);

			if (user != null)
				environment.put(Context.SECURITY_PRINCIPAL, user);

			if (password != null)
				environment.put(Context.SECURITY_CREDENTIALS, password);

			context = new InitialDirContext(environment);
			return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}
	}

	public boolean close() {
		try {

			if (context != null) context.close();
			return true;

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}
	}



}
