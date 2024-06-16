package com.example.rpt;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import net.sf.jasperreports.repo.ReportResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rpt")
public class HelloApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> sets = new HashSet<>();
        sets.add(ReportResource.class);
        return sets;
    }
}