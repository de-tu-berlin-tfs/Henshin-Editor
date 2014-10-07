<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xml="http://www.w3.org/XML/1998/namespace"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                xmlns:lxslt="http://xml.apache.org/xslt"
                xmlns:xalan="http://xml.apache.org/xalan"
                version="1.0"
                exclude-result-prefixes="#default">

  <xsl:output doctype-system="gxl.dtd"  
              method="xml" 
              indent="yes"/>
   
   <!-- remove text nodes that contain only whitespaces --> 
    <xsl:strip-space elements = '*' /> 


<xsl:template match="/Document/GraphTransformationSystem">
<gxl>
	<!-- store name of GraphTransformationSystem -->
	<attr id="{/Document/GraphTransformationSystem/@ID}" 
	      name="{/Document/GraphTransformationSystem/@name}"/>
	<!-- convert Types -->
	<xsl:apply-templates select="Types"/>

	<!-- convert InstanceGraph -->
	<xsl:apply-templates select="Graph"/>
</gxl>
</xsl:template>

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


</xsl:stylesheet>
