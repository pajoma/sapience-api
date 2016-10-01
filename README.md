
    sapience: the property of possessing or being able to possess wisdom (​Wiktionary)

The Semantic Annotations API (sapience) comprises libraries giving application developers a simple and fast way to extend
their applications with semantic functionality. Existing applications with complex and data models usually lack ways to describe
the meaning of the data, which unnecessarily impairs the exchange of data across different applications. This is especially the
case for Web services serving arbitrary content which have to be integrated into other applications. Due to our background in
Geoinformatics, the libraries have a strong focus on supporting the annotation of geospatial content compliant to standards
published by the Open Geospatial Consortium (OGC). The various libraries have been either developed within scientific research
projects, or are results of thesis implementations (ranging from BSC to PhD). The implementations have been refactored and
simplified for sapience.
All libraries are published under the terms of the Apache 2.0 license, which gives you basically the right to do whatever you
want as long as you ship the original license and keep the information about the authors. 

*Community*
Check the project page, including the ​message board, for feedback and updated news. Use the ticket system for feature requests and bugs. We also try to have regular meetings.

*Download´*
The current version is: 0.1-SNAPSHOT. You can either go directly browse the source,
download it via Maven (documentation) or download the individual libraries from the individual module documentation (see below).
Background Information

The following documention includes some basic background information about semantic annotations
(taken from the ​OGC Discussion Paper "Semantic Annotations in OGC Standards").

    An introduction into the Semantic Annotations Proxy
    Example Scenarions Semantic Data Integration, Semantic Validation of Workflows
    Introducing Semantic Annotations
    Typical Applications
    Slides, Papers, and misc media 

How do we annotate the Web services
Ontologies are classified into different types, for example shared domain ontologies developed for particular use cases describing the reality, or local application-speciﬁc ontologies which should be understood as formal speciﬁcations of the data. This has an impact on the definition of annotations. Next to the link to a certain vocabulary, a description of the annotation style is needed, too. We identified the following four styles of annotations:
Shared Information Model
Here we annotate the data with domain concepts representing objects in the real world.
Application-Specific Information Model
Here we annotate the data with application-specific concepts representing the data itself.
Functional Description Model
Here we annotate Operations of a Web service with a functional description including pre- and postconditions of this operation. If the data of a service is annotated using the Application-Specific Information Model, the Functionl Description Model links the application-specific concepts to the domain concepts.
Service Description Model
Here we annotate metadata documents of a Web service describing the whole service, for example GetCapabilities? concerning OGC Web services or *.wsdl documents concerning W3C Web services, with a Service Model (WSMO-Lite Service Model) describing all relevant parts of Web Service.
Listing of annotation possibilities for W3C- and OGC-compliant Web services:

    W3C-Web services (WSDL)
    OGC Sensor Observation Service (SOS)
    OGC Web Processing Service (WPS)
    OGC Web Feature Service (WFS)
    OGC Web Coverage Service (WCS) 

Specifications

    Sapience Architecture
    Injection Procedure 

Components

    sapience-services -- Web Service Interfaces to some components of the sapience framework.
    sapience-injectors -- Injectors are responsible for adding annotations to existing. (and supported) documents
    sapience-annotations -- The annotation model, including components to lookup annotations.
    sapience-lookup -- The Lookup service, responsible for handling databases filled with semantic annotations
    sapience-spatial -- Spatial extensions, e.g. the feature model.
    sapience-streams -- Streams to support the parsing of the different standard-compliant documents which are or have to be annotated.
    sapience-integration -- Integration of 3rd-party libraries
    semantic-proxy -- The Proxy service 

Tutorials

    Parsing KML into the Feature Model
    Getting Coordinates out of KML files
    Reading Annotations in the Feature Model (parsed from KML)
    Using Annotations to retrieve current water levels 

Miscellaneous Documentation and Discussions

    Generating OWL instances from the feature/annotation model and apply SWRL spatial built-ins 
