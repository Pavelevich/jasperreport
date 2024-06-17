ReportGenerator is a core piece of our project that is in charge of generating PDF reports. For that task, it leverages several components and libraries. 
The dependencies used for this purpose are detailed below:
JasperReports: 
This is the main library for generating reports in our project. We use version 6.17.0 of JasperReports.
This library provides functionalities to design reports, fill them with data from our data sources, and export them to various formats, including PDF, HTML, XLS, etc.
<dependency>
    <groupId>net.sf.jasperreports</groupId>
    <artifactId>jasperreports</artifactId>
    <version>6.17.0</version>
</dependency>
