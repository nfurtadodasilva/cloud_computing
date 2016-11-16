package com.efrei.lefrere_furtado_tp1;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import sun.util.logging.resources.logging;

import com.google.appengine.api.NamespaceManager;

public class NameSpaceFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
			String namespace = arg0.getParameter("namespace");
			if(NamespaceManager.get() == null) {
				if(namespace == null )
					NamespaceManager.set("domain");
				else
					NamespaceManager.set(namespace);
				System.out.println("Namespace : " + namespace);
			}

			
			//Do filter
			arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
