<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"

                xmlns:xml="http://www.w3.org/XML/1998/namespace"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                xmlns:lxslt="http://xml.apache.org/xslt"
                xmlns:xalan="http://xml.apache.org/xalan"
		
                version="1.0">
	<!-- xsl:import href="ggx2gxl.xsl"/ -->

	<xsl:output doctype-system="gtxl.dtd"  method="xml"  indent="yes"/>
   
	<!-- remove text nodes that contain only whitespaces --> 
	<xsl:strip-space elements = '*' /> 

	<xsl:template match="/Document/GraphTransformationSystem">
		<gtxl xmlns:xlink="http://www.w3.org/1999/xlink">
		
		<!-- store name of GraphTransformationSystem -->
	    <attr id="{/Document/GraphTransformationSystem/@ID}" 
	      name="{/Document/GraphTransformationSystem/@name}"/>
		
			<!-- in gtxl (AGG) the type/schema graph is saved here (not in a separate file) -->
			<!-- if TypeGraph is present, transform it to Type Graph otherwise to automatically generated Schema Graph -->
             <xsl:apply-templates select="Types"/>
			<!-- graphtransformationsystem begins here -->	
			<gts>
				<!-- DPO/SPO approach , depends on dangling, injective identification being set -->
				<xsl:choose>
                    <xsl:when test="TaggedValue[@Tag='dangling'][@TagValue='true']">
                        <xsl:choose>
                            <xsl:when test="(TaggedValue[@Tag='injective'][@TagValue='true'])or(TaggedValue[@Tag='identification'][@TagValue='true'])">
                                <xsl:attribute name="approach">DPO</xsl:attribute>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:attribute name="approach">SPO</xsl:attribute>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="approach">SPO</xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>
				<!-- store each Tagged Value of GraphTransformationSystem in attr* -->
				<xsl:call-template name="TaggedValue"/>
				<!-- create xlink to TypeGraph or SchemaGraph in this gtxl file -->
				<xsl:if test="Graph[@name='Type Graph']">
					<type xlink:type="simple" xlink:href="#{Graph[@name='Type Graph']/@ID}"/>        		
    			</xsl:if>
				<xsl:if test="not (Graph[@name='Type Graph'])">
	           		<type xlink:type="simple" xlink:href="#SchemaGraph"/> 
				</xsl:if>
				<!-- import ggx2gxl.xsl to convert Graph to initial graph --> 
				<xsl:for-each select="Graph">
		  			<initial>
		    			<graph id="{./@name}">
       						<xsl:apply-templates select="Node | Edge"/>
     					</graph>
	 				</initial>
				</xsl:for-each>
 				<!-- transform each Rule (ggx) in rule (gtxl) (transformation unit in ggx not defined) -->  
 				<xsl:call-template name="Rule"/>
			</gts>
		</gtxl>
	</xsl:template>

<!--  import from  ggx2gxl.xsl  -->
<xsl:template match="Types">
	<!-- convert Types -->

	<!-- CASE 1: there is a 'TypeGraph' -->
	<xsl:if test="Graph[@name='TypeGraph']">
		<graph id="TypeGraph" edgeids="true">
			<attr id="{Graph[@name='TypeGraph']/@ID}"
			      name="{Graph[@name='TypeGraph']/@name}"/>
			<xsl:apply-templates select="Graph[@name='TypeGraph']/Node"/>
			<xsl:apply-templates select="Graph[@name='TypeGraph']/Edge"/>
		</graph>

	</xsl:if>
	<!-- CASE 2: there is no 'TypeGraph' -->
	<xsl:if test="not (Graph[@name='TypeGraph'])">
  		<graph id="Schema Graph" edgeids="true">
			<xsl:apply-templates select="EdgeType"/>
			<!-- create help structure 'X-Node' -->
			<node id="X">
				<graph id="X_Graph" edgeids="true">
					<xsl:apply-templates select="NodeType"/>
				</graph>
			</node>
  		</graph>
	</xsl:if>
</xsl:template>

<xsl:template match="Graph[@name='TypeGraph']/Node">
	<!-- CASE 1: convert 'TypeGraph - Nodes' -->
	<xsl:variable name="type" select="@type"/>
	<node id="{../../NodeType[@ID=$type]/@name}" abstract="{../../NodeType[@ID=$type]/@abstract}">
		<xsl:apply-templates select="../../NodeType[@ID=$type]/Parent"/>
		<xsl:apply-templates select="../../NodeType[@ID=$type]/AttrType"/>
		<attr name="X" kind="Layout">
			<int>
				<xsl:value-of select="NodeLayout/@X"/>
			</int>
		</attr>
		<attr name="Y" kind="Layout">
			<int>
				<xsl:value-of select="NodeLayout/@Y"/>
			</int>
		</attr>
	</node>
</xsl:template>

<xsl:template match="Parent">
	<!-- convert 'TypeGraph - Parent of NodeType' recursively -->
	<xsl:variable name="pID" select="@pID"/>
	<xsl:variable name="pid" select="../../NodeType[@ID=$pID]/@name"/>
	<parent pid="{$pid}">
		<!-- xsl:apply-templates select="../../NodeType[@ID=$pID]/Parent"/ -->
	</parent>
</xsl:template>

<xsl:template match="AttrType">
	<!-- convert 'TypeGraph - Attributes' -->
	<attr name="{@attrname}" kind="AttrType">
		<string>
			<xsl:value-of select="@typename"/>
		</string>
	</attr>
</xsl:template>

<xsl:template match="Graph[@name='TypeGraph']/Edge">
	<!-- CASE 1: convert 'TypeGraph - Edges' -->
	<xsl:variable name="type" select="@type"/>

	<xsl:variable name="source" select="@source"/>
	<xsl:variable name="sourceNode" select="../Node[@ID=$source]/@type"/>
	<!-- get source NodeType-Name -->
	<xsl:variable name="from" select="../../NodeType[@ID=$sourceNode]/@name"/>

	<xsl:variable name="target" select="@target"/>
	<xsl:variable name="targetNode" select="../Node[@ID=$target]/@type"/>
	<!-- get target NodeType-Name -->
	<xsl:variable name="to" select="../../NodeType[@ID=$targetNode]/@name"/>

	<!-- create edge-ID: 'old-edge-ID:edge-type-name' (to create a unique ID) -->
	<edge id="{@ID}:{../../EdgeType[@ID=$type]/@name}" from="{$from}" to="{$to}">
		<xsl:apply-templates select="../../EdgeType[@ID=$type]/AttrType"/>
		<xsl:if test="@sourcemin">
			<attr name="sourcemin" kind="multiplicity">
				<int>
					<xsl:value-of select="@sourcemin"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="@sourcemax">
			<attr name="sourcemax" kind="multiplicity">
				<int>
					<xsl:value-of select="@sourcemax"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="@targetmin">
			<attr name="targetmin" kind="multiplicity">
				<int>
					<xsl:value-of select="@targetmin"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="@targetmax">
			<attr name="targetmax" kind="multiplicity">
				<int>
					<xsl:value-of select="@targetmax"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@bendX">
			<attr name="bendX" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@bendX"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@bendY">
			<attr name="bendY" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@bendY"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@sourceMultiplicityOffsetX">
			<attr name="sourceMultiplicityOffsetX" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@sourceMultiplicityOffsetX"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@sourceMultiplicityOffsetY">
			<attr name="sourceMultiplicityOffsetY" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@sourceMultiplicityOffsetY"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@targetMultiplicityOffsetX">
			<attr name="targetMultiplicityOffsetX" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@targetMultiplicityOffsetX"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@targetMultiplicityOffsetY">
			<attr name="targetMultiplicityOffsetY" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@targetMultiplicityOffsetY"/>
				</int>
			</attr>
		</xsl:if>
	</edge>
</xsl:template>

<xsl:template match="NodeType">
	<!-- CASE 2: convert 'Schema Graph - Nodes' -->
	<node id="{@name}">
		<xsl:apply-templates select="AttrType"/>
	</node>
</xsl:template>

<xsl:template match="EdgeType">
	<!-- CASE 2: convert 'Schema Graph - Edges' -->
	<edge id="{@name}" from="X" to="X">
		<xsl:apply-templates select="AttrType"/>
	</edge>
</xsl:template>

<xsl:template match="Graph">
	<!-- convert 'InstanceGraph' -->
	<graph id="InstanceGraph">
		<attr id="{./@ID}" name="{./@name}"/>
		<xsl:apply-templates select="Node | Edge"/>
	</graph>
