package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.net.URL;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

public class ResourceUtil {

	//from http://stackoverflow.com/questions/3861989/preferred-way-of-loading-resources-in-java
		public static URL getResource(String resource){

		    URL url ;

		    //Try with the Thread Context Loader. 
		    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    if(classLoader != null){
		        url = classLoader.getResource(resource);
		        if(url != null){
		            return url;
		        }
		    }

		    //Let's now try with the classloader that loaded this class.
		    classLoader = Loader.class.getClassLoader();
		    if(classLoader != null){
		        url = classLoader.getResource(resource);
		        if(url != null){
		            return url;
		        }
		    }

		    //Last ditch attempt. Get the resource from the classpath.
		    return ClassLoader.getSystemResource(resource);
		}
}
