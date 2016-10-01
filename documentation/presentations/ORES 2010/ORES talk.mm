<map version="0.8.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1274890151206" ID="Freemind_Link_1243013724" MODIFIED="1274890156569" TEXT="ORES talk">
<node CREATED="1274890170754" ID="_" MODIFIED="1274890191309" POSITION="right" TEXT="Slide 1">
<node CREATED="1274890192739" ID="Freemind_Link_18952958" MODIFIED="1274890200582" TEXT="listing of all the projects">
<node CREATED="1274890200938" ID="Freemind_Link_1492319062" MODIFIED="1274890203099" TEXT="Swing"/>
<node CREATED="1274890203470" ID="Freemind_Link_1824305119" MODIFIED="1274890205736" TEXT="GDI-Grid"/>
<node CREATED="1274890207906" ID="Freemind_Link_1218063965" MODIFIED="1274890209518" TEXT="Envision"/>
<node CREATED="1274890209848" ID="Freemind_Link_1817604233" MODIFIED="1274890221609" TEXT="with URL, logo, and overall objective"/>
</node>
</node>
<node CREATED="1274890226690" ID="Freemind_Link_277993385" MODIFIED="1274890228110" POSITION="right" TEXT="Slide 2">
<node CREATED="1274890228747" ID="Freemind_Link_1478557416" MODIFIED="1274890233494" TEXT="ontology engineering">
<node CREATED="1274890250120" ID="Freemind_Link_736408536" MODIFIED="1274890275220" TEXT="specification: concept maps with domain experts">
<node CREATED="1274890288848" ID="Freemind_Link_1294923115" MODIFIED="1274890297016" TEXT="show swing concept map as example"/>
</node>
<node CREATED="1274890277594" ID="Freemind_Link_1684108958" MODIFIED="1274890280173" TEXT="implementation">
<node CREATED="1274890234043" ID="Freemind_Link_1773985256" MODIFIED="1274890247248" TEXT="WSML as ontology language"/>
<node CREATED="1274890366589" ID="Freemind_Link_170578015" MODIFIED="1274890371298" TEXT="using WSMT (STI)"/>
</node>
</node>
<node CREATED="1274890313674" ID="Freemind_Link_157293632" MODIFIED="1274890319638" TEXT="how are ontologies used">
<node CREATED="1274890320467" ID="Freemind_Link_129459260" MODIFIED="1274890330710" TEXT="semantic annotation of geospatial (OGC) web services"/>
<node CREATED="1274890334905" ID="Freemind_Link_1966730818" MODIFIED="1274890361943" TEXT="implementation of an semantic annotation and semantic query tool (JSI)"/>
</node>
</node>
<node CREATED="1274890378597" ID="Freemind_Link_293356330" MODIFIED="1274890380523" POSITION="right" TEXT="Slide 3">
<node CREATED="1274890423009" ID="Freemind_Link_821095429" MODIFIED="1274890431153" TEXT="first approach">
<node CREATED="1274890526756" ID="Freemind_Link_1348787025" MODIFIED="1274890529679" TEXT="file-based approach"/>
<node CREATED="1274890431652" ID="Freemind_Link_1800573461" MODIFIED="1274890447171" TEXT="engineered wsml ontologies served with subversion">
<node CREATED="1274890447719" ID="Freemind_Link_1612912805" MODIFIED="1274890454192" TEXT="screenshot of swing.brgm.fr"/>
</node>
<node CREATED="1274890462666" ID="Freemind_Link_1064061715" MODIFIED="1274890471013" TEXT="drawbacks">
<node CREATED="1274890471882" ID="Freemind_Link_1696596737" MODIFIED="1274890484657" TEXT="crude versioning support (on the ontology level)"/>
<node CREATED="1274890485291" ID="Freemind_Link_1230259449" MODIFIED="1274890504737" TEXT="hard to maintain (also due to the lack of tools)">
<node CREATED="1274890585102" ID="Freemind_Link_1882437995" MODIFIED="1274890683678" TEXT="structural consistency checks were performed manually"/>
</node>
<node CREATED="1274890537393" ID="Freemind_Link_241493650" MODIFIED="1274890541720" TEXT="hard to modularize">
<node CREATED="1274890542549" ID="Freemind_Link_1351727175" MODIFIED="1274890548583" TEXT="borderline cases"/>
<node CREATED="1274890551182" ID="Freemind_Link_402728740" MODIFIED="1274890574906" TEXT="where was this concept again?"/>
</node>
</node>
</node>
</node>
<node CREATED="1274890617018" ID="Freemind_Link_1027820028" MODIFIED="1274890619561" POSITION="right" TEXT="Slide 4">
<node CREATED="1274890620238" ID="Freemind_Link_610444847" MODIFIED="1274890630068" TEXT="identified requirements from the projects">
<node CREATED="1274890632412" ID="Freemind_Link_585731857" MODIFIED="1274890644116" TEXT=" filter mechanisms to select relevant concepts only"/>
<node CREATED="1274890650055" ID="Freemind_Link_733668583" MODIFIED="1274890650799" TEXT="ontology evolution (and access to prior versions)"/>
<node CREATED="1274890661269" ID="Freemind_Link_1821201425" MODIFIED="1274890673360" TEXT="automated (structural) consistency checks"/>
</node>
</node>
<node CREATED="1274891159435" ID="Freemind_Link_1827868676" MODIFIED="1274891162059" POSITION="right" TEXT="Slide 5">
<node CREATED="1274891164263" ID="Freemind_Link_1895201302" MODIFIED="1274891174397" TEXT="shifting the focus from the ontology to the concept">
<node CREATED="1274891175290" ID="Freemind_Link_788573847" MODIFIED="1274891182470" TEXT="concept description as fundamental unit"/>
<node CREATED="1274891186007" ID="Freemind_Link_548705549" MODIFIED="1274891194989" TEXT="the URL identifies a concept description"/>
<node CREATED="1274891195665" ID="Freemind_Link_1531822855" MODIFIED="1274891207593" TEXT="context as part of the URL"/>
</node>
</node>
<node CREATED="1274890691775" ID="Freemind_Link_1467612518" MODIFIED="1274891228243" POSITION="right" TEXT="Slide 6">
<node CREATED="1274890696694" ID="Freemind_Link_1005471703" MODIFIED="1274890705728" TEXT="first implementation of core">
<node CREATED="1274890706558" ID="Freemind_Link_440923654" MODIFIED="1274890714739" TEXT="http://purl.org/net/concepts"/>
<node CREATED="1274891231946" ID="Freemind_Link_1617962798" MODIFIED="1274891237397" TEXT="domains as sets">
<node CREATED="1274891239332" ID="Freemind_Link_225120710" MODIFIED="1274950131334" TEXT="http://purl.org/net/concepts/Swing/"/>
<node CREATED="1274950141013" ID="Freemind_Link_1291966792" MODIFIED="1274950152505" TEXT="http://purl.org/net/"/>
</node>
</node>
</node>
</node>
</map>