</xsl:template>


<xsl:template match="Node">
	<!-- convert 'InstanceGraph - Nodes' -->
	<node id="{@ID}">
		<xsl:variable name="type" select="@type"/>
		<!-- create xlink to NodeType-Name -->
		<type xlink:href="{/Document/GraphTransformationSystem/Types/NodeType[@ID=$type]/@name}"/>

		<xsl:apply-templates select="Attribute"/>

		<attr name="X" kind="Layout">
			<int>
				<xsl:value-of select="NodeLayout/@X"/>
			</int>
		</attr>
		<attr name="Y" kind="Layout">
			<int>
				<xsl:value-of select="NodeLayout/@Y"/>
			</int>
		</attr>
	</node>
</xsl:template>

<xsl:template match="Attribute">
	<!-- convert 'InstanceGraph - Node - Attributes' -->
	<xsl:if test="name(..)='Node'">
		<xsl:variable name="attrtype" select="@type"/>
		<xsl:variable name="attrname" select="/Document/GraphTransformationSystem/Types/NodeType/AttrType[@ID=$attrtype]/@attrname"/>
		<xsl:variable name="typename" select="/Document/GraphTransformationSystem/Types/NodeType/AttrType[@ID=$attrtype]/@typename"/>
		<attr name="{$attrname}">
   <xsl:if test="Value/java/string"> 
     <xsl:copy-of select="Value/java/string"/>
			</xsl:if>
   <xsl:if test="Value/java"> 
    <xsl:if test="not (Value/java/string)"> 
				 <freeType>
      <xsl:copy-of select="Value/java"/>
				 </freeType>
			 </xsl:if>
   </xsl:if>
			<xsl:if test="Value/object"> 
				<freeType>
					<xsl:copy-of select="Value/object"/>
				</freeType>
			</xsl:if>
			<xsl:if test="Value/boolean"> 
				<bool>
					<xsl:value-of select="Value/."/>
				</bool>
			</xsl:if>
			<xsl:if test="Value/string | Value/String"> 
				<string>
					<xsl:value-of select="Value/."/>
				</string>
			</xsl:if>
			<xsl:if test="Value/int | Value/Integer"> 
				<int>
					<xsl:value-of select="Value/."/>
				</int>
			</xsl:if>
			<xsl:if test="Value/double | Value/Double"> 
				<double>
					<xsl:value-of select="Value/."/>
				</double>
			</xsl:if>
			<xsl:if test="Value/float | Value/Float"> 
				<float>
					<xsl:value-of select="Value/."/>
				</float>
			</xsl:if>
			<xsl:if test="Value/short | Value/Short"> 
				<short>
					<xsl:value-of select="Value/."/>
				</short>
			</xsl:if>
			<xsl:if test="Value/long | Value/Long"> 
				<long>
					<xsl:value-of select="Value/."/>
				</long>
			</xsl:if>
			<xsl:if test="Value/char | Value/Character"> 
				<char>
					<xsl:value-of select="Value/."/>
				</char>
			</xsl:if>
		</attr>
	</xsl:if>
	<!-- convert 'InstanceGraph - Edge - Attributes' -->
	<xsl:if test="name(..)='Edge'">
		<xsl:variable name="attrtype" select="@type"/>
		<xsl:variable name="attrname" select="/Document/GraphTransformationSystem/Types/EdgeType/AttrType[@ID=$attrtype]/@attrname"/>
		<xsl:variable name="typename" select="/Document/GraphTransformationSystem/Types/EdgeType/AttrType[@ID=$attrtype]/@typename"/>
		<attr name="{$attrname}">
   <xsl:if test="Value/java/string"> 
     <xsl:copy-of select="Value/java/string"/>
			</xsl:if>
   <xsl:if test="not (Value/java/string)"> 
				<freeType>
     <xsl:copy-of select="Value/java"/>
				</freeType>
			</xsl:if>
			<xsl:if test="Value/object"> 
				<freeType>
					<xsl:copy-of select="Value/object"/>
				</freeType>
			</xsl:if>
			<xsl:if test="Value/boolean"> 
				<bool>
					<xsl:value-of select="Value/."/>
				</bool>
			</xsl:if>
			<xsl:if test="Value/string | Value/String"> 
				<string>
					<xsl:value-of select="Value/."/>
				</string>
			</xsl:if>
			<xsl:if test="Value/int | Value/Integer"> 
				<int>
					<xsl:value-of select="Value/."/>
				</int>
			</xsl:if>
			<xsl:if test="Value/double | Value/Double"> 
				<double>
					<xsl:value-of select="Value/."/>
				</double>
			</xsl:if>
			<xsl:if test="Value/float | Value/Float"> 
				<float>
					<xsl:value-of select="Value/."/>
				</float>
			</xsl:if>
			<xsl:if test="Value/short | Value/Short"> 
				<short>
					<xsl:value-of select="Value/."/>
				</short>
			</xsl:if>
			<xsl:if test="Value/long | Value/Long"> 
				<long>
					<xsl:value-of select="Value/."/>
				</long>
			</xsl:if>
			<xsl:if test="Value/char | Value/Character"> 
				<char>
					<xsl:value-of select="Value/."/>
				</char>
			</xsl:if>
		</attr>
	</xsl:if>
</xsl:template>

<xsl:template match="Edge">
	<!-- convert 'InstanceGraph - Edges' -->
	<edge id="{@ID}" from="{@source}" to="{@target}">
		<xsl:variable name="type" select="@type"/>
		<xsl:variable name="typeName" select="/Document/GraphTransformationSystem/Types/EdgeType[@ID=$type]/@name"/>

		<xsl:if test="/Document/GraphTransformationSystem/Types/Graph[@name='TypeGraph']">
			<xsl:variable name="typeID" select="/Document/GraphTransformationSystem/Types/Graph[@name='TypeGraph']/Edge[@type=$type]/@ID"/>
			<type xlink:href="{$typeID}:{$typeName}"/>
		</xsl:if>

		<xsl:if test="not (/Document/GraphTransformationSystem/Types/Graph[@name='TypeGraph'])">
			<type xlink:href="{$typeName}"/>
		</xsl:if>

		<xsl:apply-templates select="Attribute"/>

		<xsl:if test="EdgeLayout/@bendX">
			<attr name="bendX" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@bendX"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@bendY">
			<attr name="bendY" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@bendY"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@textOffsetX">
			<attr name="textOffsetX" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@textOffsetX"/>
				</int>
			</attr>
		</xsl:if>
		<xsl:if test="EdgeLayout/@textOffsetY">
			<attr name="textOffsetY" kind="Layout">
				<int>
					<xsl:value-of select="EdgeLayout/@textOffsetY"/>
				</int>
			</attr>
		</xsl:if>
	</edge>
</xsl:template>

