package com.community.configuration;

import org.broadleafcommerce.cms.web.PageHandlerMapping;
import org.broadleafcommerce.common.extensibility.context.merge.Merge;
import org.broadleafcommerce.core.web.catalog.CategoryHandlerMapping;
import org.broadleafcommerce.core.web.catalog.ProductHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @author Elbert Bautista (elbertbautista)
 */
@Configuration
@ComponentScan("com.mycompany.controller")
public class SiteServletConfig extends WebMvcConfigurerAdapter {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("blLocaleCode");
        registry.addInterceptor(localeInterceptor);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico")
            .addResourceLocations("WEB-INF/favicon.ico");
    }
    
    @Bean
    public HandlerMapping staticResourcesHandlerMapping() {
        SimpleUrlHandlerMapping resourceMapping = new SimpleUrlHandlerMapping();
        resourceMapping.setOrder(-10);
        Properties mappings = new Properties();
        mappings.put("/js/**", "blJsResources");
        mappings.put("/css/**", "blCssResources");
        mappings.put("/img/**", "blImageResources");
        mappings.put("/fonts/**", "blFontResources");
        resourceMapping.setMappings(mappings);
        return resourceMapping;
    }
    
    @Merge("blJsLocations")
    public List<String> jsLocations() {
        return Collections.singletonList("/js/");
    }

    @Merge("blCssLocations")
    public List<String> cssLocations() {
        return Collections.singletonList("/css/");
    }

    @Merge("blImageLocations")
    public List<String> imageLocations() {
        return Collections.singletonList("/img/");
    }

    @Merge("blFontLocations")
    public List<String> fontLocations() {
        return Collections.singletonList("/fonts/");
    }
    
    @Bean
    public HandlerMapping productHandlerMapping() {
        ProductHandlerMapping mapping = new ProductHandlerMapping();
        mapping.setOrder(3);
        return mapping;
    }
    
    @Bean
    public HandlerMapping pageHandlerMapping() {
        PageHandlerMapping mapping = new PageHandlerMapping();
        mapping.setOrder(4);
        return mapping;
    }
    
    @Bean
    public HandlerMapping categoryHandlerMapping() {
        CategoryHandlerMapping mapping = new CategoryHandlerMapping();
        mapping.setOrder(5);
        return mapping;
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setCookieHttpOnly(true);
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
    
}
