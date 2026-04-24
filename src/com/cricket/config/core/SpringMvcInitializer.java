package com.cricket.config.core;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.cricket.config.WebMvcConfig;
import com.cricket.config.DataSourceConfig;
import com.cricket.config.SecurityConfig;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { 
            DataSourceConfig.class,
            SecurityConfig.class   
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { 
            WebMvcConfig.class     
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}