<!-- end of ggx2gxl.xsl -->
	
	
	<xsl:template name="TaggedValue">
		<!-- AGG-specific attributes e.g. CSP, dangling, NAC, layered ... --> 
    	<xsl:for-each select="TaggedValue">
			<attr name="{@Tag}">
				<string><xsl:value-of select="@TagValue"/></string>
			</attr>
    	</xsl:for-each>
		<!-- in AGG this is always true -->
    	<attr name="attributed">
			<bool>true</bool>
		</attr>
		<attr name="attr_language">
			<string>Java</string>
		</attr>
  	</xsl:template>


	<xsl:template name="Rule">
		<!-- transform ggx_representation (lhs_graph/rhs_graph/morphism) to gtxl_integrated_representation of rule -->
		<xsl:for-each select="Rule">
			<rule id="{@ID}" name="{./Morphism/@name}">
				<xsl:call-template name="preserved"/>
				<xsl:call-template name="deleted"/>
				<xsl:call-template name="created"/>     
				<xsl:call-template name="parameter"/>
				<xsl:call-template name="condition">
                <xsl:with-param name="rule_ID"><xsl:value-of select="@ID"/></xsl:with-param>
				</xsl:call-template>
				<xsl:call-template name="embedding" />
				<xsl:call-template name="attribute" />
			</rule>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="preserved">
		<preserved>
		<!-- graph contains all the nodes and edges, together with their unchanged attributes, of the lhs mapped by the element Mapping (orig) -->
		<!-- map contains nodes and edges (without attributes) of the rhs, if the rule is not injective --> 
			<graph id="{Graph[1]/@ID}">
				<xsl:for-each select="Morphism/Mapping">
					<xsl:variable name="var_orig">
						<xsl:value-of select="@orig"/>
					</xsl:variable>
					<xsl:variable name="var_image">
						<xsl:value-of select="@image"/>
					</xsl:variable>
					<!-- preserved part of left side of rule is listed here -->
					<xsl:if test="../../Graph[1]/Node[@ID=$var_orig]">
						<!-- changed attributes are not listed here, but in the deleted and created part -->
						<xsl:call-template name="node_attr">
							<xsl:with-param name="var_orig"><xsl:value-of select="$var_orig"/></xsl:with-param>
							<xsl:with-param name="var_image"><xsl:value-of select="$var_image"/></xsl:with-param>							
						</xsl:call-template>
					</xsl:if>
					<xsl:if test="../../Graph[1]/Edge[@ID=$var_orig]">
						<!-- changed attributes are not listed here, but in the deleted and created part -->
						<xsl:call-template name="edge_attr">
							<xsl:with-param name="par_ID"><xsl:value-of select="$var_orig"/></xsl:with-param>
							<xsl:with-param name="par_image"><xsl:value-of select="$var_image"/></xsl:with-param>
							<xsl:with-param name="par_source">
								<xsl:value-of select="../../Graph[1]/Edge[@ID=$var_orig]/@source"/>
							</xsl:with-param>
							<xsl:with-param name="par_target">
								<xsl:value-of select="../../Graph[1]/Edge[@ID=$var_orig]/@target"/>
							</xsl:with-param>
							<xsl:with-param name="par_type">
								<xsl:value-of select="../../Graph[1]/Edge[@ID=$var_orig]/@type"/>
							</xsl:with-param>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
			</graph>
			<xsl:for-each select="Morphism/Mapping">
				<xsl:variable name="var_orig">
					<xsl:value-of select="@orig"/>
				</xsl:variable>
				<xsl:variable name="var_image">
					<xsl:value-of select="@image"/>
				</xsl:variable>
				<!-- check if the mapping is not injectiv-->
				<xsl:if test="count(../../Morphism/Mapping[@image=$var_image])&gt;=2">
					<!-- test if this mapping was listed before -->
					<xsl:if test="count(preceding-sibling::Mapping[@image=$var_image])=0">
						<!-- list of source nodes in attribute source followed by target knode or edge -->
						<map>
							<xsl:attribute name="source">
								<xsl:value-of select="@orig"/>
								<xsl:for-each select="following-sibling::Mapping[@image=$var_image]">
									<xsl:value-of select="concat(' ',@orig)"/>
								</xsl:for-each>
							</xsl:attribute>
							<xsl:if test="../../Graph[2]/Node[@ID=$var_image]">
								<!-- node rhs without attributes -->
								<xsl:call-template name="node_without_attr">
			                           <xsl:with-param name="var_image"><xsl:value-of select="$var_image"/></xsl:with-param>
								</xsl:call-template> 
							</xsl:if>
							<xsl:if test="../../Graph[2]/Edge[@ID=$var_image]">
								<!-- edge rhs without attributes -->
								<xsl:call-template name="edge_without_attr">
                           			<xsl:with-param name="par_ID">
										<xsl:value-of select="../../Graph[2]/Edge[@ID=$var_image]/@ID"/>
									</xsl:with-param>
                           			<xsl:with-param name="par_source">
                               			<xsl:value-of select="../../Graph[2]/Edge[@ID=$var_image]/@source"/>
                           			</xsl:with-param>
                           			<xsl:with-param name="par_target">
                               			<xsl:value-of select="../../Graph[2]/Edge[@ID=$var_image]/@target"/>
                          				</xsl:with-param>
                           			<xsl:with-param name="par_type">
                               			<xsl:value-of select="../../Graph[2]/Edge[@ID=$var_image]/@type"/>
                           			</xsl:with-param>
                       			</xsl:call-template>
							</xsl:if>
                       	</map>	
					</xsl:if>
				</xsl:if>
			</xsl:for-each>
		</preserved>
	</xsl:template>

	
	<xsl:template name="node_attr">
	<!-- template used by template preserved -->
	<!-- node of the lhs with attributes that don't change -->
		<xsl:param name="var_orig"/>
		<xsl:param name="var_image"/>
		<node id="{$var_orig}">
			<xsl:variable name="type" select="../../Graph[1]/Node[@ID=$var_orig]/@type"/>
			<!-- create xlink to NodeType-Name -->
			<type xlink:href="#{/Document/GraphTransformationSystem/Types/NodeType[@ID=$type]/@name}"/>
			<!-- check if layoutattributes change-->
			<xsl:variable name="layoutx" select="../../Graph[2]/Node[@ID=$var_image]/NodeLayout/@X"/>
			<xsl:variable name="layouty" select="../../Graph[2]/Node[@ID=$var_image]/NodeLayout/@Y"/>
			<xsl:if test="../../Graph[1]/Node[@ID=$var_orig]/NodeLayout[@X=$layoutx]">
				<attr name="X" kind="Layout">
                	<int>
                        <xsl:value-of select="../../Graph[1]/Node[@ID=$var_orig]/NodeLayout/@X"/>
                    </int>
                </attr>
			</xsl:if>
			<xsl:if test="../../Graph[1]/Node[@ID=$var_orig]/NodeLayout[@Y=$layouty]">
				<attr name="Y" kind="Layout">
                    <int>
                        <xsl:value-of select="../../Graph[1]/Node[@ID=$var_orig]/NodeLayout/@Y"/>
                    </int>
                </attr>
            </xsl:if>
			<!-- check if other attributes change -->
			<xsl:for-each select="../../Graph[1]/Node[@ID=$var_orig]/Attribute">
				<xsl:variable name="type_attr" select="@type"/>
				<!-- list only if attribute value does not change -->
				<xsl:if test="./Value=../../../Graph[2]/Node[@ID=$var_image]/Attribute[@type=$type_attr]/Value">
					<xsl:apply-templates select="."/>
				</xsl:if>
			</xsl:for-each>
		</node>
	</xsl:template>

	<xsl:template name="node_without_attr">
    <!-- template used by template preserved -->
	<!-- node of the rhs without attributes (for nodes belonging to map) -->
        <xsl:param name="var_image"/>
        <node id="{$var_image}">
            <xsl:variable name="type" select="../../Graph[2]/Node[@ID=$var_image]/@type"/>
            <!-- create xlink to NodeType-Name -->
            <type xlink:href="#{/Document/GraphTransformationSystem/Types/NodeType[@ID=$type]/@name}"/>
        </node>
    </xsl:template>

	<xsl:template name="edge_attr">
    <!-- template used by template preserved -->
	<!-- edges of the lhs, with attributes that do not change -->
        <xsl:param name="par_ID"/>
        <xsl:param name="par_source"/>
        <xsl:param name="par_target"/>
        <xsl:param name="par_type"/>
        <xsl:param name="par_image"/>
        <edge id="{$par_ID}" from="{$par_source}" to="{$par_target}">
            <xsl:variable name="typeName" select="/Document/GraphTransformationSystem/Types/EdgeType[@ID=$par_type]/@name"/>
            <xsl:if test="/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph']">
                <xsl:variable name="typeID" select="/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph']/Edge[@type=$par_type]/@ID"/>
                <type xlink:href="#{$typeID}:{$typeName}"/>
            </xsl:if>
            <xsl:if test="not (/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph'])">
                <type xlink:href="#{$typeName}"/>
            </xsl:if>
            <!-- check if layout attributes change -->
            <xsl:variable name="bendX" select="../../Graph[2]/Edge[@ID=$par_image]/EdgeLayout/@bendX"/>
            <xsl:variable name="bendY" select="../../Graph[2]/Edge[@ID=$par_image]/EdgeLayout/@bendY"/>
            <xsl:variable name="textOffsetX" select="../../Graph[2]/Edge[@ID=$par_image]/EdgeLayout/@textOffsetX"/>
            <xsl:variable name="textOffsetY" select="../../Graph[2]/Edge[@ID=$par_image]/EdgeLayout/@textOffsetY"/>
            <xsl:variable name="loopW" select="../../Graph[2]/Edge[@ID=$par_image]/EdgeLayout/@loopW"/>
            <xsl:variable name="loopH" select="../../Graph[2]/Edge[@ID=$par_image]/EdgeLayout/@loopH"/>
            <xsl:if test="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout[@bendX=$bendY]">
                <attr name="bendX" kind="Layout">
                    <int>
                        <xsl:value-of select="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout/@bendX"/>
                    </int>
                </attr>
            </xsl:if>
            <xsl:if test="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout[@bendY=$bendY]">
                <attr name="bendY" kind="Layout">
                    <int>
                        <xsl:value-of select="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout/@bendY"/>
                    </int>
                </attr>
            </xsl:if>
            <xsl:if test="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout[@textOffsetX=$textOffsetX]">
                <attr name="textOffsetX" kind="Layout">
                    <int>
                        <xsl:value-of select="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout/@textOffsetX"/>
                    </int>
                </attr>
            </xsl:if>
            <xsl:if test="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout[@textOffsetY=$textOffsetY]">
                <attr name="textOffsetY" kind="Layout">
                    <int>
                        <xsl:value-of select="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout/@textOffsetY"/>
                    </int>
                </attr>
            </xsl:if>
            <xsl:if test="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout[@loopH=$loopH]">
                <attr name="loopH" kind="Layout">
                    <int>
                        <xsl:value-of select="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout/@loopH"/>
                    </int>
                </attr>
            </xsl:if>
            <xsl:if test="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout[@loopW=$loopW]">
                <attr name="loopW" kind="Layout">
                    <int>
                        <xsl:value-of select="../../Graph[1]/Edge[@ID=$par_ID]/EdgeLayout/@loopW"/>
                    </int>
                </attr>
            </xsl:if>
            <!-- check if other attributes change -->
            <xsl:for-each select="../../Graph[1]/Edge[@ID=$par_ID]/Attribute">
                <xsl:variable name="type_attr" select="@type"/>
                <!-- only list if value of attribute does not change -->
				<xsl:if test="Value=../../../Graph[2]/Edge[@ID=$par_image]/Attribute[@type=$type_attr]/Value">
                    <xsl:apply-templates select="."/>
                </xsl:if>
            </xsl:for-each>
        </edge>
    </xsl:template>

	<xsl:template name="edge_without_attr">
    <!-- template used by template preserved -->
	<!-- edge of rhs, without any attributes, used in the element map, for non injective mappings -->
        <xsl:param name="par_ID"/>
        <xsl:param name="par_source"/>
        <xsl:param name="par_target"/>
        <xsl:param name="par_type"/>
        <xsl:param name="par_image"/>
        <edge id="{$par_ID}" from="{$par_source}" to="{$par_target}">
            <xsl:variable name="typeName" select="/Document/GraphTransformationSystem/Types/EdgeType[@ID=$par_type]/@name"/>
            <xsl:if test="/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph']">
                <xsl:variable name="typeID" select="/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph']/Edge[@type=$par_type]/@ID"/>
                <type xlink:href="#{$typeID}:{$typeName}"/>
            </xsl:if>
            <xsl:if test="not (/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph'])">
                <type xlink:href="#{$typeName}"/>
            </xsl:if>
        </edge>
    </xsl:template>

	<xsl:template name="deleted">
	<!-- deleted contains all the nodes and edges, together with all their attributes, of the lhs not mapped by the element Mapping (not occuring in any orig) -->
	<!-- deleted also contains the old attribute value of a changing attribute of preserved graph elements -->
		<deleted>
			<xsl:for-each select="Graph[1]/Node">
				<xsl:variable name="var_ID">
					<xsl:value-of select="@ID"/>
				</xsl:variable>
				<xsl:if test="count(../../Morphism/Mapping[@orig=$var_ID])=0">
					<!-- node that should be deleted -->
					<xsl:apply-templates select="."/>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="Graph[1]/Edge">
				<xsl:variable name="var_ID">
					<xsl:value-of select="@ID"/>
				</xsl:variable>
				<!-- edge that should be deleted -->
				<xsl:if test="count(../../Morphism/Mapping[@orig=$var_ID])=0">
					<xsl:apply-templates select="."/>
				</xsl:if>
			</xsl:for-each>
			<!-- look for the elements that are preserved, because their attributes can change their value, then list the old attribute value here  -->
			<xsl:for-each select="Morphism/Mapping">
				<xsl:variable name="var_orig">
					<xsl:value-of select="@orig"/>
				</xsl:variable>
				<xsl:variable name="var_image">
					<xsl:value-of select="@image"/>
				</xsl:variable>
				<!-- list old attribute value of preserved nodes that change their value-->
				<xsl:for-each select="../../Graph[1]/Node[@ID=$var_orig]/Attribute">
           			<xsl:variable name="attrtype" select="@type"/>
                    <xsl:variable name="attrname" select="/Document/GraphTransformationSystem/Types/NodeType/AttrType[@ID=$attrtype]/@attrname"/>
					<xsl:if test="./Value!=../../../Graph[2]/Node[@ID=$var_image]/Attribute[@type=$attrtype]/Value">
                    	<attr name="{$attrname}" idref="{$var_orig}">
	                    	<xsl:call-template name="attribute_value"/>
                    	</attr>
       		        </xsl:if>
	            </xsl:for-each>
				<!-- list layout attribute value of preserved nodes of lhs if value changes -->
				<xsl:for-each select="../../Graph[1]/Node[@ID=$var_orig]">
					<xsl:variable name="layoutx" select="../../Graph[2]/Node[@ID=$var_image]/NodeLayout/@X"/>
                    <xsl:variable name="layouty" select="../../Graph[2]/Node[@ID=$var_image]/NodeLayout/@Y"/>
                    <xsl:if test="./NodeLayout[@X!=$layoutx]">
                        <attr name="X" kind="Layout" idref="{$var_orig}">
                            <int>
                                <xsl:value-of select="./NodeLayout/@X"/>
                            </int>
                        </attr>
                    </xsl:if>
                    <xsl:if test="./NodeLayout[@Y!=$layouty]">
                        <attr name="Y" kind="Layout" idref="{$var_orig}">
                            <int>
                                <xsl:value-of select="./NodeLayout/@Y"/>
                            </int>
                        </attr>
                    </xsl:if>
				</xsl:for-each>
				<!-- list old attribute value of preserved edges of lhs if value changes -->
				<xsl:for-each select="../../Graph[1]/Edge[@ID=$var_orig]/Attribute">
					<xsl:variable name="attrtype" select="@type"/>
                    <xsl:variable name="attrname" select="/Document/GraphTransformationSystem/Types/EdgeType/AttrType[@ID=$attrtype]/@attrname"/>
                	<xsl:if test="./Value!=../../../Graph[2]/Edge[@ID=$var_image]/Attribute[@type=$attrtype]/Value">
                		<attr name="{$attrname}" idref="{$var_orig}">
                            <xsl:call-template name="attribute_value"/>
                        </attr>
					</xsl:if>
            	</xsl:for-each>
				<!-- list layout attribute value of preserved edges of lhs if value changes -->
				<xsl:for-each select="../../Graph[1]/Edge[@ID=$var_orig]">
					<xsl:variable name="bendX" select="../../Graph[2]/Edge[@ID=$var_image]/EdgeLayout/@bendX"/>
                    <xsl:variable name="bendY" select="../../Graph[2]/Edge[@ID=$var_image]/EdgeLayout/@bendY"/>
                    <xsl:variable name="textOffsetX" select="../../Graph[2]/Edge[@ID=$var_image]/EdgeLayout/@textOffsetX"/>
                    <xsl:variable name="textOffsetY" select="../../Graph[2]/Edge[@ID=$var_image]/EdgeLayout/@textOffsetY"/>
    				<xsl:variable name="loopH" select="../../Graph[2]/Edge[@ID=$var_image]/EdgeLayout/@loopH"/>
                    <xsl:variable name="loopW" select="../../Graph[2]/Edge[@ID=$var_image]/EdgeLayout/@loopW"/>                
					<xsl:if test="./EdgeLayout[@bendX!=$bendY]">
                        <attr name="bendX" kind="Layout" idref="{$var_orig}">
                            <int>
                                <xsl:value-of select="./EdgeLayout/@bendX"/>
                            </int>
                        </attr>
                    </xsl:if>
                    <xsl:if test="./EdgeLayout[@bendY!=$bendY]">
                        <attr name="bendY" kind="Layout" idref="{$var_orig}">
                            <int>
                                <xsl:value-of select="./EdgeLayout/@bendY"/>
                            </int>
                        </attr>
                    </xsl:if>
                    <xsl:if test="./EdgeLayout[@textOffsetX!=$textOffsetX]">
                        <attr name="textOffsetX" kind="Layout" idref="{$var_orig}">
                            <int>
                                <xsl:value-of select="./EdgeLayout/@textOffsetX"/>
                            </int>
                        </attr>
                    </xsl:if>
                    <xsl:if test="./EdgeLayout[@textOffsetY!=$textOffsetY]">
                        <attr name="textOffsetY" kind="Layout" idref="{$var_orig}">
                            <int>
                                <xsl:value-of select="./EdgeLayout/@textOffsetY"/>
                            </int>
                        </attr>
                    </xsl:if>
					<xsl:if test="./EdgeLayout[@loopH!=$loopH]">
                        <attr name="loopH" kind="Layout" idref="{$var_orig}">
                            <int> 
                                <xsl:value-of select="./EdgeLayout/@loopH"/>
                            </int>
                        </attr>
                    </xsl:if> 
                    <xsl:if test="./EdgeLayout[@loopW!=$loopW]">
                        <attr name="loopW" kind="Layout" idref="{$var_orig}">
                            <int>
                                <xsl:value-of select="./EdgeLayout/@loopW"/>
                            </int>
                        </attr>
                    </xsl:if>
				</xsl:for-each>
			</xsl:for-each>
		</deleted>
	</xsl:template>

	<xsl:template name="created">
	<!-- created are all the elements of the Right graph that are not in the image of the Mapping element (id does not occur in any image-attribute) -->
		<created>
			<xsl:for-each select="Graph[2]/Node">
				<xsl:variable name="var_ID">
					<xsl:value-of select="@ID"/>
				</xsl:variable>
				<xsl:if test="count(../../Morphism/Mapping[@image=$var_ID])=0">
					<xsl:apply-templates select="."/> 
				</xsl:if>
				<xsl:choose>
					<!-- change of layout attributes -->
					<!-- if rule is injectiv and node is preserved then id of node of rhs is not mentioned in the document, so i have to take id of node in lhs -->
					<xsl:when test="count(../../Morphism/Mapping[@image=$var_ID])=1">
						<xsl:variable name="var_orig" select="../../Morphism/Mapping[@image=$var_ID]/@orig"/>
						<xsl:variable name="layoutx" select="../../Graph[1]/Node[@ID=$var_orig]/NodeLayout/@X"/>
			            <xsl:variable name="layouty" select="../../Graph[1]/Node[@ID=$var_orig]/NodeLayout/@Y"/>
						<xsl:if test="./NodeLayout[@X!=$layoutx]">
							<attr name="X" kind="Layout" idref="{$var_orig}">
    			                <int>
        			                <xsl:value-of select="./NodeLayout/@X"/>
            			        </int>
                			</attr>
						</xsl:if>
						<xsl:if test="./NodeLayout[@Y!=$layouty]">
                			<attr name="Y" kind="Layout" idref="{$var_orig}">
                    			<int>
                        			<xsl:value-of select="./NodeLayout/@Y"/>
                    			</int>
                			</attr>
						</xsl:if>
					</xsl:when>
					<!-- change of layout attributes -->
					<!-- if rule is not injective, list the new value of the attribute, if it changes, only once-->
					<xsl:otherwise>
						<!-- TODO this is very difficult again: make sure that at least one of them changes and only then list the layout attribute-->
						<xsl:if test="count(../../Morphism/Mapping[@image=$var_ID])&gt;1">
							<attr name="X" kind="Layout" idref="{$var_ID}">
    	                        <int>
        	                        <xsl:value-of select="./NodeLayout/@X"/>
            	                </int>
                	        </attr>
                    	    <attr name="Y" kind="Layout" idref="{$var_ID}">
                        	    <int>
                            	    <xsl:value-of select="./NodeLayout/@Y"/>
                            	</int>
                        	</attr>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			<xsl:for-each select="Graph[2]/Edge">
				<xsl:variable name="var_ID">
					<xsl:value-of select="@ID"/>
				</xsl:variable>
				<xsl:if test="count(../../Morphism/Mapping[@image=$var_ID])=0">
					<!-- id's of nodes incident to created edge correspond to id's of the left graph when nodes are preserved, of right graph when created -->
					<xsl:variable name="source">
                    	<xsl:value-of select="@source"/>
                	</xsl:variable>
					<xsl:variable name="target">
                        <xsl:value-of select="@target"/>
                    </xsl:variable>
					<edge id="{@ID}">
						<xsl:choose>
							<!-- test if the source_node is preserved or created to determine id of from_node-->
                        	<xsl:when test="../../Morphism/Mapping[@image=$source]">
								<xsl:attribute name="from">
									<xsl:value-of select="../../Morphism/Mapping[@image=$source]/@orig"/>
								</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
                            	<xsl:attribute name="from">
									<xsl:value-of select="@source"/>
								</xsl:attribute>
							</xsl:otherwise>
                    	</xsl:choose>
                    	<xsl:choose>
							<!-- test if the target_node is preserved or created to determine id of to_node-->
                        	<xsl:when test="../../Morphism/Mapping[@image=$target]">
								<xsl:attribute name="to">
									<xsl:value-of select="../../Morphism/Mapping[@image=$target]/@orig"/>
								</xsl:attribute>	
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="to">   
                                    <xsl:value-of select="@target"/>
								</xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:variable name="type" select="@type"/>
						<xsl:variable name="typeName" select="/Document/GraphTransformationSystem/Types/EdgeType[@ID=$type]/@name"/>
						<xsl:if test="/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph']">
							<xsl:variable name="typeID" select="/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph']/Edge[@type=$type]/@ID"/>
							<type xlink:href="#{$typeID}:{$typeName}"/>
						</xsl:if>
						<xsl:if test="not (/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph'])">
							<type xlink:href="#{$typeName}"/>
						</xsl:if>
						<xsl:apply-templates select="Attribute"/>
						<xsl:if test="EdgeLayout/@bendX">
							<attr name="bendX" kind="Layout">
								<int>
									<xsl:value-of select="EdgeLayout/@bendX"/>
								</int>
							</attr>
						</xsl:if>
						<xsl:if test="EdgeLayout/@bendY">
							<attr name="bendY" kind="Layout">
								<int>
									<xsl:value-of select="EdgeLayout/@bendY"/>
								</int>
							</attr>
						</xsl:if>
						<xsl:if test="EdgeLayout/@textOffsetX">
							<attr name="textOffsetX" kind="Layout">
								<int>
									<xsl:value-of select="EdgeLayout/@textOffsetX"/>
								</int>
							</attr>
						</xsl:if>
						<xsl:if test="EdgeLayout/@textOffsetY">
							<attr name="textOffsetY" kind="Layout">
								<int>
									<xsl:value-of select="EdgeLayout/@textOffsetY"/>
								</int>
							</attr>
						</xsl:if>
						<xsl:if test="EdgeLayout/@loopH">
                            <attr name="loopH" kind="Layout">
                                <int>
                                    <xsl:value-of select="EdgeLayout/@loopH"/>
                                </int>
                            </attr>
                        </xsl:if>
                        <xsl:if test="EdgeLayout/@loopW">
                            <attr name="loopW" kind="Layout">
                                <int>
                                    <xsl:value-of select="EdgeLayout/@loopW"/>
                                </int>
                            </attr>
                        </xsl:if>
					</edge>
				</xsl:if>
               	<xsl:choose>
					<!-- change of layout attributes -->
                    <!-- if rule is injectiv and edge is preserved then id of node of rhs is not mentioned in the document, so i have to take id of node in lhs -->
					<xsl:when test="count(../../Morphism/Mapping[@image=$var_ID])=1">	
						<xsl:variable name="var_orig" select="../../Morphism/Mapping[@image=$var_ID]/@orig"/>
						<xsl:variable name="bendX" select="../../Graph[1]/Edge[@ID=$var_orig]/EdgeLayout/@bendX"/>
			            <xsl:variable name="bendY" select="../../Graph[1]/Edge[@ID=$var_orig]/EdgeLayout/@bendY"/>
			            <xsl:variable name="textOffsetX" select="../../Graph[1]/Edge[@ID=$var_orig]/EdgeLayout/@textOffsetX"/>
			            <xsl:variable name="textOffsetY" select="../../Graph[1]/Edge[@ID=$var_orig]/EdgeLayout/@textOffsetY"/>
						<xsl:variable name="loopH" select="../../Graph[1]/Edge[@ID=$var_orig]/EdgeLayout/@loopH"/>
                        <xsl:variable name="loopW" select="../../Graph[1]/Edge[@ID=$var_orig]/EdgeLayout/@loopW"/>
						<xsl:if test="./EdgeLayout[@bendX!=$bendX]">
	        	            	<attr name="bendX" kind="Layout" idref="{$var_orig}">
   		        	            	<int>
       		        	            	<xsl:value-of select="./EdgeLayout/@bendX"/>
           		        	    	</int>
               		    		</attr>
               			</xsl:if>
						<xsl:if test="./EdgeLayout[@bendY!=$bendY]">
		                    	<attr name="bendY" kind="Layout" idref="{$var_orig}">
       			                	<int>
           	    		            	<xsl:value-of select="./EdgeLayout/@bendY"/>
               	        			</int>
	            	        	</attr>
               			</xsl:if>
						<xsl:if test="./EdgeLayout[@textOffsetX!=$textOffsetX]">
   	                			<attr name="textOffsetX" kind="Layout" idref="{$var_orig}">
       	                			<int>
    	                        	<xsl:value-of select="./EdgeLayout/@textOffsetX"/>
       			                	</int>
               			    	</attr>
      		        	</xsl:if>
						<xsl:if test="./EdgeLayout[@textOffsetY!=$textOffsetY]">
   	            		    	<attr name="textOffsetY" kind="Layout" idref="{$var_orig}">
       	                			<int>
           	                			<xsl:value-of select="./EdgeLayout/@textOffsetY"/>
   		     	                	</int>
       		    	        	</attr>
						</xsl:if>
						<xsl:if test="./EdgeLayout[@loopH!=$loopH]">
                                <attr name="loopH" kind="Layout" idref="{$var_orig}">
                                    <int>
                                    <xsl:value-of select="./EdgeLayout/@loopH"/>
                                    </int>
                                </attr>
                        </xsl:if>
                        <xsl:if test="./EdgeLayout[@loopW!=$loopW]">
                                <attr name="loopW" kind="Layout" idref="{$var_orig}">
                                    <int>
                                        <xsl:value-of select="./EdgeLayout/@loopW"/>
                                    </int>
                                </attr>
                        </xsl:if>
					</xsl:when>
					<!-- change of layout attributes -->
					<!-- if rule is not injective, list the new value of the attribute, if it changes, only once-->
					<xsl:otherwise>
						<!-- mapping is not injective --> 
						<xsl:if test="count(../../Morphism/Mapping[@image=$var_ID])&gt;1">
						<!-- TODO very difficult check if layout value changes for at least one of the source edges, only then list the new created layout attribute -->
							<xsl:if test="./EdgeLayout/@bendX">
                                <attr name="bendX" kind="Layout" idref="{$var_ID}">
       	                            <int>
           	                            <xsl:value-of select="./EdgeLayout/@bendX"/>
               	                    </int>
                   	            </attr>
                       	    </xsl:if>
                           	<xsl:if test="./EdgeLayout/@bendY">
	                           <attr name="bendY" kind="Layout" idref="{$var_ID}">
                                    <int>
       	                                <xsl:value-of select="./EdgeLayout/@bendY"/>
           	                        </int>
               	                </attr>
                   	        </xsl:if>
                       	    <xsl:if test="./EdgeLayout/@textOffsetX">
                           	    <attr name="textOffsetX" kind="Layout" idref="{$var_ID}">
                               	    <int>
                                   	    <xsl:value-of select="./EdgeLayout/@textOffsetX"/>
	                                   </int>
                                </attr>
       	                    </xsl:if>
           	                <xsl:if test="./EdgeLayout/@textOffsetY">
               	                <attr name="textOffsetY" kind="Layout" idref="{$var_ID}">
                   	                <int>
                       	                <xsl:value-of select="./EdgeLayout/@textOffsetY"/>
                           	        </int>
                               	</attr>
                           	</xsl:if>
							<xsl:if test="./EdgeLayout/@loopH">
                                <attr name="loopH" kind="Layout" idref="{$var_ID}">
                                    <int>
                                        <xsl:value-of select="./EdgeLayout/@loopH"/>
                                    </int>
                                </attr>
                            </xsl:if>
                            <xsl:if test="./EdgeLayout/@loopW">
                               <attr name="loopW" kind="Layout" idref="{$var_ID}">
                                    <int>
                                        <xsl:value-of select="./EdgeLayout/@loopW"/>
                                    </int>
                                </attr>
                            </xsl:if>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			<!-- non-layout attributes that should be created, because their valueis changed by the application of the rule -->
            <!-- only consider the elements that are preserved, only their attributes can change their value -->
			<!-- attention for non injective mapping, then if the attribute changes value for at least one of the injective mappings, list this attribute of rhs --> 
            <xsl:for-each select="Morphism/Mapping">
                <xsl:variable name="var_orig">
                    <xsl:value-of select="@orig"/>
                </xsl:variable>
                <xsl:variable name="var_image">
                    <xsl:value-of select="@image"/>
                </xsl:variable>
				<!-- if non injective -->
                <xsl:if test="count(../../Morphism/Mapping[@image=$var_image])&gt;=2">
					<!-- test if this mapping was handled before, because we should do this only once for a non injective mapping -->
					<xsl:if test="count(preceding-sibling::Mapping[@image=$var_image])=0">
						<!-- for each attribute belonging to the node of rhs -->
						<xsl:for-each select="../../Graph[2]/Node[@ID=$var_image]/Attribute">
							<!-- TODO (very difficult) test if at least one of the attributes of elements of the lhs mapped changes its value-->
							<!-- worst case now : each attribute of rhs node is created, although all the values stay the same-->
							<xsl:variable name="attrtype" select="@type"/>
                            <xsl:variable name="attrname" select="/Document/GraphTransformationSystem/Types/NodeType/AttrType[@ID=$attrtype]/@attrname"/>
							<attr name="{$attrname}" idref="{$var_image}">
								<xsl:call-template name="attribute_value"/>
							</attr>
						</xsl:for-each>
						<!-- for each attribute belonging to the edge of rhs -->
						<xsl:for-each select="../../Graph[2]/Edge[@ID=$var_image]/Attribute">
                            <!-- TODO (very difficult) test if at least one of the attributes of the lhs mapped changes its value-->
							<!-- worst case now : each attribute of rhs edge is created (and preserved), although all the values stay the same-->
						    <xsl:variable name="attrtype" select="@type"/>
                            <xsl:variable name="attrname" select="/Document/GraphTransformationSystem/Types/EdgeType/AttrType[@ID=$attrtype]/@attrname"/>
                            <attr name="{$attrname}" idref="{$var_image}">
                                <xsl:call-template name="attribute_value"/>
                            </attr>	
                        </xsl:for-each>
					</xsl:if>
                </xsl:if>
				<!-- if injective, be carefull with idref of attributes, idref should be id of preserved element of lhs -->
				<xsl:if test="count(../../Morphism/Mapping[@image=$var_image])=1">
                    <!-- injectiv mapping -->
                    <!-- list of attributes of node/edge of rhs that should be created, because the values of these attributes were changed -->
                    <xsl:for-each select="../../Graph[1]/Node[@ID=$var_orig]/Attribute">
                        <xsl:variable name="type_attr" select="@type"/>
                        <!-- list attribute of rhs with idref of node/edge lhs if value changes -->
                        <xsl:if test="./Value!=../../../Graph[2]/Node[@ID=$var_image]/Attribute[@type=$type_attr]/Value">
							<xsl:for-each select="../../../Graph[2]/Node[@ID=$var_image]/Attribute[@type=$type_attr]">
								<xsl:variable name="attrtype" select="@type"/>
        						<xsl:variable name="attrname" select="/Document/GraphTransformationSystem/Types/NodeType/AttrType[@ID=$attrtype]/@attrname"/>
        						<attr name="{$attrname}" idref="{$var_orig}">
									<xsl:call-template name="attribute_value"/>
								</attr>
                			</xsl:for-each>
				        </xsl:if>
                    </xsl:for-each>
                    <xsl:for-each select="../../Graph[1]/Edge[@ID=$var_orig]/Attribute">
                        <xsl:variable name="type_attr" select="@type"/>
                        <!-- list attribute of rhs if value changes -->
                        <xsl:if test="./Value!=../../../Graph[2]/Edge[@ID=$var_image]/Attribute[@type=$type_attr]/Value">
							<xsl:for-each select="../../../Graph[2]/Edge[@ID=$var_image]/Attribute[@type=$type_attr]">
                        		<xsl:variable name="attrtype" select="@type"/>
						        <xsl:variable name="attrname" select="/Document/GraphTransformationSystem/Types/EdgeType/AttrType[@ID=$attrtype]/@attrname"/>
								<attr name="{$attrname}" idref="{$var_orig}">
									<xsl:call-template name="attribute_value"/>
								</attr>
                        	</xsl:for-each>
						</xsl:if>
                    </xsl:for-each>
                </xsl:if>
            </xsl:for-each>
		</created>
	</xsl:template>

	<!-- deleted and created template use this template to set the value of an attribute -->
	<!-- TODO in ggx2gxl.xsl reimplement the template Attribute such that it can be used here (delete generation of attr name="" because it's always different) --> 
	<xsl:template name="attribute_value">
        <xsl:if test="Value/java/string">
            <!-- TODO waiting for new gxl element freetype-->
                <xsl:copy-of select="Value/java/string"/>
        </xsl:if>
        <xsl:if test="Value/java">
            <xsl:if test="not (Value/java/string)">
                <!-- TODO waiting for new gxl element freetype -->
                <freeType>
                    <xsl:copy-of select="Value/java"/>
                </freeType>
            </xsl:if>
        </xsl:if>
		<!-- TODO content of freeType is PCDATA, so value-of select="Value/Object/SerializedData" or "Value/Object/void" -->
        <xsl:if test="Value/object">
            <!-- TODO waiting for new gxl element freetype, then only take the PCDATA part -->
            <freeType>
                <xsl:copy-of select="Value/object"/>
            </freeType>
        </xsl:if>
        <xsl:if test="Value/boolean">
            <bool>
                <xsl:value-of select="Value/boolean"/>
            </bool>
        </xsl:if>
        <xsl:if test="Value/string">
            <string>
                <xsl:value-of select="Value/string"/>
            </string>
        </xsl:if>
        <xsl:if test="Value/int">
            <int>
                <xsl:value-of select="Value/int"/>
            </int>
        </xsl:if>
        <xsl:if test="Value/double">
            <!-- TODO double is not allowed in gxl.dtd,possible loss of precision -->
            <float>
                <xsl:value-of select="Value/double"/>
            </float>
        </xsl:if>
        <xsl:if test="Value/float">
            <float>
                <xsl:value-of select="Value/float"/>
            </float>
        </xsl:if>
        <xsl:if test="Value/short">
            <!-- short is not allowed in gxl.dtd -->
            <int>
                <xsl:value-of select="Value/short"/>
            </int>
        </xsl:if>
        <xsl:if test="Value/long">
            <!-- long is not allowed in gxl.dtd -->
            <int>
                <xsl:value-of select="Value/long"/>
            </int>
        </xsl:if>
        <xsl:if test="Value/char">
            <!-- char is not allowed in gxl.dtd -->
            <string>
                <xsl:value-of select="Value/char"/>
            </string>
        </xsl:if>
    </xsl:template>

	<xsl:template name="condition">
		<!-- translate ApplicationCondition (ggx) in condition (gtxl) -->
		<xsl:if test="(count(ApplCondition/NAC)=1)and(count(ApplCondition/AttrCondition/Condition)=0)">
			<precondition>
				<xsl:call-template name="graphCondition"/>
			</precondition>
		</xsl:if>
		<xsl:if test="(count(ApplCondition/NAC)=0)and(count(ApplCondition/AttrCondition/Condition)=1)">
			<precondition>
           		<xsl:call-template name="attrCondition"/> 
			</precondition>
        </xsl:if>
		<xsl:if test="(count(ApplCondition/NAC)=1)and(count(ApplCondition/AttrCondition/Condition)=1)">
			<precondition>
			<condition>
			<conjunction>
				<xsl:call-template name="attrCondition"/>
				<xsl:call-template name="graphCondition"/>
			</conjunction>
			</condition>
			</precondition>
		</xsl:if>
		<xsl:if test="(count(ApplCondition/NAC)&gt;=2)or(count(ApplCondition/AttrCondition/Condition)&gt;=2)">
            <precondition>
            <condition>
            <conjunction>
                <xsl:call-template name="attrCondition"/>
                <xsl:call-template name="graphCondition"/>
            </conjunction>
            </condition>
            </precondition>
        </xsl:if>
	</xsl:template>

	<xsl:template name="attrCondition">
		<xsl:for-each select="ApplCondition/AttrCondition/Condition">
			<condition>
                 <!-- if necessary then an id for attrCondition can be generated here -->
                 <attrCondition>
                      <xsl:value-of select="Value/java/string"/>
                 </attrCondition> 
            </condition>
        </xsl:for-each>
	</xsl:template>

	<xsl:template name="graphCondition">
		<xsl:for-each select="ApplCondition/NAC">
                    <condition id="{concat(Graph/@ID,'nac')}">
                    <graphCondition>
                    <xsl:choose>
                        <xsl:when test="Graph/@name">
                            <xsl:attribute name="name">
                                <xsl:value-of select="Graph/@name"/>
                            </xsl:attribute>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:if test="@name">
                                <xsl:value-of select="@name"/>
                            </xsl:if>
                        </xsl:otherwise>
                    </xsl:choose>
                    <!-- node that should not be there -->
                    <xsl:for-each select="Graph/Node">
                        <xsl:variable name="var_ID">
                            <xsl:value-of select="@ID"/>
                        </xsl:variable>
                        <xsl:variable name="var_type">
                            <xsl:value-of select="@type"/>
                        </xsl:variable>
                        <!-- those nodes of the nac that are already in lhs are not listed anymore -->
                        <!-- remark information loss for non-injective mapping ! -->
                        <xsl:if test="count(../../Morphism/Mapping[@image=$var_ID])=0">
                            <xsl:call-template name="node_condition">
                                <xsl:with-param name="par_ID"><xsl:value-of select="$var_ID"/></xsl:with-param>
                                <xsl:with-param name="par_type"><xsl:value-of select="$var_type"/></xsl:with-param>
                            </xsl:call-template> 
                        </xsl:if> 
                    </xsl:for-each>
                    <!-- edge that should not be there -->
                    <xsl:for-each select="Graph/Edge">
                        <xsl:variable name="var_ID">
                            <xsl:value-of select="@ID"/>
                        </xsl:variable>
                        <xsl:variable name="var_type">
                            <xsl:value-of select="@type"/>
                        </xsl:variable>
                        <!-- those edges of the nac that are already in lhs are not listed anymore -->
                        <!-- remark information loss for non-injective mapping ! -->
                        <xsl:if test="count(../../Morphism/Mapping[@image=$var_ID])=0">
                            <xsl:variable name="var_source">
                                <xsl:value-of select="@source"/>
                            </xsl:variable>
                            <xsl:variable name="var_target">
                                <xsl:value-of select="@target"/>
                            </xsl:variable>
                            <xsl:choose>
                            <xsl:when test="../../Morphism/Mapping[@image=$var_source]">
                                <xsl:variable name="orig_source" select="../../Morphism/Mapping[@image=$var_source]/@orig"/>
                                <xsl:choose>
                                <xsl:when test="../../Morphism/Mapping[@image=$var_target]">
                                    <!-- only new edge in nac, between two already existing nodes -->
                                    <xsl:variable name="orig_target" select="../../Morphism/Mapping[@image=$var_target]/@orig"/>
                                    <xsl:call-template name="edge_condition">
                                        <xsl:with-param name="par_ID"><xsl:value-of select="$var_ID"/></xsl:with-param>
                                        <xsl:with-param name="par_source"><xsl:value-of select="$orig_source"/></xsl:with-param>
                                        <xsl:with-param name="par_target"><xsl:value-of select="$orig_target"/></xsl:with-param>
                                        <xsl:with-param name="par_type"><xsl:value-of select="$var_type"/></xsl:with-param>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:variable name="orig_target" select="$var_target"/>
                                    <!-- new edge with new target node in nac -->
                                    <xsl:call-template name="edge_condition">
                                        <xsl:with-param name="par_ID"><xsl:value-of select="$var_ID"/></xsl:with-param>
                                        <xsl:with-param name="par_source"><xsl:value-of select="$orig_source"/></xsl:with-param>
										<xsl:with-param name="par_target"><xsl:value-of select="$orig_target"/></xsl:with-param>
                                        <xsl:with-param name="par_type"><xsl:value-of select="$var_type"/></xsl:with-param>
                                    </xsl:call-template>
                                </xsl:otherwise>
                                </xsl:choose>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:variable name="orig_source" select="$var_source"/>
                                <xsl:choose>
                                <xsl:when test="../../Morphism/Mapping[@image=$var_target]">
                                    <!-- new edge with new source node in nac -->
                                    <xsl:variable name="orig_target" select="../../Morphism/Mapping[@image=$var_target]/@orig"/>
                                    <xsl:call-template name="edge_condition">
                                        <xsl:with-param name="par_ID"><xsl:value-of select="$var_ID"/></xsl:with-param>
                                        <xsl:with-param name="par_source"><xsl:value-of select="$orig_source"/></xsl:with-param>
                                        <xsl:with-param name="par_target"><xsl:value-of select="$orig_target"/></xsl:with-param>
                                        <xsl:with-param name="par_type"><xsl:value-of select="$var_type"/></xsl:with-param>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <!-- new edge with new source and target node in nac -->
                                    <xsl:variable name="orig_target" select="$var_target"/>
                                    <xsl:call-template name="edge_condition">
                                        <xsl:with-param name="par_ID"><xsl:value-of select="$var_ID"/></xsl:with-param>
                                        <xsl:with-param name="par_source"><xsl:value-of select="$orig_source"/></xsl:with-param>
                                        <xsl:with-param name="par_target"><xsl:value-of select="$orig_target"/></xsl:with-param>
                                        <xsl:with-param name="par_type"><xsl:value-of select="$var_type"/></xsl:with-param>
                                    </xsl:call-template>
                                </xsl:otherwise>
                                </xsl:choose>
                            </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                    </xsl:for-each>
                </graphCondition>
                </condition>
            </xsl:for-each>
	</xsl:template>

	<xsl:template name="node_condition">
	<!-- like in ggx2gxl.xsl only with changes in id -->
		<xsl:param name="par_ID"/>
		<xsl:param name="par_type"/>
		<node id="{$par_ID}">
			<!-- create xlink to NodeType-Name -->
			<type xlink:href="#{/Document/GraphTransformationSystem/Types/NodeType[@ID=$par_type]/@name}"/>
			<xsl:apply-templates select="Attribute"/>
			<attr name="X" kind="Layout">
				<int>
					<xsl:value-of select="NodeLayout/@X"/>
				</int>
			</attr>
			<attr name="Y" kind="Layout">
				<int>
					<xsl:value-of select="NodeLayout/@Y"/>
				</int>
			</attr>
		</node>
	</xsl:template>

	<xsl:template name="edge_condition">
    <!-- like in ggx2gxl.xsl only with changes in id -->
        <xsl:param name="par_ID"/>
        <xsl:param name="par_type"/>
		<xsl:param name="par_source"/>
		<xsl:param name="par_target"/>
    	<!-- convert 'Instance Graph - Edges' -->
    	<edge id="{$par_ID}" from="{$par_source}" to="{$par_target}">
        	<xsl:variable name="type" select="$par_type"/>
        	<xsl:variable name="typeName" select="/Document/GraphTransformationSystem/Types/EdgeType[@ID=$par_type]/@name"/>

        	<xsl:if test="/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph']">
            	<xsl:variable name="typeID" select="/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph']/Edge[@type=$par_type]/@ID"/>
            	<type xlink:href="#{$typeID}:{$typeName}"/>
        	</xsl:if>

        	<xsl:if test="not (/Document/GraphTransformationSystem/Types/Graph[@name='Type Graph'])">
            	<type xlink:href="#{$typeName}"/>
        	</xsl:if>

        	<xsl:apply-templates select="Attribute"/>
        	<xsl:if test="../../Graph/Edge[@ID=$par_ID]/EdgeLayout/@bendX">
            	<attr name="bendX" kind="Layout">
                	<int>
                    	<xsl:value-of select="EdgeLayout/@bendX"/>
                	</int>
            	</attr>
        	</xsl:if>
        	<xsl:if test="../../Graph/Edge[@ID=$par_ID]/EdgeLayout/@bendY">
            	<attr name="bendY" kind="Layout">
                	<int>
                    	<xsl:value-of select="EdgeLayout/@bendY"/>
                	</int>
            	</attr>
        	</xsl:if>
        	<xsl:if test="../../Graph/Edge[@ID=$par_ID]/EdgeLayout/@textOffsetX">
            	<attr name="textOffsetX" kind="Layout">
                	<int>
                    	<xsl:value-of select="EdgeLayout/@textOffsetX"/>
                	</int>
            	</attr>
        	</xsl:if>
        	<xsl:if test="../../Graph/Edge[@ID=$par_ID]/EdgeLayout/@textOffsetY">
            	<attr name="textOffsetY" kind="Layout">
                	<int>
                    	<xsl:value-of select="EdgeLayout/@textOffsetY"/>
                	</int>
            	</attr>
        	</xsl:if>
			<xsl:if test="../../Graph/Edge[@ID=$par_ID]/EdgeLayout/@loopH">
                <attr name="loopH" kind="Layout">
                    <int>
                        <xsl:value-of select="EdgeLayout/@loopH"/>
                    </int>
                </attr> 
            </xsl:if>       
            <xsl:if test="../../Graph/Edge[@ID=$par_ID]/EdgeLayout/@loopW">
                <attr name="loopW" kind="Layout">
                    <int>
                        <xsl:value-of select="EdgeLayout/@loopW"/>
                    </int>      
                </attr>         
            </xsl:if>
    	</edge>	
	</xsl:template>

	<xsl:template name="embedding">
		<!-- to be further refined in DTD -->
	</xsl:template>

	<xsl:template name="parameter">
		<!-- variable and parameter in gts are described in one element Parameter, transform to parameter and variable in gtxl -->
		<xsl:for-each select="Parameter">
			<xsl:if test="@PTYPE">
				<parameter name="{@name}">
					<xsl:if test="@PTYPE='input'">
						<xsl:attribute name="type">in</xsl:attribute>
					</xsl:if>
					<xsl:if test="@PTYPE='output'">
                        <xsl:attribute name="type">out</xsl:attribute>
                    </xsl:if>
					<xsl:if test="@PTYPE='inout'">
                        <xsl:attribute name="type">inout</xsl:attribute>
                    </xsl:if>
					<xsl:call-template name="var_par"/>
				</parameter>
			</xsl:if>
			<xsl:if test="not (@PTYPE)">
				<variable name="{@name}">
					<xsl:call-template name="var_par"/>
				</variable>	
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="var_par">
		<xsl:if test="./@type='java/string'">
			<!-- TODO waiting for new gxl : free type -->
				<xsl:copy-of select="Value/java/string"/>
        </xsl:if>
        <xsl:if test="./@type = 'java'">
			<xsl:if test="./@type = not ('java/string')">
				<!-- TODO waiting for new gxl : free type -->
				<freeType>
					<xsl:copy-of select="Value/java"/>
				</freeType>	
			</xsl:if>
		</xsl:if>
		<xsl:if test="./@type = 'object'">
			<!-- TODO waiting for new gxl : free type then only take the PCDATA part -->
			<freeType>
				<xsl:copy-of select="Value/object"/>
			</freeType>
		</xsl:if>
		<xsl:if test="./@type = 'boolean' or ./@type = 'Boolean'">
			<bool>
				<xsl:value-of select="@value"/>
			</bool>
		</xsl:if>
		<xsl:if test="./@type = 'string' or ./@type='String'">
			<string>
				<xsl:value-of select="@value"/>
			</string>
		</xsl:if>
		<xsl:if test="./@type = 'int' or ./@type='Int'">
			<int>
				<xsl:value-of select="@value"/>
			</int>
		</xsl:if>
		<xsl:if test="./@type = 'double' or ./@type='Double'">
		<!-- TODOdouble is not allowed in gxl.dtd, possible loss of precision -->
			<float>
				<xsl:value-of select="@value"/>
			</float>
		</xsl:if>
		<xsl:if test="./@type ='float' or ./@type='Float'">
			<float>
				<xsl:value-of select="@value"/>
			</float>
		</xsl:if>
		<xsl:if test="./@type ='short' or ./@type='Short'">
		<!-- short is not allowed in gxl.dtd -->
			<int>
				<xsl:value-of select="@value"/>
			</int>
		</xsl:if>
		<xsl:if test="./@type = 'long' or ./@type='Long'">
		<!-- long is not allowed in gxl.dtd -->
			<int>
				<xsl:value-of select="@value"/>
			</int>
		</xsl:if>
		<xsl:if test="./@type = 'char' or ./@type='Char'">
        <!-- char is not allowed in gxl.dtd -->
			<string>
				<xsl:value-of select="@value"/>
			</string>
		</xsl:if>	
	</xsl:template>

	<!-- rule-specific attributes e.g. layer number of rule -->
	<xsl:template name="attribute">
		<xsl:for-each select="TaggedValue">
			<attr name="{@Tag}">
				<string><xsl:value-of select="@TagValue"/></string>
			</attr>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
